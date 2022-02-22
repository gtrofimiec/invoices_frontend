package com.myprojects.invoices_frontend.mappers;

import com.myprojects.invoices_frontend.domain.Invoices;
import com.myprojects.invoices_frontend.domain.dtos.InvoicesDto;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.stream.Collectors;

public class InvoicesMapper {

    public Invoices mapToInvoice(@NotNull InvoicesDto invoiceDto) {
        CustomersMapper customersMapper = new CustomersMapper();
        ProductsMapper productsMapper = new ProductsMapper();
        UsersMapper usersMapper = new UsersMapper();
        return new Invoices(
                invoiceDto.getId(),
                invoiceDto.getNumber(),
                invoiceDto.getDate(),
                invoiceDto.getNetSum(),
                invoiceDto.getVatSum(),
                invoiceDto.getGrossSum(),
                invoiceDto.getPaymentMethod(),
                invoiceDto.getPaymentDate(),
                customersMapper.mapToCustomer(invoiceDto.getCustomerDto()),
                usersMapper.mapToUser(invoiceDto.getUserDto()),
                productsMapper.mapToProductsList(invoiceDto.getProductsDtoList())
        );
    }

    public InvoicesDto mapToInvoiceDto(@NotNull Invoices invoice) {
        CustomersMapper customersMapper = new CustomersMapper();
        ProductsMapper productsMapper = new ProductsMapper();
        UsersMapper usersMapper = new UsersMapper();
        return new InvoicesDto(
                invoice.getId(),
                invoice.getNumber(),
                invoice.getDate(),
                invoice.getNetSum(),
                invoice.getVatSum(),
                invoice.getGrossSum(),
                invoice.getPaymentMethod(),
                invoice.getPaymentDate(),
                customersMapper.mapToCustomerDto(invoice.getCustomer()),
                usersMapper.mapToUserDto(invoice.getUser()),
                productsMapper.mapToProductsDtoList(invoice.getProductsList())
        );
    }

    public List<Invoices> mapToInvoicesList(@NotNull List<InvoicesDto> invoicesDtoList) {
        return invoicesDtoList.stream()
                .map(this::mapToInvoice)
                .collect(Collectors.toList());
    }

    public List<InvoicesDto> mapToInvoicesDtoList(@NotNull List<Invoices> invoicesList) {
        return invoicesList.stream()
                .map(this::mapToInvoiceDto)
                .collect(Collectors.toList());
    }
}