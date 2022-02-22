package com.myprojects.invoices_frontend.exceptions;

import com.myprojects.invoices_frontend.layout.MainView;
import com.myprojects.invoices_frontend.layout.dialogboxes.ShowNotification;

public class NoConnectionToDB {

    MainView mainView;

    public NoConnectionToDB(MainView mainView) {
        this.mainView = mainView;
    }

    public void executeException() {
        ShowNotification noConnectionToDB = new ShowNotification("No connection to the database!", 5000);
        noConnectionToDB.show();
        mainView.btnCustomers.setEnabled(false);
        mainView.btnProducts.setEnabled(false);
        mainView.btnInvoices.setEnabled(false);
        mainView.btnUser.setEnabled(false);
    }
}
