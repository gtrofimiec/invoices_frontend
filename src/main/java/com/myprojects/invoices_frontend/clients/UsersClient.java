package com.myprojects.invoices_frontend.clients;

import com.myprojects.invoices_frontend.config.UsersConfig;
import com.myprojects.invoices_frontend.domain.Users;
import com.myprojects.invoices_frontend.domain.dtos.UsersDto;
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
public class UsersClient {

    private static final Logger LOGGER = LoggerFactory.getLogger(UsersClient.class);

    private final RestTemplate restTemplate;
    private final UsersConfig usersConfig;

    public UsersClient(RestTemplate restTemplate, UsersConfig usersConfig) {
        this.restTemplate = restTemplate;
        this.usersConfig = usersConfig;
    }

    public List<UsersDto> getUsers() {
        URI url = UriComponentsBuilder.fromHttpUrl(usersConfig.getUserEndpoint())
                .build()
                .encode()
                .toUri();
        try {
            UsersDto[] usersDtoList = restTemplate.getForObject(url, UsersDto[].class);
            if(usersDtoList.length != 0) {
                LOGGER.info("Users database was sucessfully loaded");
                return Arrays.stream(usersDtoList)
                        .collect(Collectors.toList());
            } else {
                LOGGER.warn("Users database could not be retrieved or it is empty");
                return new ArrayList<>();
            }

        } catch (RestClientException e) {
            LOGGER.error(e.getMessage(), e);
            return new ArrayList<>();
        }
    }

    public void saveUser(UsersDto userDto) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        URI url = UriComponentsBuilder.fromHttpUrl(usersConfig.getUserEndpoint())
                .build()
                .encode()
                .toUri();
        try {
            HttpEntity<UsersDto> request = new HttpEntity<>(userDto, headers);
            Users sentUser = restTemplate.postForObject(url, request, Users.class);
            if(sentUser != null) {
                LOGGER.info("User " + sentUser.getFullName() + " has been correctly sent");
            }
        } catch (RestClientException e) {
            LOGGER.error(e.getMessage(), e);
        }
    }

    public void updateUser(@NotNull UsersDto userDto) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        URI url = UriComponentsBuilder.fromHttpUrl(usersConfig.getUserEndpoint())
                .path("/" + userDto.getId())
                .build()
                .encode()
                .toUri();
        try {
            HttpEntity<UsersDto> request = new HttpEntity<>(userDto, headers);
            restTemplate.exchange(url, HttpMethod.PUT, request, UsersDto.class);
            LOGGER.info("User " + userDto.getFullName() + " has been updated");
        } catch (RestClientException e) {
            LOGGER.error(e.getMessage(), e);
        }
    }

    public void deleteUser(@NotNull UsersDto userDto) {
        URI url = UriComponentsBuilder.fromHttpUrl(usersConfig.getUserEndpoint())
                .path("/" + userDto.getId())
                .build()
                .encode()
                .toUri();
        try {
            restTemplate.delete(url);
            LOGGER.info("User " + userDto.getFullName() + "has been deleted");
        } catch (RestClientException e) {
            LOGGER.error(e.getMessage(), e);
        }
    }
}