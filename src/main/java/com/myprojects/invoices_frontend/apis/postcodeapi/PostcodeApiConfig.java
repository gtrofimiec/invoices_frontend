package com.myprojects.invoices_frontend.apis.postcodeapi;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class PostcodeApiConfig {

    @Value("http://localhost:8081/v1/invoices/getTown/")
    private String postcodeApiEndpoint;

    public String getPostcodeApiEndpoint() {
        return postcodeApiEndpoint;
    }
}