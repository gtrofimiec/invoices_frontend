package com.myprojects.invoices_frontend.layout.forms;

import com.myprojects.invoices_frontend.domain.Invoices;
import com.myprojects.invoices_frontend.layout.MainView;
import com.myprojects.invoices_frontend.services.InvoicesService;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.data.value.ValueChangeMode;
import org.jetbrains.annotations.NotNull;

public class InvoicesForm extends VerticalLayout {

    public Button btnAddNewInvoice = new Button("Dodaj fakturę ...");
    public Button btnEditInvoice = new Button("Edytuj ...");
    public HorizontalLayout invoicesButtons = new HorizontalLayout();
    private final MainView mainView;
    private final InvoicesService invoicesService = InvoicesService.getInstance();

    public InvoicesForm(@NotNull MainView mainView) {
        this.mainView = mainView;

        mainView.txtInvoicesFilter.setPlaceholder("Filtruj po numerze ...");
        mainView.txtInvoicesFilter.setClearButtonVisible(true);
        mainView.txtInvoicesFilter.setValueChangeMode(ValueChangeMode.EAGER);
        mainView.txtInvoicesFilter.addValueChangeListener(e -> find());

        btnEditInvoice.addClickListener(event -> mainView.editInvoiceForm.updateInvoicesForm(
                mainView.gridInvoices.asSingleSelect().getValue()));
        btnAddNewInvoice.addClickListener(event -> addNewInvoice());

        invoicesButtons.add(btnAddNewInvoice, btnEditInvoice);
    }

    public void updateInvoicesForm(Invoices invoice) {
        if (invoice == null) {
            setVisible(false);
        } else {
            mainView.gridInvoices.setItems(invoicesService.getInvoicesList());
        }
    }

    public void addNewInvoice() {
        mainView.mainContent.removeAll();
        mainView.mainContent.add(mainView.gridNewInvoiceProductsList);
        mainView.gridNewInvoiceProductsList.asSingleSelect().clear();
        mainView.gridNewInvoiceProductsList.setVisible(true);
        mainView.itemsToolbar.setVisible(false);
        mainView.newInvoiceForm.newInvoiceHeaderToolbar.setVisible(true);
        mainView.newInvoiceForm.newInvoicePaymentsToolbar.setVisible(true);
        mainView.newInvoiceForm.newInvoiceFooterToolbar.setVisible(true);
        mainView.newInvoiceForm.clearNewInvoicesForm();
    }

    private void find() {
        mainView.gridInvoices.setItems(invoicesService.findByNumber(mainView.txtInvoicesFilter.getValue()));
    }
}