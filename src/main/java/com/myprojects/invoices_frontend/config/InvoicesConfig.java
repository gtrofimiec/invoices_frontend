package com.myprojects.invoices_frontend.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class InvoicesConfig {

    @Value("http://localhost:8081/v1/invoices/invoices")
    private String invoicesEndpoint;

    public String getInvoicesEndpoint() {
        return invoicesEndpoint;
    }
}
