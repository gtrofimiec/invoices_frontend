package com.myprojects.invoices_frontend.layout.forms;

import com.myprojects.invoices_frontend.MainView;
import com.myprojects.invoices_frontend.domain.Products;
import com.myprojects.invoices_frontend.domain.VatRate;
import com.myprojects.invoices_frontend.services.ProductsService;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.data.converter.StringToBigDecimalConverter;
import com.vaadin.flow.data.converter.StringToLongConverter;

public class ProductsForm extends FormLayout {

    private TextField txtId = new TextField("Id");
    private TextField txtName = new TextField("Nazwa produktu");
    private ComboBox<VatRate> cmbVatRate = new ComboBox<>("Stawka VAT");
    private TextField txtNetPrice = new TextField("Cena netto");
    private TextField txtVatValue = new TextField("Wartość VAT");
    private TextField txtGrossPrice = new TextField("Cena brutto");
    private Button btnSave = new Button("Zapisz");
    private Button btnDelete = new Button("Usuń");
    private Binder<Products> binder = new Binder<>(Products.class);
    private MainView mainView;
    private ProductsService service = ProductsService.getInstance();

    public ProductsForm(MainView mainView) {
        this.mainView = mainView;
        HorizontalLayout buttons = new HorizontalLayout(btnSave, btnDelete);
        btnSave.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        add(txtName, cmbVatRate, txtNetPrice, txtVatValue, txtGrossPrice, buttons);
        btnSave.addClickListener(event -> save());
        btnDelete.addClickListener(event -> delete());
//        binder.forField(txtId)
//                .withNullRepresentation("")
//                .withConverter(new StringToLongConverter("Not a long value"))
//                .bind(Products::getId, Products::setId);
        binder.forField(txtNetPrice)
                .withNullRepresentation("")
                .withConverter(new StringToBigDecimalConverter("Not a bigdecimal value"))
                .bind(Products::getNetPrice, Products::setNetPrice);
        binder.forField(txtVatValue)
                .withNullRepresentation("")
                .withConverter(new StringToBigDecimalConverter("Not a bigdecimal value"))
                .bind(Products::getVatValue, Products::setVatValue);
        binder.forField(txtGrossPrice)
                .withNullRepresentation("")
                .withConverter(new StringToBigDecimalConverter("Not a bigdecimal value"))
                .bind(Products::getGrossPrice, Products::setGrossPrice);
        binder.bindInstanceFields(this);
    }

    private void save() {
        Products product = binder.getBean();
        service.save(product);
        mainView.refresh();
        update(null);
    }

    private void delete() {
        Products product = binder.getBean();
        service.delete(product);
        mainView.refresh();
        update(null);
    }

    public void update(Products product) {
        binder.setBean(product);
        if (product == null) {
            setVisible(false);
        } else {
            setVisible(true);
            txtName.focus();
        }
    }
}
