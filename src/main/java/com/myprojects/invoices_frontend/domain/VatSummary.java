package com.myprojects.invoices_frontend.domain;

import java.math.BigDecimal;

public class VatSummary {

    int vatRate;
    BigDecimal netSum;
    BigDecimal vatSum;
    BigDecimal grossSum;

    public VatSummary(int vatRate, BigDecimal netSum, BigDecimal vatSum, BigDecimal grossSum) {
        this.vatRate = vatRate;
        this.netSum = netSum;
        this.vatSum = vatSum;
        this.grossSum = grossSum;
    }

    public int getVatRate() {
        return vatRate;
    }

    public void setVatRate(int vatRate) {
        this.vatRate = vatRate;
    }

    public BigDecimal getNetSum() {
        return netSum;
    }

    public void setNetSum(BigDecimal netSum) {
        this.netSum = netSum;
    }

    public BigDecimal getVatSum() {
        return vatSum;
    }

    public void setVatSum(BigDecimal vatSum) {
        this.vatSum = vatSum;
    }

    public BigDecimal getGrossSum() {
        return grossSum;
    }

    public void setGrossSum(BigDecimal grossSum) {
        this.grossSum = grossSum;
    }
}
