package com.myprojects.invoices_frontend.layout.forms;

import com.myprojects.invoices_frontend.MainView;
import com.myprojects.invoices_frontend.domain.Invoices;
import com.myprojects.invoices_frontend.services.InvoicesService;
import com.myprojects.invoices_frontend.services.UsersService;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.BigDecimalField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.data.binder.PropertyId;
import com.vaadin.flow.data.converter.StringToDateConverter;
import com.vaadin.flow.data.value.ValueChangeMode;
import org.jetbrains.annotations.NotNull;

import java.math.BigDecimal;

public class InvoicesForm extends FormLayout {

    @PropertyId("number")
    private TextField txtNumber = new TextField("Numer faktury");
    @PropertyId("date")
    private TextField txtDate = new TextField("Data wystawienia");
    private TextField txtUser = new TextField("Sprzedawca");
    private TextField txtCustomer = new TextField("Kontrahent");
    @PropertyId("netPrice")
    private BigDecimalField txtNetPrice = new BigDecimalField("Cena netto",
            new BigDecimal(0.00), "0.00");
    @PropertyId("vatValue")
    private BigDecimalField txtVatValue = new BigDecimalField("Wartość VAT",
            new BigDecimal(0.00), "0.00");
    @PropertyId("grossPrice")
    private BigDecimalField txtGrossPrice = new BigDecimalField("Cena brutto",
            new BigDecimal(0.00), "0.00");
    @PropertyId("payment_method")
    private TextField txtPayment = new TextField("Forma płatności");
    private Button btnSave = new Button("Zapisz");
    private Button btnDelete = new Button("Usuń");
    private Button btnCancel = new Button("Zamknij");
    private Button btnChangeUser = new Button("Zmień ...");
    private Button btnChangeCustomer = new Button("Zmień ...");
    private Button btnSelectUser = new Button("Wybierz ...");
    public Button btnSelectCustomer = new Button("Wybierz ...");
    private Binder<Invoices> binder = new Binder<>(Invoices.class);
    private MainView mainView;
    private InvoicesService invoicesService = InvoicesService.getInstance();
    private UsersService usersService = UsersService.getInstance();

    public InvoicesForm(@NotNull MainView mainView) {
        this.mainView = mainView;
        mainView.txtInvoicesFilter.setPlaceholder("Filtruj po numerze ...");
        mainView.txtInvoicesFilter.setClearButtonVisible(true);
        mainView.txtInvoicesFilter.setValueChangeMode(ValueChangeMode.EAGER);

        btnSave.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        btnDelete.addThemeVariants(ButtonVariant.LUMO_ERROR);
        btnCancel.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        btnChangeUser.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        btnChangeCustomer.addThemeVariants(ButtonVariant.LUMO_PRIMARY);

        mainView.txtInvoicesFilter.addValueChangeListener(e -> find());
        btnSave.addClickListener(event -> updateInvoice());
        btnDelete.addClickListener(event -> deleteInvoice());
        btnCancel.addClickListener(event -> cancel());
        btnChangeUser.addClickListener(event -> changeUser());
        btnChangeCustomer.addClickListener(event -> changeCustomer());

        if(usersService.getUsersList().size() > 0) {
            txtUser.setValue(usersService.getActiveUser().getFullName());
        } else {
            txtUser.setValue("");
        }

        HorizontalLayout buttons = new HorizontalLayout(btnSave, btnDelete, btnCancel);
        VerticalLayout userLayout = new VerticalLayout(txtUser, btnChangeUser);
        VerticalLayout customerLayout = new VerticalLayout(txtCustomer, btnChangeCustomer);
        add(txtNumber, txtDate, userLayout, customerLayout, txtNetPrice, txtVatValue, txtGrossPrice,
                txtPayment, buttons);
        binder.forField(txtDate)
                .withNullRepresentation("")
                .withConverter(new StringToDateConverter())
                .bind(Invoices::getDate, Invoices::setDate);
        binder.bindInstanceFields(this);
    }

    private void updateInvoice() {
        Invoices invoice = binder.getBean();
//        if(!txtNumber.isEmpty()) {
//            if (invoice.getId() != null) {
                invoicesService.updateInvoice(invoice);
//            } else {
//                invoicesService.saveInvoice(invoice);
//            }
//        }
        mainView.refresh();
        updateForm(invoice);
    }

    private void deleteInvoice() {
        Invoices invoice = binder.getBean();
        invoicesService.deleteInvoice(invoice);
        mainView.refresh();
        updateForm(null);
    }

    private void cancel() {
        this.setVisible(false);
//        mainView.newInvoiceHeaderToolbar.setVisible(false);
    }

    public void updateForm(Invoices invoice) {
        binder.setBean(invoice);
        if (invoice == null) {
            setVisible(false);
        } else {
            setVisible(true);
            txtNumber.focus();
        }
    }

    private void find() {
        mainView.gridInvoices.setItems(
                invoicesService.findByNumber(mainView.txtInvoicesFilter.getValue())
        );
    }

    private void selectUser() {
        txtUser.setValue(mainView.gridSelectUser.asSingleSelect().getValue().getFullName());
        mainView.itemsToolbar.remove(btnSelectUser);
        mainView.mainContent.remove(mainView.gridSelectUser);
        mainView.mainContent.add(mainView.gridInvoices, this);
        mainView.refresh();
        mainView.gridInvoices.setVisible(true);
        this.setVisible(true);
    }

    private void changeUser() {
        mainView.itemsToolbar.add(btnSelectUser);
        mainView.mainContent.remove(mainView.gridInvoices);
        mainView.mainContent.remove(this);
        mainView.mainContent.add(mainView.gridSelectUser);
        btnSelectUser.setVisible(true);
        mainView.gridSelectUser.setVisible(true);
        btnSelectUser.addClickListener(e -> selectUser());
        mainView.gridSelectUser.asSingleSelect().addValueChangeListener(event -> selectUser());
    }

    private void selectCustomer() {
        txtCustomer.setValue(mainView.gridSelectCustomer.asSingleSelect().getValue().getFullName());
        mainView.itemsToolbar.remove(btnSelectCustomer);
        mainView.mainContent.remove(mainView.gridSelectCustomer);
        mainView.mainContent.add(mainView.gridInvoices, this);
        mainView.refresh();
        mainView.gridInvoices.setVisible(true);
        this.setVisible(true);
    }

    private void changeCustomer() {
        mainView.itemsToolbar.add(btnSelectCustomer);
        mainView.mainContent.remove(mainView.gridInvoices);
        mainView.mainContent.remove(this);
        mainView.mainContent.add(mainView.gridSelectCustomer);
        btnSelectCustomer.setVisible(true);
        mainView.gridSelectCustomer.setVisible(true);
        btnSelectCustomer.addClickListener(e -> selectCustomer());
        mainView.gridSelectCustomer.asSingleSelect().addValueChangeListener(event -> selectCustomer());
    }
}