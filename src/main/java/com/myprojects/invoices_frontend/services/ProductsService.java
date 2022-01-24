package com.myprojects.invoices_frontend.services;

import com.myprojects.invoices_frontend.clients.ProductsClient;
import com.myprojects.invoices_frontend.domain.Products;
import com.myprojects.invoices_frontend.mappers.ProductsMapper;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class ProductsService {

    private List<Products> productsList;
    private static ProductsClient productsClient;
    private static ProductsService productsService;
    private static ProductsMapper productsMapper = new ProductsMapper();

    public ProductsService(ProductsClient productsClient) {
        ProductsService.productsClient = productsClient;
    }

    public static ProductsService getInstance() {
        if (productsService == null) {
            productsService = new ProductsService(productsClient);
        }
        return productsService;
    }

    public Set<Products> findByName(String name) {
        return productsList.stream()
                .filter(p -> p.getName().contains(name))
                .collect(Collectors.toSet());
    }

    public List<Products> getProductsList() {
        productsList = productsMapper.mapToProductsList(productsClient.getProducts());
        return productsList;
    }

    public void saveProduct(Products product) {
        productsClient.saveProduct(productsMapper.mapToProductDto(product));
    }

    public void updateProduct(Products product) {
        productsClient.updateProduct(productsMapper.mapToProductDto(product));
    }

    public void deleteProduct(@NotNull Products product) {
        productsClient.deleteProduct(productsMapper.mapToProductDto(product));
    }
}