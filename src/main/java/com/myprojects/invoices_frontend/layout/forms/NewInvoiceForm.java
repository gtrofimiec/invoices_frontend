package com.myprojects.invoices_frontend.layout.forms;

import com.myprojects.invoices_frontend.MainView;
import com.myprojects.invoices_frontend.domain.Customers;
import com.myprojects.invoices_frontend.domain.Invoices;
import com.myprojects.invoices_frontend.domain.Products;
import com.myprojects.invoices_frontend.domain.Users;
import com.myprojects.invoices_frontend.services.CustomersService;
import com.myprojects.invoices_frontend.services.InvoicesService;
import com.myprojects.invoices_frontend.services.ProductsService;
import com.myprojects.invoices_frontend.services.UsersService;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.textfield.BigDecimalField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.data.binder.PropertyId;
import com.vaadin.flow.data.converter.StringToDateConverter;
import org.jetbrains.annotations.NotNull;

import java.math.BigDecimal;
import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class NewInvoiceForm extends FormLayout {

    Long newInvoiceId;
    @PropertyId("customer")
    Customers customer;
    @PropertyId("user")
    Users user;
    @PropertyId("productsList")
    List<Products> productsList = new ArrayList<>();
    @PropertyId("number")
    public TextField txtNumber = new TextField("Numer faktury");
    @PropertyId("date")
    public TextField txtDate = new TextField("Data wystawienia");
    public TextField txtUser = new TextField("Sprzedawca");
    public TextField txtCustomer = new TextField("Kontrahent");
    @PropertyId("netSum")
    public BigDecimalField txtNetSum = new BigDecimalField("Wartość netto",
            new BigDecimal("0.00"), "0.00");
    @PropertyId("vatSum")
    public BigDecimalField txtVatSum = new BigDecimalField("Wartość VAT",
            new BigDecimal("0.00"), "0.00");
    @PropertyId("grossSum")
    public BigDecimalField txtGrossSum = new BigDecimalField("DO ZAPŁATY",
            new BigDecimal("0.00"), "0.00");
    @PropertyId("paymentMethod")
    public TextField txtPayment = new TextField("Forma płatności");

    public Button btnSave = new Button("Zapisz");
    public Button btnCancel = new Button("Zamknij");
    public Button btnAddUser = new Button("Zmień ...");
    public Button btnSelectUser = new Button("Wybierz ...");
    public Button btnAddCustomer = new Button("Zmień ...");
    public Button btnSelectCustomer = new Button("Wybierz ...");
    public Button btnAddProduct = new Button("Dodaj produkt ...");
    public Button btnSelectProduct = new Button("Wybierz ...");
    private Binder<Invoices> binderInvoices = new Binder<>(Invoices.class);
    private Binder<Products> binderProducts = new Binder<>(Products.class);
    private MainView mainView;
    private InvoicesService invoicesService = InvoicesService.getInstance();
    private CustomersService customersService = CustomersService.getInstance();
    private UsersService usersService = UsersService.getInstance();
    private ProductsService productsService = ProductsService.getInstance();
    private Users activeUser = new Users();

    public NewInvoiceForm(@NotNull MainView mainView) {
        this.mainView = mainView;

        txtNumber.setValue("01/2022");
        txtDate.setValue(String.valueOf(LocalDate.now()));
        txtDate.setPlaceholder("yyyy-mm-dd");
        txtPayment.setValue("przelew, 7 dni");
        btnSave.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        btnCancel.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        btnAddUser.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        btnAddCustomer.addThemeVariants(ButtonVariant.LUMO_PRIMARY);

        btnSave.addClickListener(event -> saveInvoice());
        btnCancel.addClickListener(event -> cancel());
        btnAddUser.addClickListener(event -> selectUser());
        btnAddCustomer.addClickListener(event -> selectCustomer());
        btnAddProduct.addClickListener(event -> selectProduct());
        btnSelectCustomer.addClickListener(event -> addCustomerToInvoice());
        btnSelectProduct.addClickListener(event -> addProductToList());
        btnSelectUser.addClickListener(event -> addUserToInvoice());
        btnSelectProduct.setVisible(false);
        btnSelectCustomer.setVisible(false);
        btnSelectUser.setVisible(false);

        activeUser = usersService.getActiveUser();
        if(activeUser.getId() != null) {
            user = activeUser;
            txtUser.setValue(activeUser.getFullName());
        } else {
            txtUser.setValue("");
        }

        binderInvoices.forField(txtDate)
                .withNullRepresentation("")
                .withConverter(new StringToDateConverter())
                .bind(Invoices::getDate, Invoices::setDate);
        binderInvoices.bindInstanceFields(this);
//        binderProducts.bindInstanceFields(this);
    }

    private void saveInvoice() {
        Invoices newInvoice = new Invoices(
                txtNumber.getValue(),
                Date.valueOf(txtDate.getValue()),
                txtNetSum.getValue(),
                txtVatSum.getValue(),
                txtGrossSum.getValue(),
                txtPayment.getValue(),
                customer, user, productsList
        );
        invoicesService.saveInvoice(newInvoice);
        cancel();
        mainView.gridInvoices.setItems(invoicesService.getInvoicesList());
//        updateForm(newInvoice);
    }

    private void cancel() {
        this.setVisible(false);
        mainView.newInvoiceHeaderToolbar.setVisible(false);
        mainView.newInvoiceFooterToolbar.setVisible(false);
        mainView.itemsToolbar.setVisible(true);
    }

    public void updateForm(Invoices invoice) {
        binderInvoices.setBean(invoice);
        if (invoice == null) {
            setVisible(false);
        } else {
            setVisible(true);
            txtNumber.focus();
        }
    }

    private void selectUser() {
        mainView.mainContent.remove(mainView.gridNewInvoiceProductsList);
        mainView.mainContent.remove(this);
        mainView.mainContent.add(mainView.gridSelectUser);
        btnSave.setVisible(false);
        btnCancel.setVisible(false);
        btnAddProduct.setVisible(false);
        mainView.gridSelectUser.setItems(usersService.getUsersList());
        btnSelectUser.setVisible(true);
        mainView.gridSelectUser.setVisible(true);
    }

    private void addUserToInvoice() {
        user = mainView.gridSelectUser.asSingleSelect().getValue();
        txtUser.setValue(user.getFullName());
        btnSelectUser.setVisible(false);
        mainView.mainContent.remove(mainView.gridSelectUser);
        mainView.mainContent.add(mainView.gridNewInvoiceProductsList);
        mainView.mainContent.add(this);
        btnSave.setVisible(true);
        btnCancel.setVisible(true);
        btnAddUser.setVisible(true);
    }

    private void selectCustomer() {
        mainView.mainContent.remove(mainView.gridNewInvoiceProductsList);
        mainView.mainContent.remove(this);
        mainView.mainContent.add(mainView.gridSelectCustomer);
        btnSave.setVisible(false);
        btnCancel.setVisible(false);
        btnAddProduct.setVisible(false);
        mainView.gridSelectCustomer.setItems(customersService.getCustomersList());
        btnSelectCustomer.setVisible(true);
        mainView.gridSelectCustomer.setVisible(true);
    }

    private void addCustomerToInvoice() {
        customer = mainView.gridSelectCustomer.asSingleSelect().getValue();
        txtCustomer.setValue(customer.getFullName());
        btnSelectCustomer.setVisible(false);
        mainView.mainContent.remove(mainView.gridSelectCustomer);
        mainView.mainContent.add(mainView.gridNewInvoiceProductsList);
        mainView.mainContent.add(this);
        btnSave.setVisible(true);
        btnCancel.setVisible(true);
        btnAddProduct.setVisible(true);
    }

    private void selectProduct() {
        mainView.mainContent.remove(mainView.gridNewInvoiceProductsList);
        mainView.mainContent.remove(this);
        mainView.mainContent.add(mainView.gridSelectProduct);
        btnSave.setVisible(false);
        btnCancel.setVisible(false);
        btnAddProduct.setVisible(false);
        mainView.gridSelectProduct.setItems(productsService.getProductsList());
        btnSelectProduct.setVisible(true);
        mainView.gridSelectProduct.setVisible(true);
    }

    private void addProductToList() {
        Products product = mainView.gridSelectProduct.asSingleSelect().getValue();
        productsList.add(product);
//        if(txtNetSum.getValue() == null) {
//            txtNetSum.setValue(new BigDecimal("0.00"));
//        }
        txtNetSum.setValue(txtNetSum.getValue().add(product.getNetPrice()));
//        if(txtVatSum.getValue() == null) {
//            txtVatSum.setValue(new BigDecimal("0.00"));
//        }
        txtVatSum.setValue(txtVatSum.getValue().add(product.getVatValue()));
//        if(txtGrossSum.getValue() == null) {
//            txtGrossSum.setValue(new BigDecimal("0.00"));
//        }
        txtGrossSum.setValue(txtGrossSum.getValue().add(product.getGrossPrice()));
        mainView.gridNewInvoiceProductsList.setItems(productsList);
        btnSelectProduct.setVisible(false);
        mainView.mainContent.remove(mainView.gridSelectProduct);
        mainView.mainContent.add(mainView.gridNewInvoiceProductsList);
        mainView.mainContent.add(this);
        btnSave.setVisible(true);
        btnCancel.setVisible(true);
        btnAddProduct.setVisible(true);
    }
}