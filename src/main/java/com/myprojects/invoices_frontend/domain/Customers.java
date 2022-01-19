package com.myprojects.invoices_frontend.domain;

import lombok.Data;

import java.util.Objects;

@Data
public class Customers {

    private Long id;
    private String fullName;
    private String nip;
    private String street;
    private String postcode;
    private String town;

    public Customers() {
    }

    public Customers(Long id, String fullName, String nip, String street, String postcode, String town) {
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

    public String getFullName() {
        return fullName;
    }

    public String getNip() {
        return nip;
    }

    public String getStreet() {
        return street;
    }

    public String getPostcode() {
        return postcode;
    }

    public String getTown() {
        return town;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public void setNip(String nip) {
        this.nip = nip;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public void setPostcode(String postcode) {
        this.postcode = postcode;
    }

    public void setTown(String town) {
        this.town = town;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Customers)) return false;
        Customers customers = (Customers) o;
        return id.equals(customers.id) && fullName.equals(customers.fullName) && nip.equals(customers.nip) && street.equals(customers.street) && postcode.equals(customers.postcode) && town.equals(customers.town);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, fullName, nip, street, postcode, town);
    }
}