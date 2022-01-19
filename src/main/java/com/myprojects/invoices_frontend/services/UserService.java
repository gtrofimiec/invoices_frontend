package com.myprojects.invoices_frontend.services;

import com.myprojects.invoices_frontend.domain.User;
import com.vaadin.flow.data.binder.Binder;

public class UserService {

    private User user;
    private static UserService userService;
    private Binder<User> binder = new Binder<>(User.class);

    public UserService() {
        this.user = exampleData();
    }

    public static UserService getInstance() {
        if (userService == null) {
            userService = new UserService();
        }
        return userService;
    }

    public User getUser() {
        return user;
    }

    private User exampleData() {
        return new User(1L, "Marian", "5630016732", "Jakaś",
                "20-022", "Większe");
    }

    public void save(User user) {
        this.user = user;
    }

    public void delete(User user) {
        this.user = null;
    }
}