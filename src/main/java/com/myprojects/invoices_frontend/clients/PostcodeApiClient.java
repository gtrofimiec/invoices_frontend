package com.myprojects.invoices_frontend.clients;

import com.myprojects.invoices_frontend.config.PostcodeApiConfig;
import com.myprojects.invoices_frontend.domain.dtos.PostcodeApiDto;
import com.myprojects.invoices_frontend.exceptions.TownFromPostcodeNotFoundException;
import com.myprojects.invoices_frontend.layout.dialogboxes.ShowNotification;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

@Component
public class PostcodeApiClient {

    private static final Logger LOGGER = LoggerFactory.getLogger(PostcodeApiClient.class);

    private final RestTemplate restTemplate;
    private final PostcodeApiConfig postcodeApiConfig;

    public PostcodeApiClient(RestTemplate restTemplate, PostcodeApiConfig postcodeApiConfig) {
        this.restTemplate = restTemplate;
        this.postcodeApiConfig = postcodeApiConfig;
    }

    public PostcodeApiDto getTown(String postcode) throws TownFromPostcodeNotFoundException {
        URI url = UriComponentsBuilder.fromHttpUrl(postcodeApiConfig.getPostcodeApiEndpoint())
                .path(postcode)
                .build()
                .encode()
                .toUri();

        try {
            PostcodeApiDto townResponse = restTemplate.getForObject(url, PostcodeApiDto.class);
            if(townResponse.getTown() != null) {
                LOGGER.info("Town name from postcode database was succesfully get");
            }
            return new PostcodeApiDto(townResponse.getTown());
        } catch (ResponseStatusException | ResourceAccessException | NullPointerException e) {
            ShowNotification notFoundNotification = new ShowNotification("Nie znaleziono w bazie kodów " +
                    "pocztowych. Sprawdź wprowadzony kod i spróbuj jeszcze raz",
                    5000);
            notFoundNotification.show();
            LOGGER.error(e.getMessage());
            return null;
        }
    }
}