package com.myprojects.invoices_frontend.services;

import com.myprojects.invoices_frontend.domain.Products;
import com.vaadin.flow.data.binder.Binder;
import org.jetbrains.annotations.NotNull;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

public class ProductsService {

    private Set<Products> products;
    private static ProductsService productsService;
    private Binder<Products> binder = new Binder<>(Products.class);

    public ProductsService() {
        this.products = exampleData();
    }

    public static ProductsService getInstance() {
        if (productsService == null) {
            productsService = new ProductsService();
        }
        return productsService;
    }

    public Set<Products> getProducts() {
        return new HashSet<>(products);
    }

    public Set<Products> findByName(String name) {
        return products.stream()
                .filter(p -> p.getName().contains(name))
                .collect(Collectors.toSet());
    }

    private @NotNull Set<Products> exampleData() {
        Set<Products> products = new HashSet<>();
        products.add(new Products(1L, "młotek", 23, new BigDecimal("20.00"),
                new BigDecimal("4.60"), new BigDecimal("24.60")));
        return products;
    }

    public void save(Products product) {
        this.products.add(product);
    }

    public void delete(Products product) {
        this.products.remove(product);
    }
}
