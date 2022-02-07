package com.myprojects.invoices_frontend.domain.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.math.BigDecimal;
import java.util.List;

public class ProductsDto {

    @JsonProperty
    private Long id;
    @JsonProperty
    private String name;
    @JsonProperty
    private int vatRate;
    @JsonProperty
    private BigDecimal netPrice;
    @JsonProperty
    private BigDecimal vatValue;
    @JsonProperty
    private BigDecimal grossPrice;
    @JsonProperty
    private List<InvoicesDto> invoicesDtoList;

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

    public List<InvoicesDto> getInvoicesDtoList() {
        return invoicesDtoList;
    }

    public void setInvoicesDtoList(List<InvoicesDto> invoicesDtoList) {
        this.invoicesDtoList = invoicesDtoList;
    }
}