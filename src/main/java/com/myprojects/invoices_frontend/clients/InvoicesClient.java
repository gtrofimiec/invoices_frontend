package com.myprojects.invoices_frontend.clients;

import com.myprojects.invoices_frontend.config.InvoicesConfig;
import com.myprojects.invoices_frontend.domain.Invoices;
import com.myprojects.invoices_frontend.domain.dtos.InvoicesDto;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class InvoicesClient {

    private static final Logger LOGGER = LoggerFactory.getLogger(InvoicesClient.class);

    private final RestTemplate restTemplate;
    private final InvoicesConfig invoicesConfig;

    public InvoicesClient(RestTemplate restTemplate, InvoicesConfig invoicesConfig) {
        this.restTemplate = restTemplate;
        this.invoicesConfig = invoicesConfig;
    }

    public List<InvoicesDto> getInvoices() {
        URI url = UriComponentsBuilder.fromHttpUrl(invoicesConfig.getInvoicesEndpoint())
                .build()
                .encode()
                .toUri();
        try {
            InvoicesDto[] invoicesDtoList = restTemplate.getForObject(url, InvoicesDto[].class);
            if(invoicesDtoList.length != 0) {
                LOGGER.info("Invoices database was sucessfully loaded");
                return Arrays.stream(invoicesDtoList)
                        .collect(Collectors.toList());
            } else {
                LOGGER.warn("Invoices database could not be retrieved or it is empty");
                return new ArrayList<>();
            }

        } catch (RestClientException e) {
            LOGGER.error(e.getMessage(), e);
            return new ArrayList<>();
        }
    }

    public void saveInvoice(InvoicesDto invoiceDto) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        URI url = UriComponentsBuilder.fromHttpUrl(invoicesConfig.getInvoicesEndpoint())
                .build()
                .encode()
                .toUri();
        try {
            HttpEntity<InvoicesDto> request = new HttpEntity<>(invoiceDto, headers);
            Invoices sentInvoice = restTemplate.postForObject(url, request, Invoices.class);
            if(sentInvoice != null) {
                LOGGER.info("Invoice number " + sentInvoice.getNumber() + " has been correctly sent");
            }
        } catch (RestClientException e) {
            LOGGER.error(e.getMessage(), e);
        }
    }

    public void updateInvoice(@NotNull InvoicesDto invoicesDto) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        URI url = UriComponentsBuilder.fromHttpUrl(invoicesConfig.getInvoicesEndpoint())
                .path("/" + invoicesDto.getId())
                .build()
                .encode()
                .toUri();
        try {
            HttpEntity<InvoicesDto> request = new HttpEntity<>(invoicesDto, headers);
            restTemplate.exchange(url, HttpMethod.PUT, request, InvoicesDto.class);
            LOGGER.info("Invoice number " + invoicesDto.getNumber() + " has been updated");
        } catch (RestClientException e) {
            LOGGER.error(e.getMessage(), e);
        }
    }

    public void deleteInvoice(@NotNull InvoicesDto invoicesDto) {
        URI url = UriComponentsBuilder.fromHttpUrl(invoicesConfig.getInvoicesEndpoint())
                .path("/" + invoicesDto.getId())
                .build()
                .encode()
                .toUri();
        try {
            restTemplate.delete(url);
            LOGGER.info("Invoice number " + invoicesDto.getNumber() + "has been deleted");
        } catch (RestClientException e) {
            LOGGER.error(e.getMessage(), e);
        }
    }
}