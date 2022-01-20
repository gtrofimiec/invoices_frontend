package com.myprojects.invoices_frontend.mappers;

import com.myprojects.invoices_frontend.domain.Customers;
import com.myprojects.invoices_frontend.domain.dtos.CustomersDto;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CustomersMapper {

    public Customers mapToCustomer(final @NotNull CustomersDto customerDto) { // throws CustomerNotFoundException {
        Customers customer = new Customers();
        customer.setId(customerDto.getId());
        customer.setFullName(customerDto.getFullName());
        customer.setNip(customerDto.getNip());
        customer.setStreet(customerDto.getStreet());
        customer.setPostcode(customerDto.getPostcode());
        customer.setTown(customerDto.getTown());
        return customer;
    }

    public CustomersDto mapToCustomerDto(final @NotNull Customers customer) { // throws CustomerNotFoundException {
        CustomersDto customerDto = new CustomersDto();
        customerDto.setId(customer.getId());
        customerDto.setFullName(customer.getFullName());
        customerDto.setNip(customer.getNip());
        customerDto.setStreet(customer.getStreet());
        customerDto.setPostcode(customer.getPostcode());
        customerDto.setTown(customer.getTown());
        return customerDto;
    }

    public List<CustomersDto> mapToCustomersDtoList(final @NotNull List<Customers> customersList) {
        return customersList.stream()
                .map(this::mapToCustomerDto)
                .collect(Collectors.toList());
    }

    public List<Customers> mapToCustomersList(final @NotNull List<CustomersDto> customersDtoList) {
        return customersDtoList.stream()
                .map(this::mapToCustomer)
                .collect(Collectors.toList());
    }

}