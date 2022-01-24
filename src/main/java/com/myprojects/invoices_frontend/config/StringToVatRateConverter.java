//package com.myprojects.invoices_frontend.config;
//
//import com.myprojects.invoices_frontend.domain.VatRate;
//import com.vaadin.flow.data.binder.Result;
//import com.vaadin.flow.data.binder.ValueContext;
//import com.vaadin.flow.data.converter.Converter;
//
//import java.util.Arrays;
//import java.util.stream.Collectors;
//
//public class StringToVatRateConverter implements Converter<VatRate, Integer> {
//
//    @Override
//    public Result<Integer> convertToModel(VatRate value, ValueContext context) {
//        if (value == null) {
//            return null;
//        } else {
//            return Result.ok(value.getValue());
//        }
//    }
//
//    @Override
//    public VatRate convertToPresentation(Integer value, ValueContext context) {
//        if (value == null) {
//            return null;
//        }
//        int val = value;
//        return Arrays.stream(VatRate.values())
//                .filter(v -> v.getValue() == val)
//                .collect(Collectors.toList())
//                .get(0);
//    }
//}