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
import com.vaadin.flow.component.textfield.BigDecimalField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.data.binder.PropertyId;
import com.vaadin.flow.data.value.ValueChangeMode;
import org.jetbrains.annotations.NotNull;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Arrays;
import java.util.Locale;
import java.util.stream.Collectors;

public class ProductsForm extends FormLayout {

    @PropertyId("name")
    private TextField txtName = new TextField("Nazwa produktu");
    @PropertyId("vatRate")
    private final ComboBox<Integer> cmbVatRate = new ComboBox<>("Stawka VAT");
    @PropertyId("netPrice")
    private BigDecimalField txtNetPrice = new BigDecimalField("Cena netto",
            new BigDecimal(0.00), "0.00");
    @PropertyId("vatValue")
    private BigDecimalField txtVatValue = new BigDecimalField("Wartość VAT",
            new BigDecimal(0.00), "0.00");
    @PropertyId("grossPrice")
    private BigDecimalField txtGrossPrice = new BigDecimalField("Cena brutto",
            new BigDecimal(0.00), "0.00");
    private Button btnSave = new Button("Zapisz");
    private Button btnDelete = new Button("Usuń");
    private Button btnCancel = new Button("Zamknij");
    private Binder<Products> binder = new Binder<>(Products.class);
    private MainView mainView;
    private ProductsService productService = ProductsService.getInstance();

    public ProductsForm(@NotNull MainView mainView) {
        this.mainView = mainView;
        mainView.txtProductsFilter.setPlaceholder("Filtruj po nazwie ...");
        mainView.txtProductsFilter.setClearButtonVisible(true);
        mainView.txtProductsFilter.setValueChangeMode(ValueChangeMode.EAGER);
        mainView.txtProductsFilter.addValueChangeListener(e -> find());

        btnSave.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        btnDelete.addThemeVariants(ButtonVariant.LUMO_ERROR);
        btnCancel.addThemeVariants(ButtonVariant.LUMO_PRIMARY);

        btnSave.addClickListener(event -> saveProduct());
        btnDelete.addClickListener(event -> deleteProduct());
        btnCancel.addClickListener(event -> cancel());
        txtVatValue.addFocusListener(event -> calculateValues());

        HorizontalLayout buttons = new HorizontalLayout(btnSave, btnDelete, btnCancel);

        add(txtName, cmbVatRate, txtNetPrice, txtVatValue, txtGrossPrice, buttons);

        txtNetPrice.setLocale(Locale.ROOT);
        txtVatValue.setLocale(Locale.ROOT);
        txtGrossPrice.setLocale(Locale.ROOT);

        cmbVatRate.setItems(Arrays.stream(VatRate.values())
                .map(VatRate::getValue)
                .collect(Collectors.toList()));
        cmbVatRate.setValue(23);

        binder.bindInstanceFields(this);
    }

    private void saveProduct() {
        Products product = binder.getBean();
        if(!txtName.isEmpty()) {
            if (product.getId() != null) {
                productService.updateProduct(product);
            } else {
                productService.saveProduct(product);
            }
        }
        this.setVisible(false);
        mainView.gridProducts.setItems(productService.getProductsList());
    }

    private void deleteProduct() {
        Products product = binder.getBean();
        productService.deleteProduct(product);
        this.setVisible(false);
        mainView.gridProducts.setItems(productService.getProductsList());
    }

    private void cancel() {
        this.setVisible(false);
    }

    public void updateProductsForm(Products product) {
        binder.setBean(product);
        if (product == null) {
            setVisible(false);
        } else {
            setVisible(true);
            txtName.focus();
        }
    }

    private void find() {
        mainView.gridProducts.setItems(
                productService.findByName(mainView.txtProductsFilter.getValue())
        );
    }

    public void calculateValues() {
        if (txtNetPrice.getValue() != null) {
            BigDecimal vatRateValue = new BigDecimal(String.valueOf(cmbVatRate.getValue()));
            vatRateValue = vatRateValue.divide(BigDecimal.valueOf(100), 2, RoundingMode.HALF_UP);
            BigDecimal vatValue = txtNetPrice.getValue().multiply(vatRateValue).setScale(2, RoundingMode.HALF_UP);
            txtVatValue.setValue(vatValue);
            BigDecimal grossValue = txtNetPrice.getValue().add(vatValue).setScale(2, RoundingMode.HALF_UP);
            txtGrossPrice.setValue(grossValue);
            txtNetPrice.setValue(txtNetPrice.getValue().setScale(2, RoundingMode.HALF_UP));
        } else {
            txtNetPrice.focus();
        }
    }
}