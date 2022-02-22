package com.myprojects.invoices_frontend.domain.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

public class InvoicesDto {

    @JsonProperty
    private Long id;
    @JsonProperty
    private String number;
    @JsonProperty
    private LocalDate date;
    @JsonProperty
    private BigDecimal netSum;
    @JsonProperty
    private BigDecimal vatSum;
    @JsonProperty
    private BigDecimal grossSum;
    @JsonProperty
    private String paymentMethod;
    @JsonProperty
    private LocalDate paymentDate;
    @JsonProperty
    private CustomersDto customerDto;
    @JsonProperty
    private UsersDto userDto;
    @JsonProperty
    private List<ProductsDto> productsDtoList;

    public InvoicesDto() {
    }

    public InvoicesDto(Long id, String number, LocalDate date, BigDecimal netSum, BigDecimal vatSum,
                       BigDecimal grossSum, String paymentMethod, LocalDate paymentDate,
                       CustomersDto customerDto, UsersDto userDto, List<ProductsDto> productsDtoList) {
        this.id = id;
        this.number = number;
        this.date = date;
        this.netSum = netSum;
        this.vatSum = vatSum;
        this.grossSum = grossSum;
        this.paymentMethod = paymentMethod;
        this.paymentDate = paymentDate;
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