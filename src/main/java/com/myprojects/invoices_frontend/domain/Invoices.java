package com.myprojects.invoices_frontend.domain;

import lombok.Data;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Data
public class Invoices {

    private Long id;
    private String number;
    private Date date;
    private BigDecimal netSum;
    private BigDecimal vatSum;
    private BigDecimal grossSum;
    private String paymentMethod;
    private Customers customer;
    private Users user;
    private List<Products> productsList;

    public Invoices() {
    }

    public Invoices(Long id, String number, Date date, BigDecimal netSum, BigDecimal vatSum, BigDecimal grossSum,
                    String paymentMethod, Customers customer, Users user, List<Products> productsList) {
        this.id = id;
        this.number = number;
        this.date = date;
        this.netSum = netSum;
        this.vatSum = vatSum;
        this.grossSum = grossSum;
        this.paymentMethod = paymentMethod;
        this.customer = customer;
        this.user = user;
        this.productsList = productsList;
    }

    public Invoices(String number, Date date, BigDecimal netSum, BigDecimal vatSum, BigDecimal grossSum,
                    String paymentMethod, Customers customer, Users user, List<Products> productsList) {
        this.number = number;
        this.date = date;
        this.netSum = netSum;
        this.vatSum = vatSum;
        this.grossSum = grossSum;
        this.paymentMethod = paymentMethod;
        this.customer = customer;
        this.user = user;
        this.productsList = productsList;
    }

    public Invoices(Long id, String number, Date date, BigDecimal netSum, BigDecimal vatSum,
                    BigDecimal grossSum, String paymentMethod, Customers customer, Users user) {
        this.id = id;
        this.number = number;
        this.date = date;
        this.netSum = netSum;
        this.vatSum = vatSum;
        this.grossSum = grossSum;
        this.paymentMethod = paymentMethod;
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

    public Date getDate() {
        return date;
    }

    public String getFormattedDate() {
        SimpleDateFormat formatter = new SimpleDateFormat(
                "yyyy-MM-dd");
        return formatter.format(date);
    }

    public void setDate(Date date) {
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

    @Override
    public String toString() {
        return number;
    }
}