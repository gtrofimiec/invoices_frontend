package com.myprojects.invoices_frontend.domain;

public enum PaymentMethods {

    CASH("gotówka"), BANK("przelew"), CARD("karta kredytowa");

    private final String paymentMethod;

    PaymentMethods(String paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    public int getId() {
        return ordinal();
    }

    public String getPaymentMethod() {
        return paymentMethod;
    }
}