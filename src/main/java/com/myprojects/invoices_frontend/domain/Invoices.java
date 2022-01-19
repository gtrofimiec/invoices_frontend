package com.myprojects.invoices_frontend.domain;

import java.util.Date;
import java.util.Objects;

public class Invoices {

    private Long id;
    private String number;
    private Date date;
    private Customers customer;

    public Invoices() {
    }

    public Invoices(Long id, String number, Date date, Customers customer) {
        this.id = id;
        this.number = number;
        this.date = date;
        this.customer = customer;
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

    public Customers getCustomer() {
        return customer;
    }

    public void setCustomer(Customers customer) {
        this.customer = customer;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Invoices)) return false;
        Invoices invoices = (Invoices) o;
        return id.equals(invoices.id) && number.equals(invoices.number) && date.equals(invoices.date) && customer.equals(invoices.customer);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, number, date, customer);
    }
}