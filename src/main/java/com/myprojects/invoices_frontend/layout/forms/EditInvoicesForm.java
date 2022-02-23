package com.myprojects.invoices_frontend.layout.forms;

import com.itextpdf.text.DocumentException;
import com.myprojects.invoices_frontend.domain.*;
import com.myprojects.invoices_frontend.domain.dtos.MailDto;
import com.myprojects.invoices_frontend.layout.MainView;
import com.myprojects.invoices_frontend.layout.dialogboxes.ConfirmationDialog;
import com.myprojects.invoices_frontend.layout.dialogboxes.ShowNotification;
import com.myprojects.invoices_frontend.layout.grids.GridCreator;
import com.myprojects.invoices_frontend.layout.pdfgenerator.PdfGenerator;
import com.myprojects.invoices_frontend.services.*;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.contextmenu.GridContextMenu;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.textfield.BigDecimalField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.PropertyId;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.Period;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class EditInvoicesForm extends FormLayout {

    private static final Logger LOGGER = LoggerFactory.getLogger(EditInvoicesForm.class);

    boolean isPdfGenerated = false;
    Invoices activeInvoice;
    Customers customer;
    Users user;
    List<Products> productsList;
    @PropertyId("number")
    public TextField txtNumber = new TextField("Numer faktury");
    @PropertyId("date")
    public DatePicker dtpDate = new DatePicker("Data wystawienia");
    @PropertyId("user")
    public TextField txtUser = new TextField("Sprzedawca");
    @PropertyId("customer")
    public TextField txtCustomer = new TextField("Kontrahent");
    @PropertyId("netSum")
    public BigDecimalField txtNetSum = new BigDecimalField("Wartość netto",
            new BigDecimal("0.00"), "0.00");
    @PropertyId("vatSum")
    public BigDecimalField txtVatSum = new BigDecimalField("Wartość VAT",
            new BigDecimal("0.00"), "0.00");
    @PropertyId("grossSum")
    public BigDecimalField txtGrossSum = new BigDecimalField("DO ZAPŁATY",
            new BigDecimal("0.00"), "0.00");
    @PropertyId("paymentMethod")
    public ComboBox<String> cmbPaymentMethod = new ComboBox<>("Forma płatności");
    public TextField txtPayment = new TextField("Termin płatności");
    @PropertyId("paymentDate")
    public DatePicker dtpPaymentDate = new DatePicker("Data płatności");
    public Button btnSave = new Button("Zapisz");
    public Button btnCancel = new Button("Zamknij");
    public Button btnShowCustomersList = new Button("Wybierz ...");
    public Button btnAddCustomer = new Button("Zmień ...");
    public Button btnCancelFromProductList = new Button("Wyjdź");
    public Button btnShowProductsList = new Button("Wybierz ...");
    public Button btnAddProduct = new Button("Dodaj produkt ...");
    public Button btnShowUsersList = new Button("Zmień ...");
    public Button btnAddUser = new Button("Wybierz ...");
    public Button btnRemoveProduct = new Button("Usuń produkt ...");
    public Button btnPdfGenerator = new Button("Do pdf ...");
    @PropertyId("productsList")
    private Grid<Products> gridProductsList;
    public HorizontalLayout editInvoiceFooterToolbar = new HorizontalLayout();
    public HorizontalLayout editInvoiceHeaderToolbar = new HorizontalLayout();
    public HorizontalLayout editInvoicePaymentsToolbar = new HorizontalLayout();
    private final MainView mainView;
    private final InvoicesService invoicesService = InvoicesService.getInstance();
    private final CustomersService customersService = CustomersService.getInstance();
    private final UsersService usersService = UsersService.getInstance();
    private final ProductsService productsService = ProductsService.getInstance();
    private final MailService mailService = MailService.getInstance();

    public EditInvoicesForm(@NotNull MainView mainView) {
        this.mainView = mainView;

        btnSave.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        btnCancel.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        btnCancelFromProductList.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        btnShowUsersList.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        btnAddCustomer.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        btnRemoveProduct.addThemeVariants(ButtonVariant.LUMO_ERROR);
        btnPdfGenerator.addThemeVariants(ButtonVariant.LUMO_PRIMARY);

        btnSave.addClickListener(event -> updateInvoice());
        btnCancel.addClickListener(event -> cancel());
        btnCancelFromProductList.addClickListener(event -> cancelFromProductList());
        btnShowUsersList.addClickListener(event -> selectUser());
        btnAddCustomer.addClickListener(event -> selectCustomer());
        btnAddProduct.addClickListener(event -> selectProduct());
        btnRemoveProduct.addClickListener(event -> removeProductFromList());
        btnShowCustomersList.addClickListener(event -> addCustomerToInvoice());
        btnShowProductsList.addClickListener(event -> addProductToList());
        btnAddUser.addClickListener(event -> addUserToInvoice());
        dtpPaymentDate.addFocusListener(event -> calculatePaymentDate());
        btnPdfGenerator.addClickListener(event -> {
            try {
                pdfGenerator(activeInvoice);
                isPdfGenerated = true;
            } catch (IOException | DocumentException e) {
                e.printStackTrace();
            }
        });

        editInvoiceHeaderToolbar.add(txtNumber, dtpDate, txtUser, btnShowUsersList, txtCustomer,
                btnAddCustomer);
        editInvoicePaymentsToolbar.add(cmbPaymentMethod, txtPayment, dtpPaymentDate);
        editInvoiceFooterToolbar.add(txtNetSum, txtVatSum, txtGrossSum, btnAddProduct, btnShowCustomersList,
                btnAddUser, btnShowProductsList, btnSave, btnCancel, btnCancelFromProductList,
                btnPdfGenerator);

        GridContextMenu<Invoices> menu = mainView.gridInvoices.addContextMenu();
        menu.addItem("Edytuj fakturę", event -> updateInvoicesForm(mainView.gridInvoices.asSingleSelect().getValue()));
        menu.addItem("Usuń fakturę", event -> deleteInvoice());
        menu.addItem("Zapisz do pdf", event -> {
            try {
                pdfGenerator(mainView.gridInvoices.asSingleSelect().getValue());
                isPdfGenerated = true;
            } catch (IOException | DocumentException e) {
                e.printStackTrace();
            }
        });
        menu.addItem("Wyślij fakturę mailem", event -> {
            try {
                sendMail();
            } catch (DocumentException | IOException e) {
                e.printStackTrace();
            }
        });

        btnPdfGenerator.setVisible(true);
        btnShowProductsList.setVisible(false);
        btnShowCustomersList.setVisible(false);
        btnAddUser.setVisible(false);
        btnCancelFromProductList.setVisible(false);
        cmbPaymentMethod.setItems(loadPaymentMethods());
    }

    private void updateInvoice() {
        activeInvoice.setNumber(txtNumber.getValue());
        activeInvoice.setDate(dtpDate.getValue());
        activeInvoice.setNetSum(txtNetSum.getValue());
        activeInvoice.setVatSum(txtVatSum.getValue());
        activeInvoice.setGrossSum(txtGrossSum.getValue());
        activeInvoice.setPaymentMethod(cmbPaymentMethod.getValue());
        activeInvoice.setPaymentDate(dtpPaymentDate.getValue());
        activeInvoice.setCustomer(customer);
        activeInvoice.setUser(user);
        activeInvoice.setProductsList(productsList);

        if(isInvoiceComplete(activeInvoice)) {
            invoicesService.updateInvoice(activeInvoice);
            mainView.gridInvoices.setItems(invoicesService.getInvoicesList());
            cancel();
        } else {
            ShowNotification showNotification = new ShowNotification("Dane do faktury niekompletne", 5000);
            showNotification.show();
        }
    }

    private void deleteInvoice() {
        activeInvoice = mainView.gridInvoices.asSingleSelect().getValue();
        ConfirmationDialog confirmationDialog = new ConfirmationDialog();
        confirmationDialog.setTitle("");
        confirmationDialog.setQuestion("Skasować fakturę nr " + activeInvoice.getNumber() + "?");
        confirmationDialog.getConfirm().addClickListener(event -> {
            invoicesService.deleteInvoice(activeInvoice);
            mainView.gridInvoices.setItems(invoicesService.getInvoicesList());
            confirmationDialog.close();
            cancel();
        });
        confirmationDialog.open();
    }

    public void updateInvoicesForm(Invoices invoice) {
        if (invoice == null) {
            setVisible(false);
        } else {
            activeInvoice = invoice;
            customer = invoice.getCustomer();
            user = invoice.getUser();
            productsList = invoice.getProductsList();
            GridCreator gridCreator = new GridCreator(mainView);
            gridProductsList = gridCreator.createEditInvoiceProductsList();
            txtNumber.setValue(activeInvoice.getNumber());
            dtpDate.setValue(activeInvoice.getDate());
            txtUser.setValue(user.getFullName());
            txtCustomer.setValue(customer.getFullName());
            cmbPaymentMethod.setValue(activeInvoice.getPaymentMethod());
            dtpPaymentDate.setValue(activeInvoice.getPaymentDate());

            Period period = Period.between(dtpPaymentDate.getValue(), dtpDate.getValue());
            int days = Math.abs(period.getDays());
            txtPayment.setValue(String.valueOf(days));

            txtNetSum.setValue(activeInvoice.getNetSum());
            txtVatSum.setValue(activeInvoice.getVatSum());
            txtGrossSum.setValue(activeInvoice.getGrossSum());
            gridProductsList.setItems(activeInvoice.getProductsList());

            mainView.mainContent.removeAll();
            mainView.mainContent.add(gridProductsList);
            gridProductsList.asSingleSelect().clear();
            gridProductsList.setVisible(true);
            mainView.itemsToolbar.setVisible(false);
            editInvoiceHeaderToolbar.setVisible(true);
            editInvoicePaymentsToolbar.setVisible(true);
            editInvoiceFooterToolbar.setVisible(true);
            txtNumber.focus();
        }
    }

    private void cancel() {
        editInvoiceHeaderToolbar.setVisible(false);
        editInvoicePaymentsToolbar.setVisible(false);
        editInvoiceFooterToolbar.setVisible(false);
        mainView.invoiceMenuClick();
    }

    private void cancelFromProductList() {
        btnShowProductsList.setVisible(false);
        mainView.mainContent.remove(mainView.gridSelectProduct);
        mainView.mainContent.add(gridProductsList);
        mainView.mainContent.add(this);
        btnSave.setVisible(true);
        btnCancel.setVisible(true);
        btnAddProduct.setVisible(true);
        btnRemoveProduct.setVisible(true);
    }

    private void selectUser() {
        mainView.mainContent.remove(gridProductsList);
        mainView.mainContent.remove(this);
        mainView.mainContent.add(mainView.gridSelectUser);
        btnSave.setVisible(false);
        btnCancel.setVisible(false);
        btnAddProduct.setVisible(false);
        btnRemoveProduct.setVisible(false);
        mainView.gridSelectUser.setItems(usersService.getUsersList());
        btnAddUser.setVisible(true);
        mainView.gridSelectUser.setVisible(true);
    }

    private void selectCustomer() {
        mainView.mainContent.remove(gridProductsList);
        mainView.mainContent.remove(this);
        mainView.mainContent.add(mainView.gridSelectCustomer);
        btnSave.setVisible(false);
        btnCancel.setVisible(false);
        btnAddProduct.setVisible(false);
        btnRemoveProduct.setVisible(false);
        mainView.gridSelectCustomer.setItems(customersService.getCustomersList());
        btnShowCustomersList.setVisible(true);
        mainView.gridSelectCustomer.setVisible(true);
    }

    private void addUserToInvoice() {
        user = mainView.gridSelectUser.asSingleSelect().getValue();
        txtUser.setValue(user.getFullName());
        btnAddUser.setVisible(false);
        mainView.mainContent.remove(mainView.gridSelectUser);
        mainView.mainContent.add(gridProductsList);
        mainView.mainContent.add(this);
        btnSave.setVisible(true);
        btnCancel.setVisible(true);
        btnAddProduct.setVisible(true);
        btnRemoveProduct.setVisible(true);
    }

    private void addCustomerToInvoice() {
        customer = mainView.gridSelectCustomer.asSingleSelect().getValue();
        txtCustomer.setValue(customer.getFullName());
        btnShowCustomersList.setVisible(false);
        mainView.mainContent.remove(mainView.gridSelectCustomer);
        mainView.mainContent.add(gridProductsList);
        mainView.mainContent.add(this);
        btnSave.setVisible(true);
        btnCancel.setVisible(true);
        btnAddProduct.setVisible(true);
        btnRemoveProduct.setVisible(true);
    }

    private void selectProduct() {
        mainView.mainContent.remove(gridProductsList);
        mainView.mainContent.remove(this);
        mainView.mainContent.add(mainView.gridSelectProduct);
        btnSave.setVisible(false);
        btnCancel.setVisible(false);
        btnAddProduct.setVisible(false);
        btnRemoveProduct.setVisible(false);
        btnPdfGenerator.setVisible(false);
        mainView.gridSelectProduct.setItems(productsService.getProductsList());
        btnShowProductsList.setVisible(true);
        btnCancelFromProductList.setVisible(true);
        mainView.gridSelectProduct.setVisible(true);
    }

    private void addProductToList() {
        Products product = mainView.gridSelectProduct.asSingleSelect().getValue();
        productsList.add(product);
        activeInvoice.setProductsList(productsList);
        txtNetSum.setValue(txtNetSum.getValue().add(product.getNetPrice()));
        txtVatSum.setValue(txtVatSum.getValue().add(product.getVatValue()));
        txtGrossSum.setValue(txtGrossSum.getValue().add(product.getGrossPrice()));
        gridProductsList.setItems(productsList);
        btnShowProductsList.setVisible(false);
        btnCancelFromProductList.setVisible(false);
        mainView.mainContent.remove(mainView.gridSelectProduct);
        mainView.mainContent.add(gridProductsList);
        mainView.mainContent.add(this);
        btnSave.setVisible(true);
        btnCancel.setVisible(true);
        btnAddProduct.setVisible(true);
        btnRemoveProduct.setVisible(true);
        btnPdfGenerator.setVisible(true);
    }

    private void removeProductFromList() {
        Products product = mainView.gridSelectProduct.asSingleSelect().getValue();
        productsList.remove(product);
        activeInvoice.setProductsList(productsList);
        gridProductsList.setItems(productsList);
        btnShowProductsList.setVisible(false);
        btnCancelFromProductList.setVisible(false);
        mainView.mainContent.remove(mainView.gridSelectProduct);
        mainView.mainContent.add(gridProductsList);
        mainView.mainContent.add(this);
        btnSave.setVisible(true);
        btnCancel.setVisible(true);
        btnAddProduct.setVisible(true);
        btnRemoveProduct.setVisible(true);
        btnPdfGenerator.setVisible(true);
    }

    private List<String> loadPaymentMethods() {
        return Arrays.stream(PaymentMethods.values())
                .map(PaymentMethods::getPaymentMethod)
                .collect(Collectors.toList());
    }

    private void calculatePaymentDate() {
        LocalDate paymentDate = dtpDate.getValue();
        if (!Objects.equals(txtPayment.getValue(), "0")) {
            int payment = Integer.parseInt(txtPayment.getValue());
            paymentDate = dtpDate.getValue().plusDays(payment);
        }
        dtpPaymentDate.setValue(paymentDate);
    }

    public void pdfGenerator(Invoices invoice) throws IOException, DocumentException {
        PdfGenerator pdfGenerator = new PdfGenerator(invoice);
        try {
            File invoiceFile = new File(invoice.getUser().getPdfPath() + "/Faktura nr "
                    + invoice.getNumber().replace("/", "_") + " z dnia "
                    +  invoice.getDate() + ".pdf");
            if(invoiceFile.exists()) {
                invoiceFile.delete();
            }
            pdfGenerator.generatePdf();
            isPdfGenerated = true;
            ConfirmationDialog sendEmailDialog = new ConfirmationDialog();
            sendEmailDialog.setTitle("");
            sendEmailDialog.setQuestion("Czy wysłać fakturę mailem na domyślny adres?");
            sendEmailDialog.addConfirmationListener(event -> {
                try {
                    sendMail();
                } catch (DocumentException | IOException e) {
                    e.printStackTrace();
                }
                sendEmailDialog.close();
            });
            sendEmailDialog.open();
        }  catch (IOException | DocumentException e) {
            e.printStackTrace();
        }
        cancel();
    }

    public void sendMail() throws DocumentException, IOException {
        activeInvoice = mainView.gridInvoices.asSingleSelect().getValue();
        MailDto mailDto = new MailDto();
        File invoiceFile = new File(activeInvoice.getUser().getPdfPath() + "/Faktura nr "
                +  activeInvoice.getNumber().replace("/", "_") + " z dnia "
                +  activeInvoice.getDate() + ".pdf");
        if(!invoiceFile.exists()) {
            PdfGenerator pdfGenerator = new PdfGenerator(activeInvoice);
            invoiceFile = pdfGenerator.generatePdf();
        }
        mailDto.setMailTo(activeInvoice.getCustomer().getMail());
        mailDto.setSubject("Faktura VAT nr " + activeInvoice.getNumber() + " z dnia "
                + activeInvoice.getDate());
        mailDto.setMessage("W załączniku wysyłam fakturę VAT nr " + activeInvoice.getNumber() + " z dnia "
                + activeInvoice.getDate());
        mailDto.setInvoice(invoiceFile);
        mailDto.setAttachmentName(invoiceFile.getName());

        mailService.sendMail(mailDto);
    }

    boolean isInvoiceComplete(@NotNull Invoices invoice) {
        return !invoice.getNumber().isEmpty() &&
                !invoice.getDate().toString().isEmpty() &&
                !invoice.getNetSum().toString().isEmpty() &&
                !invoice.getVatSum().toString().isEmpty() &&
                !invoice.getGrossSum().toString().isEmpty() &&
                !invoice.getPaymentMethod().isEmpty() &&
                !invoice.getPaymentDate().toString().isEmpty() &&
                invoice.getCustomer() != null &&
                invoice.getUser() != null &&
                !invoice.getProductsList().isEmpty();
    }
}