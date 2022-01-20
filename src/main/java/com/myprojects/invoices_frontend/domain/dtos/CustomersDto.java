package com.myprojects.invoices_frontend.domain.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.Objects;

@Data
public class CustomersDto {

    @JsonProperty("customer_id")
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

    public CustomersDto() {
    }

    public CustomersDto(Long id, String fullName, String nip, String street, String postcode, String town) {
        this.id = id;
        this.fullName = fullName;
        this.nip = nip;
        this.street = street;
        this.postcode = postcode;
        this.town = town;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CustomersDto that = (CustomersDto) o;
        return id.equals(that.id) && fullName.equals(that.fullName) && nip.equals(that.nip) && street.equals(that.street) && postcode.equals(that.postcode) && town.equals(that.town);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, fullName, nip, street, postcode, town);
    }
}