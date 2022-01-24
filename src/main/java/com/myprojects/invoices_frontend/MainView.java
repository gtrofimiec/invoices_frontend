package com.myprojects.invoices_frontend;

import com.myprojects.invoices_frontend.domain.Customers;
import com.myprojects.invoices_frontend.domain.Invoices;
import com.myprojects.invoices_frontend.domain.Products;
import com.myprojects.invoices_frontend.domain.Users;
import com.myprojects.invoices_frontend.layout.forms.CustomersForm;
import com.myprojects.invoices_frontend.layout.forms.InvoicesForm;
import com.myprojects.invoices_frontend.layout.forms.ProductsForm;
import com.myprojects.invoices_frontend.layout.forms.UserForm;
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

@Route
public class MainView extends VerticalLayout {

    private CustomersService customersService = CustomersService.getInstance();
    private ProductsService productsService = ProductsService.getInstance();
    private InvoicesService invoicesService = InvoicesService.getInstance();
    private UsersService userService = UsersService.getInstance();

    public Grid<Customers> gridCustomers = new Grid<>(Customers.class);
    public Grid<Products> gridProducts = new Grid<>(Products.class);
    public Grid<Invoices> gridInvoices = new Grid<>(Invoices.class);
    public Grid<Users> gridUser = new Grid<>(Users.class);
    public TextField txtCustomersFilter = new TextField();
    public TextField txtProductsFilter = new TextField();
    public TextField txtInvoicesFilter = new TextField();
    public TextField txtUserFilter = new TextField();
    private CustomersForm customersForm = new CustomersForm(this);
    private ProductsForm productsForm = new ProductsForm(this);
    private InvoicesForm invoicesForm = new InvoicesForm(this);
    private UserForm userForm = new UserForm(this);
    private Button btnCustomers = new Button("Kontrahenci");
    private Button btnProducts = new Button("Produkty");
    private Button btnInvoices = new Button("Faktury");
    private Button btnUser = new Button("Użytkownik");
    private Button btnAddNewCustomer = new Button("Dodaj kontahenta ...");
    private Button btnAddNewProduct = new Button("Dodaj produkt ...");
    private Button btnAddNewInvoice = new Button("Dodaj fakturę ...");
    private Button btnAddNewUser = new Button("Dodaj użytkownika ...");
    HorizontalLayout mainContent = new HorizontalLayout();
    HorizontalLayout itemsToolbar = new HorizontalLayout();

    public MainView() {

        gridCustomers.setColumns("id", "fullName", "nip", "street", "postcode", "town");
        gridProducts.setColumns("id", "name", "vatRate", "netPrice", "vatValue", "grossPrice");
        gridInvoices.setColumns("id", "number", "date", "customer");
        gridUser.setColumns("id", "fullName", "nip", "street", "postcode", "town", "active");
        gridCustomers.setVisible(false);
        gridProducts.setVisible(false);
        gridInvoices.setVisible(false);
        gridUser.setVisible(false);

        btnAddNewCustomer.setVisible(false);
        btnAddNewProduct.setVisible(false);
        btnAddNewInvoice.setVisible(false);
        btnAddNewCustomer.setVisible(false);

        menuButtonClick(btnCustomers, gridCustomers, btnAddNewCustomer, customersForm, txtCustomersFilter );
        menuButtonClick(btnProducts, gridProducts, btnAddNewProduct, productsForm, txtProductsFilter);
        menuButtonClick(btnInvoices, gridInvoices, btnAddNewInvoice, invoicesForm, txtInvoicesFilter);
        menuButtonClick(btnUser, gridUser, btnAddNewUser, userForm, txtUserFilter);

        btnAddNewCustomer.addClickListener(e -> {
            gridCustomers.asSingleSelect().clear();
            customersForm.updateForm(new Customers());
        });
        btnAddNewProduct.addClickListener(e -> {
            gridProducts.asSingleSelect().clear();
            productsForm.updateForm(new Products());
        });
        btnAddNewInvoice.addClickListener(e -> {
            gridInvoices.asSingleSelect().clear();
            invoicesForm.update(new Invoices());
        });
        btnAddNewUser.addClickListener(e -> {
            gridUser.asSingleSelect().clear();
            userForm.updateForm(new Users());
        });

        HorizontalLayout mainToolbar = new HorizontalLayout(
                btnCustomers,
                btnProducts,
                btnInvoices,
                btnUser
        );

        mainContent.setSizeFull();
        gridCustomers.setSizeFull();
        gridProducts.setSizeFull();
        gridInvoices.setSizeFull();
        gridUser.setSizeFull();

        add(mainToolbar, itemsToolbar, mainContent);
        customersForm.updateForm(null);
        productsForm.updateForm(null);
        invoicesForm.update(null);
        userForm.updateForm(null);
        setSizeFull();
        refresh();

        gridCustomers.asSingleSelect().addValueChangeListener(event -> customersForm.updateForm(
                gridCustomers.asSingleSelect().getValue()));
        gridProducts.asSingleSelect().addValueChangeListener(event -> productsForm.updateForm(
                gridProducts.asSingleSelect().getValue()));
        gridInvoices.asSingleSelect().addValueChangeListener(event -> invoicesForm.update(
                gridInvoices.asSingleSelect().getValue()));
        gridUser.asSingleSelect().addValueChangeListener(event -> userForm.updateForm(
                gridUser.asSingleSelect().getValue()));
    }

    public void refresh() {
        gridCustomers.setItems(customersService.getCustomersList());
        gridProducts.setItems(productsService.getProductsList());
        gridInvoices.setItems(invoicesService.getInvoices());
        gridUser.setItems(userService.getUsersList());
    }

    public void menuButtonClick(@NotNull Button menuButton, Grid grid, Button button,
                                FormLayout form, TextField filter) {
        menuButton.addClickListener(e -> {
            mainContent.removeAll();
            mainContent.add(grid, form);
            itemsToolbar.removeAll();
            itemsToolbar.add(button, filter);
            grid.setVisible(true);
            button.setVisible(true);
            filter.setVisible(true);
        });
    }
}