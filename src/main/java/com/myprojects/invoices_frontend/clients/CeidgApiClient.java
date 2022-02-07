package com.myprojects.invoices_frontend.clients;

import com.myprojects.invoices_frontend.config.CeidgApiConfig;
import com.myprojects.invoices_frontend.domain.dtos.CeidgApiDto;
import com.myprojects.invoices_frontend.layout.dialogboxes.ShowNotification;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

@Component
public class CeidgApiClient {

    private static final Logger LOGGER = LoggerFactory.getLogger(CeidgApiClient.class);

    private final RestTemplate restTemplate;
    private final CeidgApiConfig ceidgApiConfig;

    public CeidgApiClient(RestTemplate restTemplate, CeidgApiConfig ceidgApiConfig) {
        this.restTemplate = restTemplate;
        this.ceidgApiConfig = ceidgApiConfig;
    }

    public CeidgApiDto getCeidgData(String nip) {

        URI url = UriComponentsBuilder.fromHttpUrl(ceidgApiConfig.getCeidgApiEndpoint())
                .path(nip)
                .build()
                .encode()
                .toUri();

        try {
            CeidgApiDto resEntity = restTemplate.getForObject(url, CeidgApiDto.class);
            CeidgApiDto newCeidgApiDto = new CeidgApiDto(resEntity.getFullName(), nip,
                    resEntity.getStreet(),
                    resEntity.getBuilding(),
                    resEntity.getPostCode(),
                    resEntity.getTown());
            if(resEntity != null) {
                LOGGER.info("Customer data from CEIDG database was succesfully get");
            }
            return newCeidgApiDto;
        } catch (RestClientException | NullPointerException e) {
            ShowNotification notFoundNotification = new ShowNotification("Nie znaleziono w bazie CEIDG",
                    5000);
            notFoundNotification.show();
            LOGGER.error(e.getMessage(), e);
            return null;
        }
    }
}