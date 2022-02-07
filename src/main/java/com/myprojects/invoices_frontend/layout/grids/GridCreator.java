package com.myprojects.invoices_frontend.layout.grids;

import com.myprojects.invoices_frontend.MainView;
import com.myprojects.invoices_frontend.domain.Customers;
import com.myprojects.invoices_frontend.domain.Invoices;
import com.myprojects.invoices_frontend.domain.Products;
import com.myprojects.invoices_frontend.domain.Users;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.grid.ColumnTextAlign;

public class GridCreator extends FormLayout {

    private final MainView mainView;

    public GridCreator(MainView mainView) {
        this.mainView = mainView;
    }

    public void createCustomerGrid() {
        mainView.gridCustomers.removeAllColumns();
        mainView.gridCustomers.addColumn(Customers::getFullName).setHeader("Pełna nazwa");
        mainView.gridCustomers.addColumn(Customers::getNip).setHeader("NIP");
        mainView.gridCustomers.addColumn(Customers::getStreet).setHeader("Ulica");
        mainView.gridCustomers.addColumn(Customers::getPostCode).setHeader("Kod pocztowy");
        mainView.gridCustomers.addColumn(Customers::getTown).setHeader("Miejscowość");
    }

    public void createSelectCustomerGrid() {
        mainView.gridSelectCustomer.removeAllColumns();
        mainView.gridSelectCustomer.addColumn(Customers::getFullName).setHeader("Pełna nazwa");
        mainView.gridSelectCustomer.addColumn(Customers::getNip).setHeader("NIP");
        mainView.gridSelectCustomer.addColumn(Customers::getStreet).setHeader("Ulica");
        mainView.gridSelectCustomer.addColumn(Customers::getPostCode).setHeader("Kod pocztowy");
        mainView.gridSelectCustomer.addColumn(Customers::getTown).setHeader("Miejscowość");
    }

    public void createProductGrid() {
        mainView.gridProducts.removeAllColumns();
        mainView.gridProducts.addColumn(Products::getName).setHeader("Nazwa");
        mainView.gridProducts.addColumn(Products::getVatRate).setHeader("Stawka VAT")
                .setTextAlign(ColumnTextAlign.END);
        mainView.gridProducts.addColumn(Products::getNetPrice).setHeader("Cena netto")
                .setTextAlign(ColumnTextAlign.END);
        mainView.gridProducts.addColumn(Products::getVatValue).setHeader("VAT")
                .setTextAlign(ColumnTextAlign.END);
        mainView.gridProducts.addColumn(Products::getGrossPrice).setHeader("Cena brutto")
                .setTextAlign(ColumnTextAlign.END);
    }

    public void createSelectProductGrid() {
        mainView.gridSelectProduct.removeAllColumns();
        mainView.gridSelectProduct.addColumn(Products::getName).setHeader("Nazwa");
        mainView.gridSelectProduct.addColumn(Products::getVatRate).setHeader("Stawka VAT")
                .setTextAlign(ColumnTextAlign.END);
        mainView.gridSelectProduct.addColumn(Products::getNetPrice).setHeader("Cena netto")
                .setTextAlign(ColumnTextAlign.END);
        mainView.gridSelectProduct.addColumn(Products::getVatValue).setHeader("VAT")
                .setTextAlign(ColumnTextAlign.END);
        mainView.gridSelectProduct.addColumn(Products::getGrossPrice).setHeader("Cena brutto")
                .setTextAlign(ColumnTextAlign.END);
    }

    public void createInvoiceGrid() {
        mainView.gridInvoices.removeAllColumns();
        mainView.gridInvoices.addColumn(Invoices::getNumber).setHeader("Numer");
        mainView.gridInvoices.addColumn(Invoices::getFormattedDate).setHeader("Data");
        mainView.gridInvoices.addColumn(Invoices::getCustomer).setHeader("Kontrahent");
        mainView.gridInvoices.addColumn(Invoices::getGrossSum).setHeader("Wartość brutto")
                .setTextAlign(ColumnTextAlign.END);
        mainView.gridInvoices.addColumn(Invoices::getVatSum).setHeader("Wartość VAT")
                .setTextAlign(ColumnTextAlign.END);
        mainView.gridInvoices.addColumn(Invoices::getNetSum).setHeader("Wartość netto")
                .setTextAlign(ColumnTextAlign.END);
        mainView.gridInvoices.addColumn(Invoices::getPaymentMethod).setHeader("Forma płatności");
        mainView.gridInvoices.addColumn(Invoices::getUser).setHeader("Użytkownik");
        mainView.gridInvoices.addColumn(Invoices::getProductsList).setHeader("Lista produktów");
    }

    public void createNewInvoiceProductsList() {
        mainView.gridNewInvoiceProductsList.removeAllColumns();
        mainView.gridNewInvoiceProductsList.addColumn(Products::getName).setHeader("Nazwa");
        mainView.gridNewInvoiceProductsList.addColumn(Products::getVatRate).setHeader("Stawka VAT")
                .setTextAlign(ColumnTextAlign.END);
        mainView.gridNewInvoiceProductsList.addColumn(Products::getNetPrice).setHeader("Cena netto")
                .setTextAlign(ColumnTextAlign.END);
        mainView.gridNewInvoiceProductsList.addColumn(Products::getVatValue).setHeader("VAT")
                .setTextAlign(ColumnTextAlign.END);
        mainView.gridNewInvoiceProductsList.addColumn(Products::getGrossPrice).setHeader("Cena brutto")
                .setTextAlign(ColumnTextAlign.END);
    }

    public void createUserGrid() {
        mainView.gridUser.removeAllColumns();
        mainView.gridUser.addColumn(Users::getFullName).setHeader("Pełna nazwa");
        mainView.gridUser.addColumn(Users::getNip).setHeader("NIP");
        mainView.gridUser.addColumn(Users::getStreet).setHeader("Ulica");
        mainView.gridUser.addColumn(Users::getPostCode).setHeader("Kod pocztowy");
        mainView.gridUser.addColumn(Users::getTown).setHeader("Miejscowość");
        mainView.gridUser.addColumn(Users::isActive).setHeader("Domyślny");
    }

    public void createSelectUserGrid() {
        mainView.gridSelectUser.removeAllColumns();
        mainView.gridSelectUser.addColumn(Users::getFullName).setHeader("Pełna nazwa");
        mainView.gridSelectUser.addColumn(Users::getNip).setHeader("NIP");
        mainView.gridSelectUser.addColumn(Users::getStreet).setHeader("Ulica");
        mainView.gridSelectUser.addColumn(Users::getPostCode).setHeader("Kod pocztowy");
        mainView.gridSelectUser.addColumn(Users::getTown).setHeader("Miejscowość");
        mainView.gridSelectUser.addColumn(Users::isActive).setHeader("Domyślny");
    }
}