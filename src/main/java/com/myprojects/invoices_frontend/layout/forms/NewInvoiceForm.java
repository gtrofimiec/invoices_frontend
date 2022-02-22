package com.myprojects.invoices_frontend.layout.forms;

import com.myprojects.invoices_frontend.domain.*;
import com.myprojects.invoices_frontend.layout.MainView;
import com.myprojects.invoices_frontend.layout.dialogboxes.ShowNotification;
import com.myprojects.invoices_frontend.services.CustomersService;
import com.myprojects.invoices_frontend.services.InvoicesService;
import com.myprojects.invoices_frontend.services.ProductsService;
import com.myprojects.invoices_frontend.services.UsersService;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.textfield.BigDecimalField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.component.textfield.TextFieldVariant;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.data.binder.PropertyId;
import org.jetbrains.annotations.NotNull;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.Period;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class NewInvoiceForm extends FormLayout {

    Invoices invoice;
    @PropertyId("customer")
    Customers customer;
    @PropertyId("user")
    Users user;
    @PropertyId("productsList")
    List<Products> productsList = new ArrayList<>();
    @PropertyId("number")
    public TextField txtNumber = new TextField("Numer faktury");
    @PropertyId("date")
    public DatePicker dtpDate = new DatePicker("Data wystawienia");
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
    public ComboBox<String> cmbPaymentMethod = new ComboBox<>("Forma płatności");
    public TextField txtPayment = new TextField("Termin płatności");
    @PropertyId("paymentDate")
    public DatePicker dtpPaymentDate = new DatePicker("Data płatności");
    public Button btnSave = new Button("Zapisz");
    public Button btnCancel = new Button("Zamknij");
    public Button btnShowCustomersList = new Button("Wybierz ...");
    public Button btnAddCustomer = new Button("Zmień ...");
    public Button btnCancelFromCustomersList = new Button("Wyjdź");
    public Button btnShowProductsList = new Button("Wybierz ...");
    public Button btnAddProduct = new Button("Dodaj produkt ...");
    public Button btnCancelFromProductList = new Button("Wyjdź");
    public Button btnShowUsersList = new Button("Wybierz ...");
    public Button btnAddUser = new Button("Zmień ...");
    public Button btnCancelFromUsersList = new Button("Wyjdź");
    private Binder<Invoices> binderInvoices = new Binder<>(Invoices.class);
    public HorizontalLayout newInvoiceFooterToolbar = new HorizontalLayout();
    public HorizontalLayout newInvoiceHeaderToolbar = new HorizontalLayout();
    public HorizontalLayout newInvoicePaymentsToolbar = new HorizontalLayout();
    private MainView mainView;
    private InvoicesService invoicesService = InvoicesService.getInstance();
    private CustomersService customersService = CustomersService.getInstance();
    private UsersService usersService = UsersService.getInstance();
    private ProductsService productsService = ProductsService.getInstance();
    private Users activeUser;

    public NewInvoiceForm(@NotNull MainView mainView) {
        this.mainView = mainView;
        clearNewInvoicesForm();

        btnSave.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        btnCancel.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        btnCancelFromProductList.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        btnAddUser.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        btnAddCustomer.addThemeVariants(ButtonVariant.LUMO_PRIMARY);

        btnSave.addClickListener(event -> saveInvoice());
        btnCancel.addClickListener(event -> cancel());
        btnCancelFromCustomersList.addClickListener(event -> cancelFromCustomersList());
        btnCancelFromProductList.addClickListener(event -> cancelFromProductList());
        btnCancelFromUsersList.addClickListener(event -> cancelFromUsersList());
        btnAddUser.addClickListener(event -> selectUser());
        btnAddCustomer.addClickListener(event -> selectCustomer());
        btnAddProduct.addClickListener(event -> selectProduct());
        btnShowCustomersList.addClickListener(event -> addCustomerToInvoice());
        btnShowProductsList.addClickListener(event -> addProductToList());
        btnShowUsersList.addClickListener(event -> addUserToInvoice());
        dtpPaymentDate.addFocusListener(event -> calculatePaymentDate());

        btnShowProductsList.setVisible(false);
        btnShowCustomersList.setVisible(false);
        btnShowUsersList.setVisible(false);
        btnCancelFromCustomersList.setVisible(false);
        btnCancelFromProductList.setVisible(false);
        btnCancelFromUsersList.setVisible(false);

        newInvoiceHeaderToolbar.add(
                txtNumber,
                dtpDate,
                txtUser,
                btnAddUser,
                txtCustomer,
                btnAddCustomer
        );
        newInvoicePaymentsToolbar.add(
                cmbPaymentMethod,
                txtPayment,
                dtpPaymentDate
        );
        newInvoiceFooterToolbar.add(
                txtNetSum,
                txtVatSum,
                txtGrossSum,
                btnAddProduct,
                btnShowProductsList,
                btnShowCustomersList,
                btnShowUsersList,
                btnSave,
                btnCancel,
                btnCancelFromCustomersList,
                btnCancelFromProductList,
                btnCancelFromUsersList
        );

        cmbPaymentMethod.setItems(loadPaymentMethods());
        binderInvoices.bindInstanceFields(this);
    }

    private void saveInvoice() {
        Invoices newInvoice = new Invoices(
                txtNumber.getValue(),
                dtpDate.getValue(),
                txtNetSum.getValue(),
                txtVatSum.getValue(),
                txtGrossSum.getValue(),
                cmbPaymentMethod.getValue(),
                dtpPaymentDate.getValue(),
                customer, user, productsList
        );
        if(isInvoiceComplete(newInvoice)) {
            invoice = newInvoice;
            invoicesService.saveInvoice(newInvoice);
            mainView.gridInvoices.setItems(invoicesService.getInvoicesList());
            cancel();
        } else {
            ShowNotification showNotification = new ShowNotification("Dane do faktury niekompletne",
                    5000);
            showNotification.show();
        }
    }

    private void cancel() {
        newInvoiceHeaderToolbar.setVisible(false);
        newInvoicePaymentsToolbar.setVisible(false);
        newInvoiceFooterToolbar.setVisible(false);
        mainView.invoiceMenuClick();
    }

    private void cancelFromCustomersList() {
        btnCancelFromCustomersList.setVisible(false);
        mainView.mainContent.remove(mainView.gridSelectCustomer);
        mainView.mainContent.add(mainView.gridNewInvoiceProductsList);
        mainView.mainContent.add(this);
        btnSave.setVisible(true);
        btnCancel.setVisible(true);
        btnAddProduct.setVisible(true);
    }

    private void cancelFromProductList() {
        btnShowProductsList.setVisible(false);
        btnCancelFromProductList.setVisible(false);
        mainView.mainContent.remove(mainView.gridSelectProduct);
        mainView.mainContent.add(mainView.gridNewInvoiceProductsList);
        mainView.mainContent.add(this);
        btnSave.setVisible(true);
        btnCancel.setVisible(true);
        btnAddProduct.setVisible(true);
    }

    private void cancelFromUsersList() {
        btnCancelFromUsersList.setVisible(false);
        mainView.mainContent.remove(mainView.gridSelectUser);
        mainView.mainContent.add(mainView.gridNewInvoiceProductsList);
        mainView.mainContent.add(this);
        btnSave.setVisible(true);
        btnCancel.setVisible(true);
        btnAddProduct.setVisible(true);
    }

    public void clearNewInvoicesForm() {
        txtNumber.setValue("");
        dtpDate.setValue(LocalDate.now());
        dtpDate.setPlaceholder("yyyy-mm-dd");
        cmbPaymentMethod.setItems(loadPaymentMethods());
        cmbPaymentMethod.setValue(PaymentMethods.CASH.getPaymentMethod());
        dtpPaymentDate.setValue(LocalDate.now());

        Period period = Period.between(dtpPaymentDate.getValue(), dtpDate.getValue());
        int days = Math.abs(period.getDays());
        txtPayment.setValue(String.valueOf(days));

        activeUser = usersService.getActiveUser();
        if(activeUser.getId() != null && activeUser.getFullName() != null) {
            user = activeUser;
            txtUser.setValue(activeUser.getFullName());
        } else {
            txtUser.setValue("");
        }

        txtCustomer.setValue("");
        txtNetSum.setValue(new BigDecimal("0.00"));
        txtNetSum.addThemeVariants(TextFieldVariant.LUMO_ALIGN_RIGHT);
        txtVatSum.setValue(new BigDecimal("0.00"));
        txtVatSum.addThemeVariants(TextFieldVariant.LUMO_ALIGN_RIGHT);
        txtGrossSum.setValue(new BigDecimal("0.00"));
        txtGrossSum.addThemeVariants(TextFieldVariant.LUMO_ALIGN_RIGHT);
        productsList.clear();
    }

    private void selectUser() {
        mainView.mainContent.remove(mainView.gridNewInvoiceProductsList);
        mainView.mainContent.remove(this);
        mainView.mainContent.add(mainView.gridSelectUser);
        btnSave.setVisible(false);
        btnCancel.setVisible(false);
        btnAddProduct.setVisible(false);
        mainView.gridSelectUser.setItems(usersService.getUsersList());
        btnShowUsersList.setVisible(true);
        btnCancelFromUsersList.setVisible(true);
        mainView.gridSelectUser.setVisible(true);
    }

    private void addUserToInvoice() {
        user = mainView.gridSelectUser.asSingleSelect().getValue();
        txtUser.setValue(user.getFullName());
        btnShowUsersList.setVisible(false);
        btnCancelFromUsersList.setVisible(false);
        mainView.mainContent.remove(mainView.gridSelectUser);
        mainView.mainContent.add(mainView.gridNewInvoiceProductsList);
        mainView.mainContent.add(this);
        btnSave.setVisible(true);
        btnCancel.setVisible(true);
        btnAddProduct.setVisible(true);
    }

    private void selectCustomer() {
        mainView.mainContent.remove(mainView.gridNewInvoiceProductsList);
        mainView.mainContent.remove(this);
        mainView.mainContent.add(mainView.gridSelectCustomer);
        btnSave.setVisible(false);
        btnCancel.setVisible(false);
        btnAddProduct.setVisible(false);
        mainView.gridSelectCustomer.setItems(customersService.getCustomersList());
        btnShowCustomersList.setVisible(true);
        btnCancelFromCustomersList.setVisible(true);
        mainView.gridSelectCustomer.setVisible(true);
    }

    private void addCustomerToInvoice() {
        customer = mainView.gridSelectCustomer.asSingleSelect().getValue();
        txtCustomer.setValue(customer.getFullName());
        btnShowCustomersList.setVisible(false);
        btnCancelFromCustomersList.setVisible(false);
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
        btnShowProductsList.setVisible(true);
        btnCancelFromProductList.setVisible(true);
        mainView.gridSelectProduct.setVisible(true);
    }

    private void addProductToList() {
        Products product = mainView.gridSelectProduct.asSingleSelect().getValue();
        productsList.add(product);
        txtNetSum.setValue(txtNetSum.getValue().add(product.getNetPrice()));
        txtVatSum.setValue(txtVatSum.getValue().add(product.getVatValue()));
        txtGrossSum.setValue(txtGrossSum.getValue().add(product.getGrossPrice()));
        mainView.gridNewInvoiceProductsList.setItems(productsList);
        btnShowProductsList.setVisible(false);
        btnCancelFromProductList.setVisible(false);
        mainView.mainContent.remove(mainView.gridSelectProduct);
        mainView.mainContent.add(mainView.gridNewInvoiceProductsList);
        mainView.mainContent.add(this);
        btnSave.setVisible(true);
        btnCancel.setVisible(true);
        btnAddProduct.setVisible(true);
    }

    private List<String> loadPaymentMethods() {
        return Arrays.stream(PaymentMethods.values())
                .map(PaymentMethods::getPaymentMethod)
                .collect(Collectors.toList());
    }

    private void calculatePaymentDate() {
        LocalDate paymentDate = dtpDate.getValue();
        if (!Objects.equals(txtPayment.getValue(), "0")) {
            int payment = Integer.parseInt(txtPayment.getValue());
            paymentDate = dtpDate.getValue().plusDays(payment);
        }
        dtpPaymentDate.setValue(paymentDate);
    }

    boolean isInvoiceComplete(@NotNull Invoices invoice) {
        return !invoice.getNumber().isEmpty() &&
                !invoice.getDate().toString().isEmpty() &&
                !invoice.getNetSum().toString().isEmpty() &&
                !invoice.getVatSum().toString().isEmpty() &&
                !invoice.getGrossSum().toString().isEmpty() &&
                !invoice.getPaymentMethod().isEmpty() &&
                !invoice.getPaymentDate().toString().isEmpty() &&
                invoice.getCustomer() != null &&
                invoice.getUser() != null &&
                !invoice.getProductsList().isEmpty();
    }
}