package com.myprojects.invoices_frontend.services;

import com.myprojects.invoices_frontend.clients.CustomersClient;
import com.myprojects.invoices_frontend.domain.Customers;
import com.myprojects.invoices_frontend.layout.dialogboxes.ShowNotification;
import com.myprojects.invoices_frontend.mappers.CustomersMapper;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class CustomersService {

    private List<Customers> customersList;
    private static CustomersClient customersClient;
    private static CustomersService customersService;
    private static InvoicesService invoicesService = InvoicesService.getInstance();
    private static CustomersMapper customersMapper = new CustomersMapper();

    public CustomersService(CustomersClient customersClient) {
        CustomersService.customersClient = customersClient;
    }

    public static CustomersService getInstance() {
        if (customersService == null) {
            customersService = new CustomersService(customersClient);
        }
        return customersService;
    }

    public List<Customers> findByFullName(String fullName) {
        return customersList.stream()
                .filter(c -> c.getFullName().contains(fullName))
                .collect(Collectors.toList());
    }

    public List<Customers> getCustomersList() {
        customersList = customersMapper.mapToCustomersList(customersClient.getCustomers());
        return customersList;
    }

    public void saveCustomer(Customers customer) {
        customersClient.saveCustomer(customersMapper.mapToCustomerDto(customer));
    }

    public void updateCustomer(Customers customer) {
        customersClient.updateCustomer(customersMapper.mapToCustomerDto(customer));
    }

    public void deleteCustomer(@NotNull Customers customer) {
        if(invoicesService.getInvoicesList().stream()
                .noneMatch(i -> Objects.equals(i.getCustomer().getId(), customer.getId()))) {
            customersClient.deleteCustomer(customersMapper.mapToCustomerDto(customer));
        } else {
            ShowNotification cantBeDeleted = new ShowNotification("Nie można usunąć kontrahenta "
            + customer.getFullName() + ". Jest z nim powiązana wystawiona faktura!", 5000);
            cantBeDeleted.show();
        }
    }
}