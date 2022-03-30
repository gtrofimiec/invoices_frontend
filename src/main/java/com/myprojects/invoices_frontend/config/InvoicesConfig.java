package com.myprojects.invoices_frontend.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class InvoicesConfig {

    @Value("${endpoint_invoices}")
    private String invoicesEndpoint;

    public String getInvoicesEndpoint() {
        return invoicesEndpoint;
    }
}