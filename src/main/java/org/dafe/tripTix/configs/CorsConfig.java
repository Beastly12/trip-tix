package org.dafe.tripTix.configs;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Collections;
import java.util.List;


@Configuration
public class CorsConfig {
    private static final Long MAX_AGE_SECS = 3600L;

    @Value("${cors.expose-headers}")
    private String exposeHeaders;

    @Value("${cors.allowed-methods}")
    private String allowedMethods;

    @Value("${cors.allowed-headers}")
    private String allowedHeaders;

    @Value("${cors.allowed-origins}")
    private String allowedOrigins;

    @Bean
    public CorsConfigurationSource configurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();

        if ("*".equals(allowedOrigins)) {
            configuration.setAllowedOrigins(Collections.singletonList("*"));
        } else {
            configuration.setAllowedOrigins(List.of(allowedOrigins.split(",")));
        }

        if ("*".equals(allowedMethods)) {
            configuration.setAllowedMethods(Collections.singletonList("*"));
        } else {
            configuration.setAllowedMethods(List.of(allowedMethods.split(",")));
        }

        if ("*".equals(allowedHeaders)) {
            configuration.addAllowedHeader("*");
        } else {
            configuration.setAllowedHeaders(List.of(allowedHeaders.split(",")));
        }

        if ("*".equals(exposeHeaders)) {
            configuration.setExposedHeaders(Collections.singletonList("*"));
        } else {
            configuration.setExposedHeaders(List.of(exposeHeaders.split(",")));
        }

        configuration.setAllowCredentials(true);
        configuration.setMaxAge(MAX_AGE_SECS);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);  // Covers all paths
        return source;
    }
}