package com.myprojects.invoices_frontend.config.converters;

import com.myprojects.invoices_frontend.domain.Customers;
import com.myprojects.invoices_frontend.services.CustomersService;
import com.vaadin.flow.data.binder.Result;
import com.vaadin.flow.data.binder.ValueContext;
import com.vaadin.flow.data.converter.Converter;

import java.util.List;

public class StringToCustomerConverter implements Converter<String, Customers> {

    @Override
    public Result<Customers> convertToModel(String value, ValueContext context) {
        CustomersService customersService = CustomersService.getInstance();
        if (value == null) {
            return null;
        }
        List<Customers> customersList = customersService.findByFullName(value);
        if (customersList.size() > 0) {
            return Result.ok(customersList.get(0));
        } else {
            return null;
        }
    }

    @Override
    public String convertToPresentation(Customers customer, ValueContext context) {
        if (customer == null) {
            return null;
        } else {
            return customer.getFullName();
        }
    }
}