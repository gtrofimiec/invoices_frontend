package com.myprojects.invoices_frontend.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class PostcodeApiConfig {

    @Value("${endpoint_postcode}")
    private String postcodeApiEndpoint;

    public String getPostcodeApiEndpoint() {
        return postcodeApiEndpoint;
    }
}