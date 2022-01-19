package com.myprojects.invoices_frontend.layout.forms;

import com.myprojects.invoices_frontend.MainView;
import com.myprojects.invoices_frontend.domain.User;
import com.myprojects.invoices_frontend.services.UserService;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.data.converter.StringToLongConverter;

public class UserForm extends FormLayout {

    private TextField id = new TextField("Id");
    private TextField fullName = new TextField("Pełna nazwa");
    private TextField nip = new TextField("NIP");
    private TextField street = new TextField("Ulica");
    private TextField postcode = new TextField("Kod pocztowy");
    private TextField town = new TextField("Miejsowość");
    private Button save = new Button("Zapisz");
    private Button delete = new Button("Usuń");
    private Binder<User> binder = new Binder<>(User.class);
    private MainView mainView;
    private UserService service = UserService.getInstance();

    public UserForm(MainView mainView) {
        this.mainView = mainView;
        HorizontalLayout buttons = new HorizontalLayout(save, delete);
        save.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        delete.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        add(fullName, nip, street, postcode, town, buttons);
        save.addClickListener(event -> save());
        delete.addClickListener(event -> delete());
        binder.forField(id)
                .withNullRepresentation("")
                .withConverter(new StringToLongConverter("Not a long value"))
                .bind(User::getId, User::setId);
        binder.bindInstanceFields(this);
    }

    private void save() {
        User user = binder.getBean();
        service.save(user);
        mainView.refresh();
        update(null);
    }

    private void delete() {
        User user = binder.getBean();
        service.delete(user);
        mainView.refresh();
        update(null);
    }

    public void update(User user) {
        binder.setBean(user);
        if (user == null) {
            setVisible(false);
        } else {
            setVisible(true);
            fullName.focus();
        }
    }
}