package com.myprojects.invoices_frontend.domain.dtos;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class PostcodeApiDto {

    @JsonProperty("miejscowosc")
    private String town;
    @JsonProperty("wojewodztwo")
    private String voivodeship;


    public PostcodeApiDto() {
    }

    public PostcodeApiDto(String town) {
        this.town = town;
    }

    public String getTown() {
        return town;
    }
}