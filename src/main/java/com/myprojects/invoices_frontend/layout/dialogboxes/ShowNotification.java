package com.myprojects.invoices_frontend.layout.dialogboxes;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.notification.Notification.Position;
import org.jetbrains.annotations.NotNull;

public class ShowNotification extends Div {

    String message;
    int duration;

    public ShowNotification(String message, int duration) {
        this.message = message;
        this.duration = duration;
        add(createButton());

    }

    private @NotNull Button createButton() {
        Button button = new Button();
        button.addClickListener(event -> show());
        return button;
    }

    public void show() {
        Notification.show(message, duration, Position.MIDDLE);
    }
}