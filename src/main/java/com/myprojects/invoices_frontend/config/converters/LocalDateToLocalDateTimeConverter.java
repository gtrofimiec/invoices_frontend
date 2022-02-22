package com.myprojects.invoices_frontend.config.converters;

import com.vaadin.flow.data.binder.Result;
import com.vaadin.flow.data.binder.ValueContext;
import com.vaadin.flow.data.converter.Converter;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class LocalDateToLocalDateTimeConverter implements Converter<LocalDate, LocalDateTime> {

   @Override
    public Result<LocalDateTime> convertToModel(LocalDate value, ValueContext context) {
        if(value == null) {
            return null;
        } else {
            return Result.ok(value.atStartOfDay());
        }
    }

    @Override
    public LocalDate convertToPresentation(LocalDateTime value, ValueContext context) {
        if(value == null) {
            return null;
        } else {
            return value.toLocalDate();
        }
    }
}