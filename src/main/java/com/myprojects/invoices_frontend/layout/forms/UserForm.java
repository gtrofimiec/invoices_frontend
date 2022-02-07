package com.myprojects.invoices_frontend.layout.forms;

import com.myprojects.invoices_frontend.MainView;
import com.myprojects.invoices_frontend.domain.Users;
import com.myprojects.invoices_frontend.services.PostcodeApiService;
import com.myprojects.invoices_frontend.services.UsersService;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.checkbox.Checkbox;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.data.binder.PropertyId;
import com.vaadin.flow.data.value.ValueChangeMode;
import org.jetbrains.annotations.NotNull;

public class UserForm extends FormLayout {

    @PropertyId("fullName")
    private TextField txtFullName = new TextField("Pełna nazwa");
    @PropertyId("nip")
    private TextField txtNip = new TextField("NIP");
    @PropertyId("street")
    private TextField txtStreet = new TextField("Ulica");
    @PropertyId("postcode")
    private TextField txtPostcode = new TextField("Kod pocztowy");
    @PropertyId("town")
    private TextField txtTown = new TextField("Miejsowość");
    @PropertyId("active")
    private Checkbox chkIsActive = new Checkbox("Domyślny");
    private Button btnSave = new Button("Zapisz");
    private Button btnDelete = new Button("Usuń");
    private Button btnCancel = new Button("Zamknij");
    private Binder<Users> binder = new Binder<>(Users.class);
    private MainView mainView;
    private UsersService usersService = UsersService.getInstance();
    private final PostcodeApiService postcodeApiService = PostcodeApiService.getInstance();

    public UserForm(@NotNull MainView mainView) {
        this.mainView = mainView;
        mainView.txtUserFilter.setPlaceholder("Filtruj po nazwie ...");
        mainView.txtUserFilter.setClearButtonVisible(true);
        mainView.txtUserFilter.setValueChangeMode(ValueChangeMode.EAGER);
        mainView.txtUserFilter.addValueChangeListener(e -> find());

        btnSave.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        btnDelete.addThemeVariants(ButtonVariant.LUMO_ERROR);
        btnCancel.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        btnSave.addClickListener(event -> saveUser());
        btnDelete.addClickListener(event -> deleteUser());
        btnCancel.addClickListener(event -> cancel());
        txtTown.addFocusListener(event -> getTownFromPostcode(txtPostcode.getValue()));
        HorizontalLayout buttons = new HorizontalLayout(btnSave, btnDelete, btnCancel);
        add(chkIsActive, txtFullName, txtNip, txtStreet, txtPostcode, txtTown, buttons);
        binder.bindInstanceFields(this);
    }

    private void saveUser() {
        Users user = binder.getBean();
        if(!txtFullName.isEmpty()) {
            if (user.getId() != null) {
                usersService.updateUser(user);
            } else {
                usersService.saveUser(user);
            }
        }
        this.setVisible(false);
        mainView.gridUser.setItems(usersService.getUsersList());
    }

    private void deleteUser() {
        Users user = binder.getBean();
        usersService.deleteUser(user);
        this.setVisible(false);
        mainView.gridUser.setItems(usersService.getUsersList());
    }

    private void cancel() {
        this.setVisible(false);
    }

    public void updateUsersForm(Users user) {
        binder.setBean(user);
        if (user == null) {
            setVisible(false);
        } else {
            setVisible(true);
            txtFullName.focus();
        }
    }

    private void find() {
        mainView.gridUser.setItems(
                usersService.findByFullName(mainView.txtUserFilter.getValue())
        );
    }

    private void getTownFromPostcode(@NotNull String postcode) {
        txtTown.setValue(postcodeApiService.getTownFromPostcode(postcode).getTown());
        btnSave.focus();
    }
}