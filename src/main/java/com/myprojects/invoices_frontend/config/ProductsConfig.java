package com.myprojects.invoices_frontend.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class ProductsConfig {

    @Value("http://localhost:8081/v1/invoices/products")
    private String productsEndpoint;

    public String getProductsEndpoint() {
        return productsEndpoint;
    }
}