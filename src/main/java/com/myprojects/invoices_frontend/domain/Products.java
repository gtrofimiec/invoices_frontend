package com.myprojects.invoices_frontend.domain;

import java.math.BigDecimal;
import java.util.List;

public class Products {

    private Long id;
    private String name;
    private String pkwiu;
    private Long quantity;
    private String measureUnit;
    private int vatRate;
    private BigDecimal netPrice;
    private BigDecimal vatValue;
    private BigDecimal grossPrice;
    private List<Invoices> invoicesList;

    public Products() {
    }

    public Products(Long id, String name, String pkwiu, String measureUnit, int vatRate,
                    BigDecimal netPrice, BigDecimal vatValue, BigDecimal grossPrice,
                    List<Invoices> invoicesList) {
        this.id = id;
        this.name = name;
        this.pkwiu = pkwiu;
        this.measureUnit = measureUnit;
        this.vatRate = vatRate;
        this.netPrice = netPrice;
        this.vatValue = vatValue;
        this.grossPrice = grossPrice;
        this.invoicesList = invoicesList;
    }

    public Products(Long id, String name, String pkwiu, String measureUnit, int vatRate,
                    BigDecimal netPrice, BigDecimal vatValue, BigDecimal grossPrice) {
        this.id = id;
        this.name = name;
        this.pkwiu = pkwiu;
        this.measureUnit = measureUnit;
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

    public String getPkwiu() {
        return pkwiu;
    }

    public void setPkwiu(String pkwiu) {
        this.pkwiu = pkwiu;
    }

    public Long getQuantity() {
        return quantity;
    }

    public void setQuantity(Long quantity) {
        this.quantity = quantity;
    }

    public String getMeasureUnit() {
        return measureUnit;
    }

    public void setMeasureUnit(String measureUnit) {
        this.measureUnit = measureUnit;
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

    @Override
    public String toString() {
        return name;
    }
}