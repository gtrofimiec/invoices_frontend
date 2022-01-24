package com.myprojects.invoices_frontend.mappers;

import com.myprojects.invoices_frontend.domain.Customers;
import com.myprojects.invoices_frontend.domain.dtos.CustomersDto;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.stream.Collectors;

public class CustomersMapper {

    public Customers mapToCustomer(final @NotNull CustomersDto customerDto) {
        return new Customers(
                customerDto.getId(),
                customerDto.getFullName(),
                customerDto.getNip(),
                customerDto.getStreet(),
                customerDto.getPostcode(),
                customerDto.getTown()
        );
    }

    public CustomersDto mapToCustomerDto(final @NotNull Customers customer) {
        return new CustomersDto(
                customer.getId(),
                customer.getFullName(),
                customer.getNip(),
                customer.getStreet(),
                customer.getPostcode(),
                customer.getTown()
        );
    }

    public List<Customers> mapToCustomersList(final @NotNull List<CustomersDto> customersDtoList) {
        return customersDtoList.stream()
                .map(this::mapToCustomer)
                .collect(Collectors.toList());
    }
}