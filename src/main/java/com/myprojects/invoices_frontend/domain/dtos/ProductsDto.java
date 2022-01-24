package com.myprojects.invoices_frontend.domain.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.math.BigDecimal;

public class ProductsDto {

    @JsonProperty("product_id")
    private Long id;
    @JsonProperty("name")
    private String name;
    @JsonProperty("vat_rate")
    private int vatRate;
    @JsonProperty("net_price")
    private BigDecimal netPrice;
    @JsonProperty("vat_value")
    private BigDecimal vatValue;
    @JsonProperty("gross_price")
    private BigDecimal grossPrice;

    public ProductsDto() {
    }

    public ProductsDto(Long id, String name, int vatRate, BigDecimal netPrice, BigDecimal vatValue,
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
}