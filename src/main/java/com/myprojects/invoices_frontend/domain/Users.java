package com.myprojects.invoices_frontend.domain;

import java.util.List;

public class Users {

    private Long id;
    private String fullName;
    private String nip;
    private String street;
    private String postCode;
    private String town;
    private String bank;
    private String accountNumber;
    private String pdfPath;
    private String userLogin;
    private String userPass;
    private boolean active;
    private List<Invoices> invoicesList;

    public Users() {
    }

    public Users(boolean active) {
        this.active = active;
    }

    public Users(Long id, String fullName, String nip, String street, String postCode, String town,
                 String bank, String accountNumber, String pdfPath, String userLogin, String userPass, boolean active) {
        this.id = id;
        this.fullName = fullName;
        this.nip = nip;
        this.street = street;
        this.postCode = postCode;
        this.town = town;
        this.bank = bank;
        this.accountNumber = accountNumber;
        this.pdfPath = pdfPath;
        this.userLogin = userLogin;
        this.userPass = userPass;
        this.active = active;
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

    public String getBank() {
        return bank;
    }

    public void setBank(String bank) {
        this.bank = bank;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    public String getPdfPath() {
        return pdfPath;
    }

    public void setPdfPath(String pdfPath) {
        this.pdfPath = pdfPath;
    }

    public String getUserLogin() {
        return userLogin;
    }

    public void setUserLogin(String userLogin) {
        this.userLogin = userLogin;
    }

    public String getUserPass() {
        return userPass;
    }

    public void setUserPass(String userPass) {
        this.userPass = userPass;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
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