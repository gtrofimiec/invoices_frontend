package com.myprojects.invoices_frontend;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
public class InvoicesFrontendApplication {

    public static void main(String[] args) {
//        SpringApplication.run(InvoicesFrontendApplication.class, args);
        SpringApplicationBuilder builder = new SpringApplicationBuilder(InvoicesFrontendApplication.class);
        builder.headless(false);
        ConfigurableApplicationContext context = builder.run(args);
    }
}