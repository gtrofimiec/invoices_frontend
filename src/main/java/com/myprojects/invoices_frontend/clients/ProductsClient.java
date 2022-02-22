package com.myprojects.invoices_frontend.clients;

import com.myprojects.invoices_frontend.config.ProductsConfig;
import com.myprojects.invoices_frontend.domain.Products;
import com.myprojects.invoices_frontend.domain.dtos.ProductsDto;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class ProductsClient {

    private static final Logger LOGGER = LoggerFactory.getLogger(ProductsClient.class);

    private final RestTemplate restTemplate;
    private final ProductsConfig productsConfig;

    public ProductsClient(RestTemplate restTemplate, ProductsConfig productsConfig) {
        this.restTemplate = restTemplate;
        this.productsConfig = productsConfig;
    }

    public List<ProductsDto> getProducts() {
        URI url = UriComponentsBuilder.fromHttpUrl(productsConfig.getProductsEndpoint())
                .build()
                .encode()
                .toUri();
        try {
            ProductsDto[] productsDtoList = restTemplate.getForObject(url, ProductsDto[].class);
            if(productsDtoList != null) {
                LOGGER.info("Products database was sucessfully loaded");
                return Arrays.stream(productsDtoList)
                        .collect(Collectors.toList());
            } else {
                LOGGER.warn("Products database could not be retrieved or it is empty");
                return new ArrayList<>();
            }

        } catch (RestClientException e) {
            if(e.contains(ResourceAccessException.class)) {
                LOGGER.error("No connection to database");
            }
            LOGGER.error(e.getMessage(), e);
            return new ArrayList<>();
        }
    }

    public void saveProduct(ProductsDto productsDto) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        URI url = UriComponentsBuilder.fromHttpUrl(productsConfig.getProductsEndpoint())
                .build()
                .encode()
                .toUri();
        try {
            HttpEntity<ProductsDto> request = new HttpEntity<>(productsDto, headers);
            Products sentProduct = restTemplate.postForObject(url, request, Products.class);
            if(sentProduct != null) {
                LOGGER.info("Product " + sentProduct.getName() + " has been correctly sent");
            }
        } catch (RestClientException e) {
            if(e.contains(ResourceAccessException.class)) {
                LOGGER.error("No connection to database");
            }
            LOGGER.error(e.getMessage(), e);
        }
    }

    public void updateProduct(@NotNull ProductsDto productsDto) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        URI url = UriComponentsBuilder.fromHttpUrl(productsConfig.getProductsEndpoint())
                .path("/" + productsDto.getId())
                .build()
                .encode()
                .toUri();
        try {
            HttpEntity<ProductsDto> request = new HttpEntity<>(productsDto, headers);
            restTemplate.exchange(url, HttpMethod.PUT, request, ProductsDto.class);
            LOGGER.info("Product " + productsDto.getName() + " has been updated");
        } catch (RestClientException e) {
            if(e.contains(ResourceAccessException.class)) {
                LOGGER.error("No connection to database");
            }
            LOGGER.error(e.getMessage(), e);
        }
    }

    public void deleteProduct(@NotNull ProductsDto productsDto) {
        URI url = UriComponentsBuilder.fromHttpUrl(productsConfig.getProductsEndpoint())
                .path("/" + productsDto.getId())
                .build()
                .encode()
                .toUri();
        try {
            restTemplate.delete(url);
            LOGGER.info("Product " + productsDto.getName() + " has been deleted");
        } catch (RestClientException e) {
            if(e.contains(ResourceAccessException.class)) {
                LOGGER.error("No connection to database");
            }
            LOGGER.error(e.getMessage(), e);
        }
    }
}