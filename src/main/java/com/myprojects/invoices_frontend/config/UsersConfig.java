package com.myprojects.invoices_frontend.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class UsersConfig {

    @Value("${endpoint_users}")
    private String userEndpoint;

    public String getUserEndpoint() {
        return userEndpoint;
    }
}