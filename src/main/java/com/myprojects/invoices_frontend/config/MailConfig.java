package com.myprojects.invoices_frontend.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class MailConfig {

    @Value("${endpoint_mail}")
    private String mailEndpoint;

    public String getMailEndpoint() {
        return mailEndpoint;
    }
}