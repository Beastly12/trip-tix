package org.dafe.tripTix.configs;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.List;


public class CorsConfig {
    private static final Long MAX_AGE_SECS = 3600L;

    @Value("#{'${cors.exposed-headers}'.split(',')}")
    public List<String> exposedHeaders;

    @Value("#{'${cors.allowed-methods}'.split(',')}")
    public List<String> allowedMethods;

    @Value("#{'${cors.allowed-headers}'.split(',')}")
    public List<String> allowedHeaders;

    @Value("#{'${cors.allowed-origins}'.split(',')}")
    public List<String> allowedOrigins;

    @Bean
    public CorsConfigurationSource configurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(allowedOrigins);
        configuration.setAllowedHeaders(allowedHeaders);
        configuration.setAllowedMethods(allowedMethods);
        configuration.setExposedHeaders(exposedHeaders);
        configuration.setAllowCredentials(true);
        configuration.setMaxAge(MAX_AGE_SECS);
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);  // Changed to cover all paths
        return source;
    }
}
