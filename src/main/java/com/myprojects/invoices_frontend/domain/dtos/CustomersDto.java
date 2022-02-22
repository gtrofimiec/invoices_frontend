package com.myprojects.invoices_frontend.domain.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class CustomersDto {

    @JsonProperty
    private Long id;
    @JsonProperty
    private String fullName;
    @JsonProperty
    private String nip;
    @JsonProperty
    private String street;
    @JsonProperty
    private String postCode;
    @JsonProperty
    private String town;
    @JsonProperty
    private String mail;
    @JsonProperty
    private List<InvoicesDto> invoicesDtoList;

    public CustomersDto() {
    }
//
    public CustomersDto(Long id, String fullName, String nip, String street, String postCode, String town,
                        String mail) {
        this.id = id;
        this.fullName = fullName;
        this.nip = nip;
        this.street = street;
        this.postCode = postCode;
        this.town = town;
        this.mail = mail;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getNip() {
        return nip;
    }

    public void setNip(String nip) {
        this.nip = nip;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getPostCode() {
        return postCode;
    }

    public void setPostCode(String postCode) {
        this.postCode = postCode;
    }

    public String getTown() {
        return town;
    }

    public void setTown(String town) {
        this.town = town;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public List<InvoicesDto> getInvoicesDtoList() {
        return invoicesDtoList;
    }

    public void setInvoicesDtoList(List<InvoicesDto> invoicesDtoList) {
        this.invoicesDtoList = invoicesDtoList;
    }
}