package com.myprojects.invoices_frontend.domain;

import java.util.List;

public class Customers {

    private Long id;
    private String fullName;
    private String nip;
    private String street;
    private String postCode;
    private String town;
    private String mail;
    private List<Invoices> invoicesList;

    public Customers() {
    }

    public Customers(Long id, String fullName, String nip, String street, String postCode, String town,
                     String mail, List<Invoices> invoicesList) {
        this.id = id;
        this.fullName = fullName;
        this.nip = nip;
        this.street = street;
        this.postCode = postCode;
        this.town = town;
        this.mail = mail;
        this.invoicesList = invoicesList;
    }

    public Customers(Long id, String fullName, String nip, String street, String postCode, String town,
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

    public List<Invoices> getInvoicesList() {
        return invoicesList;
    }

    public void setInvoicesList(List<Invoices> invoicesList) {
        this.invoicesList = invoicesList;
    }

    @Override
    public String toString() {
        return fullName;
    }
}