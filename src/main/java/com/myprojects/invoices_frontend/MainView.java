package com.myprojects.invoices_frontend;

import com.myprojects.invoices_frontend.domain.Customers;
import com.myprojects.invoices_frontend.domain.Invoices;
import com.myprojects.invoices_frontend.domain.Products;
import com.myprojects.invoices_frontend.domain.Users;
import com.myprojects.invoices_frontend.layout.dialogboxes.ShowNotification;
import com.myprojects.invoices_frontend.layout.forms.*;
import com.myprojects.invoices_frontend.layout.grids.GridCreator;
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

    private final CustomersService customersService = CustomersService.getInstance();
    private final ProductsService productsService = ProductsService.getInstance();
    private final InvoicesService invoicesService = InvoicesService.getInstance();
    private final UsersService userService = UsersService.getInstance();

    public Grid<Customers> gridCustomers = new Grid<>(Customers.class);
    public Grid<Invoices> gridInvoices = new Grid<>(Invoices.class);
    public Grid<Products> gridNewInvoiceProductsList = new Grid<>(Products.class);
    public Grid<Products> gridProducts = new Grid<>(Products.class);
    public Grid<Customers> gridSelectCustomer = new Grid<>(Customers.class);
    public Grid<Products> gridSelectProduct = new Grid<>(Products.class);
    public Grid<Users> gridSelectUser = new Grid<>(Users.class);
    public Grid<Users> gridUser = new Grid<>(Users.class);
    public TextField txtCustomersFilter = new TextField();
    public TextField txtInvoicesFilter = new TextField();
    public TextField txtProductsFilter = new TextField();
    public TextField txtUserFilter = new TextField();
    private final CustomersForm customersForm = new CustomersForm(this);
    private final InvoicesForm invoicesForm = new InvoicesForm(this);
    private final NewInvoiceForm newInvoiceForm = new NewInvoiceForm(this);
    private final ProductsForm productsForm = new ProductsForm(this);
    private final UserForm userForm = new UserForm(this);
    private final Button btnAddNewCustomer = new Button("Dodaj kontahenta ...");
    private final Button btnAddNewInvoice = new Button("Dodaj fakturę ...");
    private final Button btnAddNewProduct = new Button("Dodaj produkt ...");
    public Button btnCustomers = new Button("Kontrahenci");
    private final Button btnAddNewUser = new Button("Dodaj użytkownika ...");
    public Button btnInvoices = new Button("Faktury");
    public Button btnProducts = new Button("Produkty");
    public Button btnUser = new Button("Użytkownik");
    public HorizontalLayout itemsToolbar = new HorizontalLayout();
    public HorizontalLayout mainContent = new HorizontalLayout();
    public HorizontalLayout mainToolbar = new HorizontalLayout();
    public HorizontalLayout newInvoiceFooterToolbar = new HorizontalLayout();
    public HorizontalLayout newInvoiceHeaderToolbar = new HorizontalLayout();

    public MainView() {

        GridCreator gridCreator = new GridCreator(this);
        gridCreator.createCustomerGrid();
        gridCreator.createSelectCustomerGrid();
        gridCreator.createProductGrid();
        gridCreator.createSelectProductGrid();
        gridCreator.createInvoiceGrid();
        gridCreator.createNewInvoiceProductsList();
        gridCreator.createUserGrid();
        gridCreator.createSelectUserGrid();

        btnCustomers.addClickListener(e -> menuButtonClick(gridCustomers, btnAddNewCustomer,
                customersForm, txtCustomersFilter));
        btnProducts.addClickListener(e -> menuButtonClick(gridProducts, btnAddNewProduct,
                productsForm, txtProductsFilter));
        btnUser.addClickListener(e -> menuButtonClick(gridUser, btnAddNewUser, userForm, txtUserFilter));
        btnInvoices.addClickListener(e -> invoiceMenuClick());

        mainToolbar.add(
                btnCustomers,
                btnProducts,
                btnInvoices,
                btnUser);

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
            customersForm.updateCustomersForm(new Customers());
        });
        btnAddNewProduct.addClickListener(e -> {
            gridProducts.asSingleSelect().clear();
            productsForm.updateProductsForm(new Products());
        });
        btnAddNewInvoice.addClickListener(e -> {
            mainContent.removeAll();
            mainContent.add(gridNewInvoiceProductsList);
            gridNewInvoiceProductsList.asSingleSelect().clear();
            gridNewInvoiceProductsList.setVisible(true);
            itemsToolbar.setVisible(false);
            newInvoiceHeaderToolbar.setVisible(true);
            newInvoiceFooterToolbar.setVisible(true);
            newInvoiceForm.clearNewInvoicesForm();
        });
        btnAddNewUser.addClickListener(e -> {
            gridUser.asSingleSelect().clear();
            if(userService.getUsersList().size() == 0) {
                userForm.updateUsersForm(new Users(true));
            } else {
                userForm.updateUsersForm(new Users());
            }
        });

        mainContent.setSizeFull();
        gridCustomers.setSizeFull();
        gridSelectCustomer.setSizeFull();
        gridProducts.setSizeFull();
        gridInvoices.setSizeFull();
        gridUser.setSizeFull();
        gridSelectUser.setSizeFull();

        add(
                mainToolbar,
                itemsToolbar,
                newInvoiceHeaderToolbar,
                mainContent,
                newInvoiceFooterToolbar
        );

        customersForm.updateCustomersForm(null);
        productsForm.updateProductsForm(null);
        invoicesForm.updateInvoicesForm(null);
        userForm.updateUsersForm(null);
        setSizeFull();
        refresh();

        gridCustomers.setVisible(false);
        gridSelectCustomer.setVisible(false);
        gridProducts.setVisible(false);
        gridSelectProduct.setVisible(false);
        gridNewInvoiceProductsList.setVisible(false);
        gridInvoices.setVisible(false);

        gridSelectUser.setVisible(false);

        btnAddNewCustomer.setVisible(false);
        btnAddNewProduct.setVisible(false);
        btnAddNewInvoice.setVisible(false);
        btnAddNewCustomer.setVisible(false);

        newInvoiceHeaderToolbar.setVisible(false);
        newInvoiceFooterToolbar.setVisible(false);

        if(userService.getUsersList().isEmpty()) {
            ShowNotification usersListEmpty =
                    new ShowNotification("Wprowadź pierwszego użytkownika", 5000);
            usersListEmpty.show();
            menuButtonClick(gridUser, btnAddNewUser, userForm, txtUserFilter);
        }

        gridCustomers.asSingleSelect().addValueChangeListener(event ->
                customersForm.updateCustomersForm(gridCustomers.asSingleSelect().getValue()));
        gridProducts.asSingleSelect().addValueChangeListener(event ->
                productsForm.updateProductsForm(gridProducts.asSingleSelect().getValue()));
        gridInvoices.asSingleSelect().addValueChangeListener(event ->
                invoicesForm.updateInvoicesForm(gridInvoices.asSingleSelect().getValue()));
        gridUser.asSingleSelect().addValueChangeListener(event ->
                userForm.updateUsersForm(gridUser.asSingleSelect().getValue()));
    }

    public void refresh() {
        gridCustomers.setItems(customersService.getCustomersList());
        gridSelectCustomer.setItems(gridCustomers.getSelectedItems());
        gridProducts.setItems(productsService.getProductsList());
        gridInvoices.setItems(invoicesService.getInvoicesList());
        gridUser.setItems(userService.getUsersList());
        gridSelectUser.setItems(gridUser.getSelectedItems());
    }

    public void menuButtonClick(@NotNull Grid grid, Button button,
                                FormLayout form, TextField filter) {
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
    }

    public void invoiceMenuClick() {
        mainContent.removeAll();
        mainContent.add(gridInvoices, invoicesForm);
        itemsToolbar.removeAll();
        itemsToolbar.add(btnAddNewInvoice, txtInvoicesFilter);
        itemsToolbar.setVisible(true);
        gridInvoices.setVisible(true);
        gridInvoices.setItems(invoicesService.getInvoicesList());
        btnAddNewInvoice.setVisible(true);
        txtInvoicesFilter.setVisible(true);
    }
}