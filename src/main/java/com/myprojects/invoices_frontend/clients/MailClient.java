package com.myprojects.invoices_frontend.clients;

import com.myprojects.invoices_frontend.config.MailConfig;
import com.myprojects.invoices_frontend.domain.dtos.MailDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

@Component
public class MailClient {

    private static final Logger LOGGER = LoggerFactory.getLogger(MailClient.class);

    private final RestTemplate restTemplate;
    private final MailConfig mailConfig;

    public MailClient(RestTemplate restTemplate, MailConfig mailConfig) {
        this.restTemplate = restTemplate;
        this.mailConfig = mailConfig;
    }

    public boolean sendMail(MailDto mailDto) {
        URI url = UriComponentsBuilder.fromHttpUrl(mailConfig.getMailEndpoint())
                .build()
                .encode()
                .toUri();

        try {
            HttpEntity<MailDto> request = new HttpEntity<>(mailDto);
            ResponseEntity<Boolean> sentMail = restTemplate.exchange(url, HttpMethod.POST, request, Boolean.class);
            if(Boolean.TRUE.equals(sentMail.getBody())) {
                LOGGER.info("E-mail to " + mailDto.getMailTo() + " has been sent");
            }
            return true;
        } catch (RestClientException e) {
            if(e.contains(ResourceAccessException.class)) {
                LOGGER.error("E-mail sending error received!");
            }
            LOGGER.error(e.getMessage(), e);
            return false;
        }
    }
}
