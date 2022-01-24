package com.myprojects.invoices_frontend.services;

import com.myprojects.invoices_frontend.domain.Customers;
import com.myprojects.invoices_frontend.domain.Invoices;
import com.vaadin.flow.data.binder.Binder;
import org.jetbrains.annotations.NotNull;

import java.sql.Date;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

public class InvoicesService {

    private Set<Invoices> invoices;
    private static InvoicesService invoicesService;
    private Binder<Invoices> binder = new Binder<>(Invoices.class);

    public InvoicesService() {
        this.invoices = exampleData();
    }

    public static InvoicesService getInstance() {
        if (invoicesService == null) {
            invoicesService = new InvoicesService();
        }
        return invoicesService;
    }

    public Set<Invoices> findByNumber(String number) {
        return invoices.stream()
                .filter(i -> i.getNumber().contains(number))
                .collect(Collectors.toSet());
    }

    public Set<Invoices> getInvoices() {
        return new HashSet<>(invoices);
    }

    private @NotNull Set<Invoices> exampleData() {
        Set<Invoices> invoices = new HashSet<>();
        invoices.add(new Invoices(1L, "01/2022", new Date(2022-01-14),
                new Customers()));
        return invoices;
    }

    public void save(Invoices invoice) {
        this.invoices.add(invoice);
    }

    public void delete(Invoices invoice) {
        this.invoices.remove(invoice);
    }
}