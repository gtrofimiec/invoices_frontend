package com.myprojects.invoices_frontend.layout.grids;

import com.myprojects.invoices_frontend.domain.Customers;
import com.myprojects.invoices_frontend.domain.Invoices;
import com.myprojects.invoices_frontend.domain.Products;
import com.myprojects.invoices_frontend.domain.Users;
import com.myprojects.invoices_frontend.layout.MainView;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.grid.ColumnTextAlign;
import com.vaadin.flow.component.grid.Grid;

public class GridCreator extends FormLayout {

    private final MainView mainView;

    public GridCreator(MainView mainView) {
        this.mainView = mainView;
    }

    public void createSelectCustomerGrid() {
        mainView.gridSelectCustomer.removeAllColumns();
        mainView.gridSelectCustomer.addColumn(Customers::getFullName).setHeader("Pełna nazwa");
        mainView.gridSelectCustomer.addColumn(Customers::getNip).setHeader("NIP");
        mainView.gridSelectCustomer.addColumn(Customers::getStreet).setHeader("Ulica");
        mainView.gridSelectCustomer.addColumn(Customers::getPostCode).setHeader("Kod pocztowy");
        mainView.gridSelectCustomer.addColumn(Customers::getTown).setHeader("Miejscowość");
        mainView.gridSelectCustomer.addColumn(Customers::getMail).setHeader("E-mail");
    }

    public void createSelectProductGrid() {
        mainView.gridSelectProduct.removeAllColumns();
        mainView.gridSelectProduct.addColumn(Products::getName).setHeader("Nazwa");
        mainView.gridSelectProduct.addColumn(Products::getPkwiu).setHeader("PKWiU");
        mainView.gridSelectProduct.addColumn(Products::getMeasureUnit).setHeader("J.m.");
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
        mainView.gridInvoices.addColumn(Invoices::getDate).setHeader("Data");
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
        mainView.gridNewInvoiceProductsList.addColumn(Products::getQuantity).setHeader("Ilość");
        mainView.gridNewInvoiceProductsList.addColumn(Products::getPkwiu).setHeader("PKWiU");
        mainView.gridNewInvoiceProductsList.addColumn(Products::getMeasureUnit).setHeader("J.m.");
        mainView.gridNewInvoiceProductsList.addColumn(Products::getVatRate).setHeader("Stawka VAT")
                .setTextAlign(ColumnTextAlign.END);
        mainView.gridNewInvoiceProductsList.addColumn(Products::getNetPrice).setHeader("Cena netto")
                .setTextAlign(ColumnTextAlign.END);
        mainView.gridNewInvoiceProductsList.addColumn(Products::getVatValue).setHeader("VAT")
                .setTextAlign(ColumnTextAlign.END);
        mainView.gridNewInvoiceProductsList.addColumn(Products::getGrossPrice).setHeader("Cena brutto")
                .setTextAlign(ColumnTextAlign.END);
    }

    public Grid<Products> createEditInvoiceProductsList() {
        Grid<Products> newGrid = new Grid<>(Products.class);
        newGrid.removeAllColumns();
        newGrid.addColumn(Products::getName).setHeader("Nazwa");
        newGrid.addColumn(Products::getQuantity).setHeader("Ilość");
        newGrid.addColumn(Products::getPkwiu).setHeader("PKWiU");
        newGrid.addColumn(Products::getMeasureUnit).setHeader("J.m.");
        newGrid.addColumn(Products::getVatRate).setHeader("Stawka VAT")
                .setTextAlign(ColumnTextAlign.END);
        newGrid.addColumn(Products::getNetPrice).setHeader("Cena netto")
                .setTextAlign(ColumnTextAlign.END);
        newGrid.addColumn(Products::getVatValue).setHeader("VAT")
                .setTextAlign(ColumnTextAlign.END);
        newGrid.addColumn(Products::getGrossPrice).setHeader("Cena brutto")
                .setTextAlign(ColumnTextAlign.END);
        return newGrid;
    }

    public void createSelectUserGrid() {
        mainView.gridSelectUser.removeAllColumns();
        mainView.gridSelectUser.addColumn(Users::getFullName).setHeader("Pełna nazwa");
        mainView.gridSelectUser.addColumn(Users::getNip).setHeader("NIP");
        mainView.gridSelectUser.addColumn(Users::getStreet).setHeader("Ulica");
        mainView.gridSelectUser.addColumn(Users::getPostCode).setHeader("Kod pocztowy");
        mainView.gridSelectUser.addColumn(Users::getTown).setHeader("Miejscowość");
        mainView.gridSelectUser.addColumn(Users::isActive).setHeader("Domyślny");
        mainView.gridSelectUser.addColumn(Users::getBank).setHeader("Bank");
    }
}