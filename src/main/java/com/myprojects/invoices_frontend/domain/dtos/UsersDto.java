package com.myprojects.invoices_frontend.domain.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class UsersDto {

    @JsonProperty("user_id")
    private Long id;
    @JsonProperty("full_name")
    private String fullName;
    @JsonProperty("nip")
    private String nip;
    @JsonProperty("street")
    private String street;
    @JsonProperty("postcode")
    private String postcode;
    @JsonProperty("town")
    private String town;
    @JsonProperty("active")
    private boolean active;
    @JsonProperty("invoices_list")
    private List<InvoicesDto> invoicesDtoList;

    public UsersDto() {
    }

    public UsersDto(Long id, String fullName, String nip, String street, String postcode, String town,
                    boolean active, List<InvoicesDto> invoicesDtoList) {
        this.id = id;
        this.fullName = fullName;
        this.nip = nip;
        this.street = street;
        this.postcode = postcode;
        this.town = town;
        this.active = active;
        this.invoicesDtoList = invoicesDtoList;
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

    public String getPostcode() {
        return postcode;
    }

    public void setPostcode(String postcode) {
        this.postcode = postcode;
    }

    public String getTown() {
        return town;
    }

    public void setTown(String town) {
        this.town = town;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public List<InvoicesDto> getInvoicesDtoList() {
        return invoicesDtoList;
    }

    public void setInvoicesDtoList(List<InvoicesDto> invoicesDtoList) {
        this.invoicesDtoList = invoicesDtoList;
    }
}