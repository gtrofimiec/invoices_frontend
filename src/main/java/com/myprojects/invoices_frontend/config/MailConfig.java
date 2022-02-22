package com.myprojects.invoices_frontend.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class MailConfig {

    @Value("http://localhost:8081/v1/invoices/mail")
    private String mailEndpoint;

    public String getMailEndpoint() {
        return mailEndpoint;
    }
}
