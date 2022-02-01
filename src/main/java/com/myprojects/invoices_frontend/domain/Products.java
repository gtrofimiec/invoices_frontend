package com.myprojects.invoices_frontend.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.myprojects.invoices_frontend.domain.dtos.InvoicesDto;

import java.math.BigDecimal;
import java.util.List;
import java.util.Objects;

public class Products {

    private Long id;
    private String name;
    private int vatRate;
    private BigDecimal netPrice;
    private BigDecimal vatValue;
    private BigDecimal grossPrice;
    private List<Invoices> invoicesList;

    public Products() {
    }

    public Products(Long id, String name, int vatRate, BigDecimal netPrice, BigDecimal vatValue, BigDecimal grossPrice,
                    List<Invoices> invoicesList) {
        this.id = id;
        this.name = name;
        this.vatRate = vatRate;
        this.netPrice = netPrice;
        this.vatValue = vatValue;
        this.grossPrice = grossPrice;
        this.invoicesList = invoicesList;
    }

    public Products(Long id, String name, int vatRate, BigDecimal netPrice, BigDecimal vatValue,
                    BigDecimal grossPrice) {
        this.id = id;
        this.name = name;
        this.vatRate = vatRate;
        this.netPrice = netPrice;
        this.vatValue = vatValue;
        this.grossPrice = grossPrice;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getVatRate() {
        return vatRate;
    }

    public void setVatRate(int vatRate) {
        this.vatRate = vatRate;
    }

    public BigDecimal getNetPrice() {
        return netPrice;
    }

    public void setNetPrice(BigDecimal netPrice) {
        this.netPrice = netPrice;
    }

    public BigDecimal getVatValue() {
        return vatValue;
    }

    public void setVatValue(BigDecimal vatValue) {
        this.vatValue = vatValue;
    }

    public BigDecimal getGrossPrice() {
        return grossPrice;
    }

    public void setGrossPrice(BigDecimal grossPrice) {
        this.grossPrice = grossPrice;
    }

    public List<Invoices> getInvoicesList() {
        return invoicesList;
    }

    public void setInvoicesList(List<Invoices> invoicesList) {
        this.invoicesList = invoicesList;
    }
}