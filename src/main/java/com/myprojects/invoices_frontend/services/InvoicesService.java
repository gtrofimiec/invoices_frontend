package com.myprojects.invoices_frontend.services;

import com.myprojects.invoices_frontend.clients.InvoicesClient;
import com.myprojects.invoices_frontend.domain.Invoices;
import com.myprojects.invoices_frontend.domain.Products;
import com.myprojects.invoices_frontend.mappers.InvoicesMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class InvoicesService {

    private List<Invoices> invoicesList;
    private List<Products> productsList;
    private static InvoicesClient invoicesClient;
    private static InvoicesService invoicesService;
    private static InvoicesMapper invoicesMapper = new InvoicesMapper();

    public InvoicesService(InvoicesClient invoicesClient) {
        InvoicesService.invoicesClient = invoicesClient;
    }

    public static InvoicesService getInstance() {
        if (invoicesService == null) {
            invoicesService = new InvoicesService(invoicesClient);
        }
        return invoicesService;
    }

    public Set<Invoices> findByNumber(String number) {
        return invoicesList.stream()
                .filter(i -> i.getNumber().contains(number))
                .collect(Collectors.toSet());
    }

    public List<Invoices> getInvoicesList() {
        invoicesList = invoicesMapper.mapToInvoicesList(invoicesClient.getInvoices());
        return invoicesList;
    }

    public void saveInvoice(Invoices invoice) {
        invoicesClient.saveInvoice(invoicesMapper.mapToInvoiceDto(invoice));
    }

    public void addProductToInvoice(Products product) {
        productsList.add(product);
    }

    public void updateInvoice(Invoices invoice) {
        invoicesClient.updateInvoice(invoicesMapper.mapToInvoiceDto(invoice));
    }

    public void deleteInvoice(Invoices invoice) {
        invoicesClient.deleteInvoice(invoicesMapper.mapToInvoiceDto(invoice));
    }
}