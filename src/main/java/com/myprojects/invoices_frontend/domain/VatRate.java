package com.myprojects.invoices_frontend.domain;

public enum VatRate {
    VAT23(23), VAT8(8), VAT0(0);

    private final int value;

    VatRate(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}
