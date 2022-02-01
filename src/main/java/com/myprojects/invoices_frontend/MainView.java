package com.myprojects.invoices_frontend;

import com.myprojects.invoices_frontend.domain.Customers;
import com.myprojects.invoices_frontend.domain.Invoices;
import com.myprojects.invoices_frontend.domain.Products;
import com.myprojects.invoices_frontend.domain.Users;
import com.myprojects.invoices_frontend.layout.forms.*;
import com.myprojects.invoices_frontend.services.CustomersService;
import com.myprojects.invoices_frontend.services.InvoicesService;
import com.myprojects.invoices_frontend.services.ProductsService;
import com.myprojects.invoices_frontend.services.UsersService;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.Route;
import org.jetbrains.annotations.NotNull;

import java.util.List;

@Route
public class MainView extends VerticalLayout {

    private CustomersService customersService = CustomersService.getInstance();
    private ProductsService productsService = ProductsService.getInstance();
    private InvoicesService invoicesService = InvoicesService.getInstance();
    private UsersService userService = UsersService.getInstance();
//    public Users activeUser = new Users();

    public Grid<Customers> gridCustomers = new Grid<>(Customers.class);
    public Grid<Customers> gridSelectCustomer = new Grid<>(Customers.class);
    public Grid<Products> gridProducts = new Grid<>(Products.class);
    public Grid<Products> gridSelectProduct = new Grid<>(Products.class);
    public Grid<Products> gridNewInvoiceProductsList = new Grid<>(Products.class);
    public Grid<Invoices> gridInvoices = new Grid<>(Invoices.class);
    public Grid<Users> gridUser = new Grid<>(Users.class);
    public Grid<Users> gridSelectUser = new Grid<>(Users.class);
    public TextField txtCustomersFilter = new TextField();
    public TextField txtProductsFilter = new TextField();
    public TextField txtInvoicesFilter = new TextField();
    public TextField txtUserFilter = new TextField();
    private CustomersForm customersForm = new CustomersForm(this);
    private ProductsForm productsForm = new ProductsForm(this);
    private InvoicesForm invoicesForm = new InvoicesForm(this);
    private NewInvoiceForm newInvoiceForm = new NewInvoiceForm(this);
    private UserForm userForm = new UserForm(this);
    private Button btnCustomers = new Button("Kontrahenci");
    private Button btnProducts = new Button("Produkty");
    private Button btnInvoices = new Button("Faktury");
    private Button btnUser = new Button("Użytkownik");
    private Button btnAddNewCustomer = new Button("Dodaj kontahenta ...");
    private Button btnAddNewProduct = new Button("Dodaj produkt ...");
    private Button btnAddNewInvoice = new Button("Dodaj fakturę ...");
    private Button btnEditInvoice = new Button("Edytuj fakturę ...");
    private Button btnAddNewUser = new Button("Dodaj użytkownika ...");
    public HorizontalLayout mainContent = new HorizontalLayout();
    private HorizontalLayout mainToolbar = new HorizontalLayout();
    public HorizontalLayout itemsToolbar = new HorizontalLayout();
    public HorizontalLayout newInvoiceHeaderToolbar = new HorizontalLayout();
    public HorizontalLayout newInvoiceFooterToolbar = new HorizontalLayout();

    public MainView() {

        gridCustomers.setColumns("fullName", "nip", "street", "postCode", "town");
        gridSelectCustomer.setColumns("fullName", "nip", "street", "postCode", "town");
        gridProducts.setColumns("name", "vatRate", "netPrice", "vatValue", "grossPrice");
        gridSelectProduct.setColumns("name", "vatRate", "netPrice", "vatValue", "grossPrice");
        gridNewInvoiceProductsList.setColumns("name", "vatRate", "netPrice", "vatValue", "grossPrice");
        gridInvoices.setColumns("number", "date", "customer", "grossSum", "vatSum", "netSum", "paymentMethod", "user");
        gridUser.setColumns("fullName", "nip", "street", "postCode", "town", "active");
        gridSelectUser.setColumns("fullName", "nip", "street", "postCode", "town", "active");

        gridCustomers.setVisible(false);
        gridSelectCustomer.setVisible(false);
        gridProducts.setVisible(false);
        gridSelectProduct.setVisible(false);
        gridNewInvoiceProductsList.setVisible(false);
        gridInvoices.setVisible(false);
        gridUser.setVisible(false);
        gridSelectUser.setVisible(false);

        btnAddNewCustomer.setVisible(false);
        btnAddNewProduct.setVisible(false);
        btnAddNewInvoice.setVisible(false);
        btnAddNewCustomer.setVisible(false);
        btnEditInvoice.setVisible(false);

        newInvoiceHeaderToolbar.setVisible(false);
        newInvoiceFooterToolbar.setVisible(false);

        menuButtonClick(btnCustomers, gridCustomers, btnAddNewCustomer, customersForm, txtCustomersFilter);
        menuButtonClick(btnProducts, gridProducts, btnAddNewProduct, productsForm, txtProductsFilter);
        menuButtonClick(btnUser, gridUser, btnAddNewUser, userForm, txtUserFilter);

        invoiceMenuClick();

        mainToolbar.add(btnCustomers, btnProducts, btnInvoices, btnUser);

        newInvoiceHeaderToolbar.add(
                newInvoiceForm.txtNumber,
                newInvoiceForm.txtDate,
                newInvoiceForm.txtUser,
                newInvoiceForm.btnAddUser,
                newInvoiceForm.txtCustomer,
                newInvoiceForm.btnAddCustomer,
                newInvoiceForm.txtPayment
        );

        newInvoiceFooterToolbar.add(
                newInvoiceForm.txtNetSum,
                newInvoiceForm.txtVatSum,
                newInvoiceForm.txtGrossSum,
                newInvoiceForm.btnAddProduct,
                newInvoiceForm.btnSave,
                newInvoiceForm.btnCancel,
                newInvoiceForm.btnSelectProduct,
                newInvoiceForm.btnSelectCustomer,
                newInvoiceForm.btnSelectUser
        );

        btnAddNewCustomer.addClickListener(e -> {
            gridCustomers.asSingleSelect().clear();
            customersForm.updateForm(new Customers());
        });
        btnAddNewProduct.addClickListener(e -> {
            gridProducts.asSingleSelect().clear();
            productsForm.updateForm(new Products());
        });
        btnEditInvoice.addClickListener(e -> {
            gridInvoices.asSingleSelect().clear();
            btnEditInvoice.setVisible(true);
            invoicesForm.updateForm(new Invoices());
        });
        btnAddNewInvoice.addClickListener(e -> {
            mainContent.removeAll();
            mainContent.add(gridNewInvoiceProductsList);
            gridNewInvoiceProductsList.asSingleSelect().clear();
            gridNewInvoiceProductsList.setVisible(true);
            itemsToolbar.setVisible(false);
            newInvoiceHeaderToolbar.setVisible(true);
            newInvoiceFooterToolbar.setVisible(true);
//            newInvoiceForm.updateForm(new Invoices());
        });
        btnAddNewUser.addClickListener(e -> {
            gridUser.asSingleSelect().clear();
            userForm.updateForm(new Users());
        });

        mainContent.setSizeFull();
        gridCustomers.setSizeFull();
        gridSelectCustomer.setSizeFull();
        gridProducts.setSizeFull();
        gridInvoices.setSizeFull();
        gridUser.setSizeFull();
        gridSelectUser.setSizeFull();

        add(mainToolbar, itemsToolbar, newInvoiceHeaderToolbar, mainContent, newInvoiceFooterToolbar);
        customersForm.updateForm(null);
        productsForm.updateForm(null);
        invoicesForm.updateForm(null);
        userForm.updateForm(null);
        setSizeFull();
        refresh();

        gridCustomers.asSingleSelect().addValueChangeListener(event ->
                customersForm.updateForm(gridCustomers.asSingleSelect().getValue()));
        gridProducts.asSingleSelect().addValueChangeListener(event ->
                productsForm.updateForm(gridProducts.asSingleSelect().getValue()));
        gridInvoices.asSingleSelect().addValueChangeListener(event ->
            invoicesForm.updateForm(gridInvoices.asSingleSelect().getValue()));
        gridUser.asSingleSelect().addValueChangeListener(event ->
                userForm.updateForm(gridUser.asSingleSelect().getValue()));
    }

    public void refresh() {
        gridCustomers.setItems(customersService.getCustomersList());
        gridSelectCustomer.setItems(gridCustomers.getSelectedItems());
        gridProducts.setItems(productsService.getProductsList());
        gridInvoices.setItems(invoicesService.getInvoicesList());
        List<Users> usersList = userService.getUsersList();
        gridUser.setItems(usersList);
        gridSelectUser.setItems(gridUser.getSelectedItems());
//        activeUser = userService.getActiveUser();
    }

    public void menuButtonClick(@NotNull Button menuButton, Grid grid, Button button,
                                FormLayout form, TextField filter) {
        menuButton.addClickListener(e -> {
            mainContent.removeAll();
            mainContent.add(grid, form);
            itemsToolbar.removeAll();
            itemsToolbar.add(button, filter);
            if(!itemsToolbar.isVisible()) {
                itemsToolbar.setVisible(true);
            }
            grid.setVisible(true);
            button.setVisible(true);
            filter.setVisible(true);
            if(newInvoiceHeaderToolbar.isVisible()) {
                newInvoiceHeaderToolbar.setVisible(false);
            }
            if(newInvoiceFooterToolbar.isVisible()) {
                newInvoiceFooterToolbar.setVisible(false);
            }
        });
    }

    public void invoiceMenuClick() {
        btnInvoices.addClickListener(e -> {
            mainContent.removeAll();
            mainContent.add(gridInvoices);
            itemsToolbar.removeAll();
            itemsToolbar.add(btnAddNewInvoice, btnEditInvoice, txtInvoicesFilter);
            gridInvoices.setVisible(true);
            gridInvoices.setItems(invoicesService.getInvoicesList());
            btnAddNewInvoice.setVisible(true);
            txtInvoicesFilter.setVisible(true);
        });
    }
}