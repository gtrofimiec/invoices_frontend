package com.myprojects.invoices_frontend.mappers;

import com.myprojects.invoices_frontend.domain.Products;
import com.myprojects.invoices_frontend.domain.dtos.ProductsDto;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProductsMapper {

    public Products mapToProduct(@NotNull ProductsDto productDto) {
        InvoicesMapper invoicesMapper = new InvoicesMapper();
        return new Products(
                productDto.getId(),
                productDto.getName(),
                productDto.getVatRate(),
                productDto.getNetPrice(),
                productDto.getVatValue(),
                productDto.getGrossPrice(),
                invoicesMapper.mapToInvoicesList(productDto.getInvoicesDtoList())
        );
    }

    public ProductsDto mapToProductDto(@NotNull Products product) {
        InvoicesMapper invoicesMapper = new InvoicesMapper();
        return new ProductsDto(
                product.getId(),
                product.getName(),
                product.getVatRate(),
                product.getNetPrice(),
                product.getVatValue(),
                product.getGrossPrice(),
                invoicesMapper.mapToInvoicesDtoList(product.getInvoicesList())
        );
    }

    public List<Products> mapToProductsList(final @NotNull List<ProductsDto> productsDtoList) {
        return productsDtoList.stream()
                .map(this::mapToProduct)
                .collect(Collectors.toList());
    }

    public List<ProductsDto> mapToProductsDtoList(final @NotNull List<Products> productsList) {
        return productsList.stream()
                .map(this::mapToProductDto)
                .collect(Collectors.toList());
    }
}