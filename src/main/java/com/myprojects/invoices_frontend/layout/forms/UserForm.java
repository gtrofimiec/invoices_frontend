package com.myprojects.invoices_frontend.layout.forms;

import com.myprojects.invoices_frontend.domain.Users;
import com.myprojects.invoices_frontend.layout.MainView;
import com.myprojects.invoices_frontend.layout.dialogboxes.ConfirmationDialog;
import com.myprojects.invoices_frontend.layout.dialogboxes.FolderChooser;
import com.myprojects.invoices_frontend.services.PostcodeApiService;
import com.myprojects.invoices_frontend.services.UsersService;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.checkbox.Checkbox;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.contextmenu.GridContextMenu;
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
    @PropertyId("bank")
    private TextField txtBank = new TextField("Bank");
    @PropertyId("accountNumber")
    private TextField txtAccountNumber = new TextField("Numer konta");
    @PropertyId("active")
    private Checkbox chkIsActive = new Checkbox("Domyślny");
    @PropertyId("pdfPath")
    private TextField txtPdfPath = new TextField("Ścieżka zapisu faktur (pdf)", "D:/",
            "D:/");
    public final Button btnAddNewUser = new Button("Dodaj użytkownika ...");
    private Button btnEdit = new Button("Edytuj ...");
    private Button btnSave = new Button("Zapisz");
    private Button btnDelete = new Button("Usuń");
    private Button btnCancel = new Button("Zamknij");
    private Button btnPdfPath = new Button("Zmień ...");
    public Grid<Users> gridUser = new Grid<>(Users.class);
    public HorizontalLayout buttons = new HorizontalLayout();
    private Binder<Users> binder = new Binder<>(Users.class);
    private MainView mainView;
    private UsersService usersService = UsersService.getInstance();
    private final PostcodeApiService postcodeApiService = PostcodeApiService.getInstance();

    public UserForm(@NotNull MainView mainView) {
        this.mainView = mainView;

        buttons.add(btnAddNewUser, btnEdit);

        mainView.txtUserFilter.setPlaceholder("Filtruj po nazwie ...");
        mainView.txtUserFilter.setClearButtonVisible(true);
        mainView.txtUserFilter.setValueChangeMode(ValueChangeMode.EAGER);
        mainView.txtUserFilter.addValueChangeListener(e -> find());

        gridUser.removeAllColumns();
        gridUser.addColumn(Users::getFullName).setHeader("Pełna nazwa");
        gridUser.addColumn(Users::getNip).setHeader("NIP");
        gridUser.addColumn(Users::getStreet).setHeader("Ulica");
        gridUser.addColumn(Users::getPostCode).setHeader("Kod pocztowy");
        gridUser.addColumn(Users::getTown).setHeader("Miejscowość");
        gridUser.addColumn(Users::isActive).setHeader("Domyślny");
        gridUser.addColumn(Users::getBank).setHeader("Bank");

        btnSave.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        btnEdit.addClickListener(event -> updateUsersForm
                (gridUser.asSingleSelect().getValue()));
        btnDelete.addThemeVariants(ButtonVariant.LUMO_ERROR);
        btnCancel.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        btnAddNewUser.addClickListener(e -> {
            gridUser.asSingleSelect().clear();
            if(usersService.getUsersList().size() == 0) {
                updateUsersForm(new Users(true));
            } else {
                updateUsersForm(new Users());
            }
        });
        btnSave.addClickListener(event -> saveUser());
        btnDelete.addClickListener(event -> deleteUser());
        btnCancel.addClickListener(event -> cancel());
        btnPdfPath.addClickListener(event -> changePath());
        txtTown.addFocusListener(event -> getTownFromPostcode(txtPostcode.getValue()));

        GridContextMenu<Users> menu = gridUser.addContextMenu();
        menu.addItem("Edytuj", event ->
                updateUsersForm(gridUser.asSingleSelect().getValue()));
        menu.addItem("Usuń", event -> deleteUser());

        HorizontalLayout pdfPathLayout = new HorizontalLayout(txtPdfPath, btnPdfPath);
        HorizontalLayout buttonsLayout = new HorizontalLayout(btnSave, btnDelete, btnCancel);
        add(chkIsActive, txtFullName, txtNip, txtStreet, txtPostcode, txtTown, txtBank, txtAccountNumber,
                pdfPathLayout, buttonsLayout);
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
        gridUser.setItems(usersService.getUsersList());
    }

    private void deleteUser() {
        Users user = gridUser.asSingleSelect().getValue();
        ConfirmationDialog confirmationDialog = new ConfirmationDialog();
        confirmationDialog.setTitle("");
        confirmationDialog.setQuestion("Skasować użytkownika " + user.getFullName() + "?");
        confirmationDialog.getConfirm().addClickListener(event -> {
            usersService.deleteUser(user);
            this.setVisible(false);
            gridUser.setItems(usersService.getUsersList());
            confirmationDialog.close();
        });
        confirmationDialog.open();
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

    private void changePath() {
        FolderChooser folderChooser = new FolderChooser();
        txtPdfPath.setValue(folderChooser.getPath());
    }

    private void find() {
        gridUser.setItems(usersService.findByFullName(mainView.txtUserFilter.getValue()));
    }

    private void getTownFromPostcode(@NotNull String postcode) {
        txtTown.setValue(postcodeApiService.getTownFromPostcode(postcode).getTown());
        btnSave.focus();
    }
}