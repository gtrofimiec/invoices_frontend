package com.myprojects.invoices_frontend.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class CustomersConfig {

    @Value("${endpoint_customers}")
    private String customersEndpoint;

    public String getCustomersEndpoint() {
        return customersEndpoint;
    }
}