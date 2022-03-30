package com.myprojects.invoices_frontend.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class CeidgApiConfig {

    @Value("${endpoint_ceidg}")
    private String ceidgApiEndpoint;

    public String getCeidgApiEndpoint() {
        return ceidgApiEndpoint;
    }
}