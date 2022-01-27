package com.myprojects.invoices_frontend.config.converters;

import com.myprojects.invoices_frontend.domain.Users;
import com.myprojects.invoices_frontend.domain.VatRate;
import com.myprojects.invoices_frontend.services.UsersService;
import com.vaadin.flow.data.binder.Result;
import com.vaadin.flow.data.binder.ValueContext;
import com.vaadin.flow.data.converter.Converter;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class StringToUserConverter implements Converter<String, Users> {

//    @Override
//    public Result<String> convertToModel(Users user, ValueContext context) {
//        if (user == null) {
//            return null;
//        } else {
//            return Result.ok(user.getFullName());
//        }
//    }
//
//    @Override
//    public Users convertToPresentation(String value, ValueContext context) {
//        UsersService usersService = UsersService.getInstance();
//        if (value == null) {
//            return null;
//        }
//        List<Users> usersList = usersService.findByFullName(value);
//        if (usersList.size() > 0) {
//            return usersList.get(0);
//        } else {
//            return null;
//        }
//    }

    @Override
    public Result<Users> convertToModel(String value, ValueContext context) {
        UsersService usersService = UsersService.getInstance();
        if (value == null) {
            return null;
        }
        List<Users> usersList = usersService.findByFullName(value);
        if (usersList.size() > 0) {
            return Result.ok(usersList.get(0));
        } else {
            return null;
        }
    }

    @Override
    public String convertToPresentation(Users user, ValueContext context) {
        if (user == null) {
            return null;
        } else {
            return user.getFullName();
        }
    }
}
