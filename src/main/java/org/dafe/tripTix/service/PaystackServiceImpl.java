package org.dafe.tripTix.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.dafe.tripTix.constant.ApiConstants;
import org.dafe.tripTix.dto.InitializePaymentDto;
import org.dafe.tripTix.repository.InitializePaymentResponse;
import org.dafe.tripTix.repository.PaymentVerificationResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Service
public class PaystackServiceImpl implements PaystackService{

    @Value("${paystack.secret.key}")
    private String paystackSecretKey;

    private final RestTemplate restTemplate = new RestTemplate();
    private static final Logger logger = LoggerFactory.getLogger(PaystackServiceImpl.class);

    @Override
//    @CacheEvict(value = "bookings", allEntries = true)
    public InitializePaymentResponse initializePayment(InitializePaymentDto initializePaymentDto) {
        String url = ApiConstants.PAYSTACK_INITIALIZE_PAY;

        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + paystackSecretKey.trim());
        headers.set("Content-Type", "application/json");

        String callbackUrl = "http://localhost:8090/bookings/paystack/callback";
        initializePaymentDto.setCallback_url(callbackUrl);

        HttpEntity<InitializePaymentDto> request = new HttpEntity<>(initializePaymentDto, headers);

        try {
            logger.info("Sending request to Paystack: URL={}, Headers={}, Body={}", url, headers, initializePaymentDto);
            ResponseEntity<String> response = restTemplate.postForEntity(url, request, String.class);
            logger.info("Received raw response from Paystack: Status={}, Body={}", response.getStatusCode(), response.getBody());

            // Convert raw response to InitializePaymentResponse
            ObjectMapper objectMapper = new ObjectMapper();
            InitializePaymentResponse paymentResponse = objectMapper.readValue(response.getBody(), InitializePaymentResponse.class);

            if (paymentResponse != null && paymentResponse.isStatus()) {
                logger.info("Payment initialization successful: Authorization URL={}", paymentResponse.getData().getAuthorizationUrl());
            } else {
                logger.warn("Payment initialization failed: Status={}, Message={}", response.getStatusCode(), paymentResponse != null ? paymentResponse.getMessage() : "No response body");
            }

            return paymentResponse;
        } catch (HttpClientErrorException e) {
            logger.error("HTTP Error initializing payment: Status={}, ResponseBody={}", e.getStatusCode(), e.getResponseBodyAsString());
            throw e;
        } catch (Exception e) {
            logger.error("Error initializing payment: {}", e.getMessage(), e);
            try {
                throw e;
            } catch (JsonProcessingException ex) {
                throw new RuntimeException(ex);
            }
        }
    }


    @Override
    public PaymentVerificationResponse paymentVerification(String reference) throws Exception {
        String url = ApiConstants.PAYSTACK_VERIFY + reference;

        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + paystackSecretKey.trim());

        HttpEntity<String> request = new HttpEntity<>(headers);

        try {
            logger.info("Sending verification request to Paystack: URL={}, Headers={}", url, headers);
            ResponseEntity<PaymentVerificationResponse> response = restTemplate.exchange(url, HttpMethod.GET, request, PaymentVerificationResponse.class);
            logger.info("Received verification response from Paystack: Status={}, Body={}", response.getStatusCode(), response.getBody());
            return response.getBody();
        } catch (HttpClientErrorException e) {
            logger.error("HTTP Error verifying payment: Status={}, ResponseBody={}", e.getStatusCode(), e.getResponseBodyAsString());
            throw e;
        } catch (Exception e) {
            logger.error("Error verifying payment: {}", e.getMessage(), e);
            throw e;
        }
    }
}