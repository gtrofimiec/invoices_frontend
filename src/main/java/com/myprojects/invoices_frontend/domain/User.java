package com.myprojects.invoices_frontend.domain;

import java.util.Objects;


public class User {

    private Long id;
    private String fullName;
    private String nip;
    private String street;
    private String postcode;
    private String town;

    public User() {
    }

    public User(Long id, String fullName, String nip, String street, String postcode, String town) {
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
        if (!(o instanceof User)) return false;
        User user = (User) o;
        return id.equals(user.id) && fullName.equals(user.fullName) && nip.equals(user.nip) && street.equals(user.street) && postcode.equals(user.postcode) && town.equals(user.town);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, fullName, nip, street, postcode, town);
    }
}