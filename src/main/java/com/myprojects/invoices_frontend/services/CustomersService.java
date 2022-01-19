package com.myprojects.invoices_frontend.services;

import com.myprojects.invoices_frontend.domain.Customers;
import com.vaadin.flow.data.binder.Binder;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@AllArgsConstructor
@Service
public class CustomersService {

    private Set<Customers> customers;
    private static CustomersService customersService;
    private Binder<Customers> binder = new Binder<>(Customers.class);

    public CustomersService() {
        this.customers = getCustomers();
    }

    public static CustomersService getInstance() {
        if (customersService == null) {
            customersService = new CustomersService();
        }
        return customersService;
    }

    public Set<Customers> findByFullName(String fullName) {
        return customers.stream()
                .filter(c -> c.getFullName().contains(fullName))
                .collect(Collectors.toSet());
    }

    public Set<Customers> getCustomers() {
        Set<Customers> customers = new HashSet<>();
        customers.add(new Customers(1L, "Jan Kowalski", "5630016732", "Wesoła",
                "25-847", "Miasteczko"));
        return customers;
    }

    public void save(Customers customer) {
        this.customers.add(customer);
    }

    public void delete(Customers customer) {
        this.customers.remove(customer);
    }
}