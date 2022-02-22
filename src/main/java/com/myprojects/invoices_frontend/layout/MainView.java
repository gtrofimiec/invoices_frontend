package com.myprojects.invoices_frontend.layout;

import com.myprojects.invoices_frontend.domain.Customers;
import com.myprojects.invoices_frontend.domain.Invoices;
import com.myprojects.invoices_frontend.domain.Products;
import com.myprojects.invoices_frontend.domain.Users;
import com.myprojects.invoices_frontend.exceptions.NoConnectionToDB;
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

import java.util.Objects;

@Route
public class MainView extends VerticalLayout {

    NoConnectionToDB noConnectionToDB = new NoConnectionToDB(this);

    private final CustomersService customersService = CustomersService.getInstance();
    private final ProductsService productsService = ProductsService.getInstance();
    private final InvoicesService invoicesService = InvoicesService.getInstance();
    private final UsersService userService = UsersService.getInstance();

    public Grid<Invoices> gridInvoices = new Grid<>(Invoices.class);
    public Grid<Products> gridNewInvoiceProductsList = new Grid<>(Products.class);
    public Grid<Customers> gridSelectCustomer = new Grid<>(Customers.class);
    public Grid<Products> gridSelectProduct = new Grid<>(Products.class);
    public Grid<Users> gridSelectUser = new Grid<>(Users.class);
    public TextField txtCustomersFilter = new TextField();
    public TextField txtInvoicesFilter = new TextField();
    public TextField txtProductsFilter = new TextField();
    public TextField txtUserFilter = new TextField();
    private final CustomersForm customersForm = new CustomersForm(this);
    public final InvoicesForm invoicesForm = new InvoicesForm(this);
    public final EditInvoicesForm editInvoiceForm = new EditInvoicesForm(this);
    public final NewInvoiceForm newInvoiceForm = new NewInvoiceForm(this);
    private final ProductsForm productsForm = new ProductsForm(this);
    private final UserForm userForm = new UserForm(this);
    public Button btnCustomers = new Button("Kontrahenci");
    public Button btnInvoices = new Button("Faktury");
    public Button btnProducts = new Button("Produkty");
    public Button btnUser = new Button("Użytkownik");
    public HorizontalLayout itemsToolbar = new HorizontalLayout();
    public HorizontalLayout mainContent = new HorizontalLayout();
    public HorizontalLayout mainToolbar = new HorizontalLayout();

    public MainView() {

        GridCreator gridCreator = new GridCreator(this);
        gridCreator.createSelectCustomerGrid();
        gridCreator.createSelectProductGrid();
        gridCreator.createInvoiceGrid();
        gridCreator.createNewInvoiceProductsList();
        gridCreator.createSelectUserGrid();

        btnCustomers.addClickListener(e -> menuButtonClick(customersForm.gridCustomers,
                customersForm.buttons, customersForm, txtCustomersFilter));
        btnProducts.addClickListener(e -> menuButtonClick(productsForm.gridProducts,
                productsForm.buttons, productsForm, txtProductsFilter));
        btnUser.addClickListener(e -> menuButtonClick(userForm.gridUser, userForm.buttons, userForm,
                txtUserFilter));
        btnInvoices.addClickListener(e -> invoiceMenuClick());

        mainToolbar.add(btnCustomers, btnProducts, btnInvoices, btnUser);

        mainContent.setSizeFull();
        customersForm.gridCustomers.setSizeFull();
        gridSelectCustomer.setSizeFull();
        productsForm.gridProducts.setSizeFull();
        gridInvoices.setSizeFull();
        userForm.gridUser.setSizeFull();
        gridSelectUser.setSizeFull();

        add(
                mainToolbar,
                itemsToolbar,
                newInvoiceForm.newInvoiceHeaderToolbar,
                editInvoiceForm.editInvoiceHeaderToolbar,
                newInvoiceForm.newInvoicePaymentsToolbar,
                editInvoiceForm.editInvoicePaymentsToolbar,
                mainContent,
                newInvoiceForm.newInvoiceFooterToolbar,
                editInvoiceForm.editInvoiceFooterToolbar
        );

        customersForm.updateCustomersForm(null);
        productsForm.updateProductsForm(null);
        invoicesForm.updateInvoicesForm(null);
        userForm.updateUsersForm(null);
        setSizeFull();
        refresh();

        customersForm.gridCustomers.setVisible(false);
        gridSelectCustomer.setVisible(false);
        productsForm.gridProducts.setVisible(false);
        gridSelectProduct.setVisible(false);
        gridNewInvoiceProductsList.setVisible(false);
        gridInvoices.setVisible(false);
        gridSelectUser.setVisible(false);

        newInvoiceForm.newInvoiceHeaderToolbar.setVisible(false);
        newInvoiceForm.newInvoicePaymentsToolbar.setVisible(false);
        newInvoiceForm.newInvoiceFooterToolbar.setVisible(false);
        editInvoiceForm.editInvoiceHeaderToolbar.setVisible(false);
        editInvoiceForm.editInvoicePaymentsToolbar.setVisible(false);
        editInvoiceForm.editInvoiceFooterToolbar.setVisible(false);

        if(userService.getUsersList().isEmpty()) {
            ShowNotification usersListEmpty = new ShowNotification("Wprowadź pierwszego użytkownika",
                    5000);
            usersListEmpty.show();
            menuButtonClick(userForm.gridUser, userForm.buttons, userForm, txtUserFilter);
        } else if(Objects.equals(userService.getUsersList().get(0).getFullName(), "No database")) {
            noConnectionToDB.executeException();
        }
    }

    public void refresh() {
        customersForm.gridCustomers.setItems(customersService.getCustomersList());
        gridSelectCustomer.setItems(customersForm.gridCustomers.getSelectedItems());
        productsForm.gridProducts.setItems(productsService.getProductsList());
        gridInvoices.setItems(invoicesService.getInvoicesList());
        userForm.gridUser.setItems(userService.getUsersList());
        gridSelectUser.setItems(userForm.gridUser.getSelectedItems());
    }

    public void menuButtonClick(@NotNull Grid grid, HorizontalLayout layout,
                                FormLayout form, TextField filter) {
        mainContent.removeAll();
        mainContent.add(grid, form);
        itemsToolbar.removeAll();
        itemsToolbar.add(layout, filter);
        if(!itemsToolbar.isVisible()) {
            itemsToolbar.setVisible(true);
        }
        grid.setVisible(true);
        layout.setVisible(true);
        filter.setVisible(true);
        newInvoiceForm.newInvoiceHeaderToolbar.setVisible(false);
        newInvoiceForm.newInvoicePaymentsToolbar.setVisible(false);
        newInvoiceForm.newInvoiceFooterToolbar.setVisible(false);
        editInvoiceForm.editInvoiceHeaderToolbar.setVisible(false);
        editInvoiceForm.editInvoicePaymentsToolbar.setVisible(false);
        editInvoiceForm.editInvoiceFooterToolbar.setVisible(false);
//        if(newInvoiceForm.newInvoiceHeaderToolbar.isVisible()) {
//                newInvoiceForm.newInvoiceHeaderToolbar.setVisible(false);
//            }
//            if(newInvoiceForm.newInvoicePaymentsToolbar.isVisible()) {
//                newInvoiceForm.newInvoicePaymentsToolbar.setVisible(false);
//            }
//            if(newInvoiceForm.newInvoiceFooterToolbar.isVisible()) {
//                newInvoiceForm.newInvoiceFooterToolbar.setVisible(false);
//            }
//            if(editInvoiceForm.editInvoiceHeaderToolbar.isVisible()) {
//                editInvoiceForm.editInvoiceHeaderToolbar.setVisible(false);
//            }
//            if(editInvoiceForm.editInvoicePaymentsToolbar.isVisible()) {
//                editInvoiceForm.editInvoicePaymentsToolbar.setVisible(false);
//            }
//            if(editInvoiceForm.editInvoiceFooterToolbar.isVisible()) {
//                editInvoiceForm.editInvoiceFooterToolbar.setVisible(false);
//            }
    }

    public void invoiceMenuClick() {
        mainContent.removeAll();
        mainContent.add(gridInvoices, invoicesForm);
        itemsToolbar.removeAll();
        itemsToolbar.add(invoicesForm.invoicesButtons, txtInvoicesFilter);
        itemsToolbar.setVisible(true);
        gridInvoices.setVisible(true);
        invoicesForm.btnAddNewInvoice.setVisible(true);
        txtInvoicesFilter.setVisible(true);
    }
}