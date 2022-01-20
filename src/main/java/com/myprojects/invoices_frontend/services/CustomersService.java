package com.myprojects.invoices_frontend.services;

import com.myprojects.invoices_frontend.clients.CustomersClient;
import com.myprojects.invoices_frontend.domain.Customers;
import com.myprojects.invoices_frontend.domain.dtos.CustomersDto;
import com.myprojects.invoices_frontend.mappers.CustomersMapper;
import com.vaadin.flow.data.binder.Binder;
import lombok.AllArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@AllArgsConstructor
@Service
public class CustomersService {

    private List<Customers> customers;
    private static CustomersClient customersClient;
    private static CustomersService customersService;
    private static CustomersMapper customersMapper = new CustomersMapper();
    private Binder<Customers> binder = new Binder<>(Customers.class);

    public CustomersService(CustomersClient customersClient) {
        CustomersService.customersClient = customersClient;
    }

    public static CustomersService getInstance() {
        if (customersService == null) {
            customersService = new CustomersService(customersClient);
        }
        return customersService;
    }

    public Set<Customers> findByFullName(String fullName) {
        return customers.stream()
                .filter(c -> c.getFullName().contains(fullName))
                .collect(Collectors.toSet());
    }

    public List<Customers> getCustomers() {
        return customersMapper.mapToCustomersList(customersClient.getCustomers());
    }

    public void saveCustomer(Customers customer) {
        customersClient.saveCustomer(customersMapper.mapToCustomerDto(customer));
    }

    public void updateCustomer(Customers customer) {
        customersClient.updateCustomer(customersMapper.mapToCustomerDto(customer));
    }

    public void deleteCustomer(@NotNull Customers customer) {
        customersClient.deleteCustomer(customersMapper.mapToCustomerDto(customer));
    }
}