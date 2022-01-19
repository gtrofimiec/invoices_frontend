package com.myprojects.invoices_frontend.layout.forms;

import com.myprojects.invoices_frontend.MainView;
import com.myprojects.invoices_frontend.apis.ceidgapi.CeidgApiService;
import com.myprojects.invoices_frontend.apis.postcodeapi.PostcodeApiService;
import com.myprojects.invoices_frontend.domain.Customers;
import com.myprojects.invoices_frontend.domain.dtos.CeidgApiDto;
import com.myprojects.invoices_frontend.services.CustomersService;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.data.converter.StringToLongConverter;
import org.jetbrains.annotations.NotNull;

public class CustomersForm extends FormLayout {

    private TextField txtTd = new TextField("Id");
    private TextField txtFullName = new TextField("Pełna nazwa");
    private TextField txtNip = new TextField("NIP (tylko cyfry)", "2367852376", "2367852376");
    private TextField txtStreet = new TextField("Ulica");
    private TextField txtPostcode = new TextField("Kod pocztowy");
    private TextField txtTown = new TextField("Miejscowość");
    private Button btnSave = new Button("Zapisz");
    private Button btnDelete = new Button("Usuń");
    private Button btnGetFromCEIDG = new Button("Pobierz z CEIDG");
    private Binder<Customers> binder = new Binder<>(Customers.class);
    private MainView mainView;
    private CustomersService service = CustomersService.getInstance();
    private final PostcodeApiService postcodeApiService = PostcodeApiService.getInstance();
    private final CeidgApiService ceidgApiService = CeidgApiService.getInstance();

    public CustomersForm(MainView mainView) {
        this.mainView = mainView;
        HorizontalLayout buttons = new HorizontalLayout(btnSave, btnDelete);
        HorizontalLayout nip = new HorizontalLayout(txtNip, btnGetFromCEIDG);
        btnSave.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        btnDelete.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        btnGetFromCEIDG.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        txtTown.addFocusListener(event -> getTownFromPostcode(txtPostcode.getValue()));
        btnGetFromCEIDG.addClickListener(event -> getFromCEIDG(txtNip.getValue()));
        add(nip, txtFullName, txtStreet, txtPostcode, txtTown, buttons);
        btnSave.addClickListener(event -> save());
        btnDelete.addClickListener(event -> delete());
        binder.forField(txtTd)
                .withNullRepresentation("")
                .withConverter(new StringToLongConverter("No long value"))
                .bind(Customers::getId, Customers::setId);
        binder.bindInstanceFields(this);
    }

    private void save() {
        Customers customer = binder.getBean();
        service.save(customer);
        mainView.refresh();
        update(null);
    }

    private void delete() {
        Customers customer = binder.getBean();
        service.delete(customer);
        mainView.refresh();
        update(null);
    }

    public void update(Customers customer) {
        binder.setBean(customer);
        if (customer == null) {
            setVisible(false);
        } else {
            setVisible(true);
            txtFullName.focus();
        }
    }

    private void getTownFromPostcode(@NotNull String postcode) {
        txtTown.setValue(postcodeApiService.getTownFromPostcode(postcode).getTown());
        mainView.refresh();
    }

    private void getFromCEIDG(String nip) {
        CeidgApiDto customerData = ceidgApiService.getData(nip);
            txtFullName.setValue(customerData.getFullName());
            txtStreet.setValue(customerData.getStreet() + " " + customerData.getBuilding());
            txtPostcode.setValue(customerData.getPostCode());
            txtTown.setValue(customerData.getTown());
        mainView.refresh();
    }
}