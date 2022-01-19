package com.myprojects.invoices_frontend.layout.forms;

import com.myprojects.invoices_frontend.MainView;
import com.myprojects.invoices_frontend.domain.Customers;
import com.myprojects.invoices_frontend.domain.Invoices;
import com.myprojects.invoices_frontend.services.InvoicesService;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.data.converter.StringToDateConverter;
import com.vaadin.flow.data.converter.StringToLongConverter;

public class InvoicesForm extends FormLayout {

    private TextField id = new TextField("Id");
    private TextField number = new TextField("Numer faktury");
    private TextField date = new TextField("Data wystawienia");
    private ComboBox<Customers> customersList = new ComboBox<>("Kontrahenci");
    private Button save = new Button("Zapisz");
    private Button delete = new Button("Usuń");
    private Binder<Invoices> binder = new Binder<>(Invoices.class);
    private MainView mainView;
    private InvoicesService service = InvoicesService.getInstance();

    public InvoicesForm(MainView mainView) {
        this.mainView = mainView;
        HorizontalLayout buttons = new HorizontalLayout(save, delete);
        save.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        add(number, date, customersList, buttons);
        save.addClickListener(event -> save());
        delete.addClickListener(event -> delete());
        binder.forField(id)
                .withNullRepresentation("")
                .withConverter(new StringToLongConverter("No long value"))
                .bind(Invoices::getId, Invoices::setId);
        binder.forField(date)
                .withNullRepresentation("")
                .withConverter(new StringToDateConverter())
                .bind(Invoices::getDate, Invoices::setDate);
        binder.bindInstanceFields(this);
    }

    private void save() {
        Invoices invoice = binder.getBean();
        service.save(invoice);
        mainView.refresh();
        update(null);
    }

    private void delete() {
        Invoices invoice = binder.getBean();
        service.delete(invoice);
        mainView.refresh();
        update(null);
    }

    public void update(Invoices invoice) {
        binder.setBean(invoice);
        if (invoice == null) {
            setVisible(false);
        } else {
            setVisible(true);
            number.focus();
        }
    }
}