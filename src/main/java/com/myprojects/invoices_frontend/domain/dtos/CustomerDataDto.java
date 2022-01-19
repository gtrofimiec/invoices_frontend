package com.myprojects.invoices_frontend.domain.dtos;

import com.fasterxml.jackson.annotation.*;

import java.util.HashMap;
import java.util.Map;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "nazwa",
        "adresDzialanosci"
})
public class CustomerDataDto {

    @JsonProperty("nazwa")
    private String nazwa;
    @JsonProperty("adresDzialanosci")
    private AddressDto addressDto;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    @JsonProperty("nazwa")
    public String getNazwa() {
        return nazwa;
    }

    @JsonProperty("nazwa")
    public void setNazwa(String nazwa) {
        this.nazwa = nazwa;
    }

    @JsonProperty("adresDzialanosci")
    public AddressDto getAdresDzialanosci() {
        return addressDto;
    }

    @JsonAnyGetter
    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }
}