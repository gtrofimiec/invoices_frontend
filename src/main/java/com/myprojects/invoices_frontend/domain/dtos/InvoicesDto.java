package com.myprojects.invoices_frontend.domain.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.myprojects.invoices_frontend.domain.Customers;
import com.myprojects.invoices_frontend.domain.Users;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

public class InvoicesDto {

    @JsonProperty("invoice_id")
    private Long id;
    @JsonProperty("number")
    private String number;
    @JsonProperty("date")
    private Date date;
    @JsonProperty("net_sum")
    private BigDecimal netSum;
    @JsonProperty("vat_sum")
    private BigDecimal vatSum;
    @JsonProperty("gross_sum")
    private BigDecimal grossSum;
    @JsonProperty("payment_method")
    private String paymentMethod;
    @JsonProperty("customer_id")
    private CustomersDto customerDto;
    @JsonProperty("user_id")
    private UsersDto userDto;
    @JsonProperty("products_list")
    private List<ProductsDto> productsDtoList;

    public InvoicesDto(Long id, String number, Date date, BigDecimal netSum, BigDecimal vatSum, BigDecimal grossSum,
                       String paymentMethod, CustomersDto customerDto, UsersDto userDto,
                       List<ProductsDto> productsDtoList) {
        this.id = id;
        this.number = number;
        this.date = date;
        this.netSum = netSum;
        this.vatSum = vatSum;
        this.grossSum = grossSum;
        this.paymentMethod = paymentMethod;
        this.customerDto = customerDto;
        this.userDto = userDto;
        this.productsDtoList = productsDtoList;
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

    public CustomersDto getCustomerDto() {
        return customerDto;
    }

    public void setCustomerDto(CustomersDto customerDto) {
        this.customerDto = customerDto;
    }

    public UsersDto getUserDto() {
        return userDto;
    }

    public void setUserDto(UsersDto userDto) {
        this.userDto = userDto;
    }

    public List<ProductsDto> getProductsDtoList() {
        return productsDtoList;
    }

    public void setProductsDtoList(List<ProductsDto> productsDtoList) {
        this.productsDtoList = productsDtoList;
    }
}