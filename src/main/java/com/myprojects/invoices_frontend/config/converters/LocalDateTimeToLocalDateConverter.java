package com.myprojects.invoices_frontend.config.converters;

import com.vaadin.flow.data.binder.Result;
import com.vaadin.flow.data.binder.ValueContext;
import com.vaadin.flow.data.converter.Converter;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class LocalDateTimeToLocalDateConverter implements Converter<LocalDateTime, LocalDate> {


    @Override
    public Result<LocalDate> convertToModel(LocalDateTime value, ValueContext context) {
        if(value == null) {
            return null;
        } else {
            return Result.ok(value.toLocalDate());
        }
    }

    @Override
    public LocalDateTime convertToPresentation(LocalDate value, ValueContext context) {
        if(value == null) {
            return null;
        } else {
            return value.atStartOfDay();
        }
    }
}