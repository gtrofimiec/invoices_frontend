package com.myprojects.invoices_frontend.layout.forms;

import com.myprojects.invoices_frontend.MainView;
import com.myprojects.invoices_frontend.domain.Customers;
import com.myprojects.invoices_frontend.domain.dtos.CeidgApiDto;
import com.myprojects.invoices_frontend.services.CeidgApiService;
import com.myprojects.invoices_frontend.services.CustomersService;
import com.myprojects.invoices_frontend.services.PostcodeApiService;
import com.vaadin.flow.component.HasValue;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.formlayout.FormLayout;
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
    private Button btnSave = new Button("Zapisz");
    private Button btnDelete = new Button("Usuń");
    private Button btnCancel = new Button("Zamknij");
    private Button btnGetFromCEIDG = new Button("Pobierz z CEIDG");
    private Binder<Customers> binder = new Binder<>(Customers.class);
    private MainView mainView;
    private CustomersService customersService = CustomersService.getInstance();
    private final PostcodeApiService postcodeApiService = PostcodeApiService.getInstance();
    private final CeidgApiService ceidgApiService = CeidgApiService.getInstance();

    public CustomersForm(@NotNull MainView mainView) {
        this.mainView = mainView;

        mainView.txtCustomersFilter.setPlaceholder("Filtruj po nazwie ...");
        mainView.txtCustomersFilter.setClearButtonVisible(true);
        mainView.txtCustomersFilter.setValueChangeMode(ValueChangeMode.EAGER);

        btnSave.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        btnDelete.addThemeVariants(ButtonVariant.LUMO_ERROR);
        btnCancel.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        btnGetFromCEIDG.addThemeVariants(ButtonVariant.LUMO_PRIMARY);

        mainView.txtCustomersFilter.addValueChangeListener(e -> find());
        btnSave.addClickListener(event -> saveCustomer());
        btnDelete.addClickListener(event -> deleteCustomer());
        btnCancel.addClickListener(event -> cancel());
        btnGetFromCEIDG.addClickListener(event -> getFromCEIDG(txtNip.getValue()));
        txtTown.addFocusListener(event -> getTownFromPostcode(txtPostcode.getValue()));

        HorizontalLayout buttons = new HorizontalLayout(btnSave, btnDelete, btnCancel);
        HorizontalLayout nip = new HorizontalLayout(txtNip, btnGetFromCEIDG);
        add(nip, txtFullName, txtStreet, txtPostcode, txtTown, buttons);
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
        mainView.gridCustomers.setItems(customersService.getCustomersList());
    }

    private void deleteCustomer() {
        Customers customer = binder.getBean();
        customersService.deleteCustomer(customer);
        this.setVisible(false);
        mainView.gridCustomers.setItems(customersService.getCustomersList());
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
        mainView.gridCustomers.setItems(
                customersService.findByFullName(mainView.txtCustomersFilter.getValue())
        );
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