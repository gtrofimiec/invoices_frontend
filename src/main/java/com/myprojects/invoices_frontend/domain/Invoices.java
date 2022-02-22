package com.myprojects.invoices_frontend.domain;

import lombok.Data;
import org.jetbrains.annotations.NotNull;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Data
public class Invoices {

    private Long id;
    private String number;
    private LocalDate date;
    private BigDecimal netSum;
    private BigDecimal vatSum;
    private BigDecimal grossSum;
    private String paymentMethod;
    private LocalDate paymentDate;
    private Customers customer;
    private Users user;
    private List<Products> productsList;

    public Invoices() {
    }

    public Invoices(Long id, String number, LocalDate date, BigDecimal netSum, BigDecimal vatSum,
                    BigDecimal grossSum, String paymentMethod, LocalDate paymentDate, Customers customer,
                    Users user, List<Products> productsList) {
        this.id = id;
        this.number = number;
        this.date = date;
        this.netSum = netSum;
        this.vatSum = vatSum;
        this.grossSum = grossSum;
        this.paymentMethod = paymentMethod;
        this.paymentDate = paymentDate;
        this.customer = customer;
        this.user = user;
        this.productsList = productsList;
    }

    public Invoices(String number, LocalDate date, BigDecimal netSum, BigDecimal vatSum, BigDecimal grossSum,
                    String paymentMethod, LocalDate paymentDate, Customers customer, Users user,
                    List<Products> productsList) {
        this.number = number;
        this.date = date;
        this.netSum = netSum;
        this.vatSum = vatSum;
        this.grossSum = grossSum;
        this.paymentMethod = paymentMethod;
        this.paymentDate = paymentDate;
        this.customer = customer;
        this.user = user;
        this.productsList = productsList;
    }

    public Invoices(Long id, String number, LocalDate date, BigDecimal netSum, BigDecimal vatSum,
                    BigDecimal grossSum, String paymentMethod, LocalDate paymentDate, Customers customer,
                    Users user) {
        this.id = id;
        this.number = number;
        this.date = date;
        this.netSum = netSum;
        this.vatSum = vatSum;
        this.grossSum = grossSum;
        this.paymentMethod = paymentMethod;
        this.paymentDate = paymentDate;
        this.customer = customer;
        this.user = user;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
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

    public String getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(String paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    public LocalDate getPaymentDate() {
        return paymentDate;
    }

    public void setPaymentDate(LocalDate paymentDate) {
        this.paymentDate = paymentDate;
    }

    public Customers getCustomer() {
        return customer;
    }

    public void setCustomer(Customers customer) {
        this.customer = customer;
    }

    public Users getUser() {
        return user;
    }

    public void setUser(Users user) {
        this.user = user;
    }

    public List<Products> getProductsList() {
        return productsList;
    }

    public void setProductsList(List<Products> productsList) {
        this.productsList = productsList;
    }

    public Long getProductQuantity(Products product, @NotNull List<Products> productsList) {
        return productsList.stream()
                .filter(p -> p.equals(product))
                .count();
    }

    @Override
    public String toString() {
        return number;
    }
}