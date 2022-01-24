package com.myprojects.invoices_frontend.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class CustomersConfig {

    @Value("http://localhost:8081/v1/invoices/customers")
    private String customersEndpoint;

    public String getCustomersEndpoint() {
        return customersEndpoint;
    }
}