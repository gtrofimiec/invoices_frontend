package com.myprojects.invoices_frontend.layout.forms;

import com.myprojects.invoices_frontend.domain.Customers;
import com.myprojects.invoices_frontend.domain.dtos.CeidgApiDto;
import com.myprojects.invoices_frontend.layout.MainView;
import com.myprojects.invoices_frontend.layout.dialogboxes.ConfirmationDialog;
import com.myprojects.invoices_frontend.services.CeidgApiService;
import com.myprojects.invoices_frontend.services.CustomersService;
import com.myprojects.invoices_frontend.services.PostcodeApiService;
import com.vaadin.flow.component.HasValue;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.contextmenu.GridContextMenu;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.data.binder.PropertyId;
import com.vaadin.flow.data.value.ValueChangeMode;
import org.jetbrains.annotations.NotNull;

public class CustomersForm extends FormLayout {

    @PropertyId("fullName")
    private TextField txtFullName = new TextField("Pełna nazwa");
    @PropertyId("nip")
    private TextField txtNip = new TextField("NIP (tylko cyfry)");
    @PropertyId("street")
    private TextField txtStreet = new TextField("Ulica");
    @PropertyId("postcode")
    private TextField txtPostcode = new TextField("Kod pocztowy");
    @PropertyId("town")
    private TextField txtTown = new TextField("Miejscowość");
    @PropertyId("mail")
    private TextField txtMail = new TextField("Adres e-mail");
    public final Button btnAddNewCustomer = new Button("Dodaj kontahenta ...");
    private Button btnEdit = new Button("Edytuj ...");
    private Button btnSave = new Button("Zapisz");
    private Button btnDelete = new Button("Usuń");
    private Button btnCancel = new Button("Zamknij");
    private Button btnGetFromCEIDG = new Button("Pobierz z CEIDG");
    public Grid<Customers> gridCustomers = new Grid<>(Customers.class);
    public HorizontalLayout buttons = new HorizontalLayout();
    private Binder<Customers> binder = new Binder<>(Customers.class);
    private MainView mainView;
    private CustomersService customersService = CustomersService.getInstance();
    private final PostcodeApiService postcodeApiService = PostcodeApiService.getInstance();
    private final CeidgApiService ceidgApiService = CeidgApiService.getInstance();

    public CustomersForm(@NotNull MainView mainView) {
        this.mainView = mainView;

        buttons.add(btnAddNewCustomer, btnEdit);

        mainView.txtCustomersFilter.setPlaceholder("Filtruj po nazwie ...");
        mainView.txtCustomersFilter.setClearButtonVisible(true);
        mainView.txtCustomersFilter.setValueChangeMode(ValueChangeMode.EAGER);

        gridCustomers.removeAllColumns();
        gridCustomers.addColumn(Customers::getFullName).setHeader("Pełna nazwa");
        gridCustomers.addColumn(Customers::getNip).setHeader("NIP");
        gridCustomers.addColumn(Customers::getStreet).setHeader("Ulica");
        gridCustomers.addColumn(Customers::getPostCode).setHeader("Kod pocztowy");
        gridCustomers.addColumn(Customers::getTown).setHeader("Miejscowość");
        gridCustomers.addColumn(Customers::getMail).setHeader("E-mail");


        btnSave.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        btnDelete.addThemeVariants(ButtonVariant.LUMO_ERROR);
        btnCancel.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        btnGetFromCEIDG.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        mainView.txtCustomersFilter.addValueChangeListener(e -> find());
        btnAddNewCustomer.addClickListener(e -> {
            gridCustomers.asSingleSelect().clear();
            updateCustomersForm(new Customers());
        });

        GridContextMenu<Customers> menu = gridCustomers.addContextMenu();
        menu.addItem("Edytuj", event ->
                updateCustomersForm(gridCustomers.asSingleSelect().getValue()));
        menu.addItem("Usuń", event -> deleteCustomer());

        btnSave.addClickListener(event -> saveCustomer());
        btnEdit.addClickListener(event -> updateCustomersForm
                (gridCustomers.asSingleSelect().getValue()));
        btnDelete.addClickListener(event -> deleteCustomer());
        btnCancel.addClickListener(event -> cancel());
        btnGetFromCEIDG.addClickListener(event -> getFromCEIDG(txtNip.getValue()));
        txtTown.addFocusListener(event -> getTownFromPostcode(txtPostcode.getValue()));

        HorizontalLayout buttons = new HorizontalLayout(btnSave, btnDelete, btnCancel);
        HorizontalLayout nip = new HorizontalLayout(txtNip, btnGetFromCEIDG);
        add(nip, txtFullName, txtStreet, txtPostcode, txtTown, txtMail, buttons);

        binder.bindInstanceFields(this);
    }

    private void saveCustomer() {
        Customers customer = binder.getBean();
        if(!txtFullName.isEmpty()) {
            if (customer.getId() != null) {
                customersService.updateCustomer(customer);
            } else {
                customersService.saveCustomer(customer);
            }
        }
        this.setVisible(false);
        gridCustomers.setItems(customersService.getCustomersList());
    }

    private void deleteCustomer() {
        Customers customer = gridCustomers.asSingleSelect().getValue();
        ConfirmationDialog confirmationDialog = new ConfirmationDialog();
        confirmationDialog.setTitle("");
        confirmationDialog.setQuestion("Skasować kontrahenta " + customer.getFullName() + "?");
        confirmationDialog.getConfirm().addClickListener(event -> {
            customersService.deleteCustomer(customer);
            this.setVisible(false);
            gridCustomers.setItems(customersService.getCustomersList());
            confirmationDialog.close();
        });
        confirmationDialog.open();
    }

    private void cancel() {
        this.setVisible(false);
    }

    public void updateCustomersForm(Customers customer) {
        binder.setBean(customer);
        if (customer == null) {
            setVisible(false);
        } else {
            setVisible(true);
            txtFullName.focus();
        }
    }

    private void find() {
        gridCustomers.setItems(customersService.findByFullName(mainView.txtCustomersFilter.getValue()));
    }

    private void getTownFromPostcode(String postcode) {
        txtTown.setValue(postcodeApiService.getTownFromPostcode(postcode).getTown());
        btnSave.focus();
    }

    private void getFromCEIDG(String nip) {
        binder.getFields().forEach(HasValue::clear);
        CeidgApiDto customerData = ceidgApiService.getData(nip);
            txtNip.setValue(customerData.getNip());
            txtFullName.setValue(customerData.getFullName());
            txtStreet.setValue(customerData.getStreet() + " " + customerData.getBuilding());
            txtPostcode.setValue(customerData.getPostCode());
            txtTown.setValue(customerData.getTown());
    }
}