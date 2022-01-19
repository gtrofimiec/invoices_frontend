package com.myprojects.invoices_frontend.domain.dtos;

import com.fasterxml.jackson.annotation.*;

import javax.annotation.Generated;
import java.util.HashMap;
import java.util.Map;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "ulica",
        "budynek",
        "miasto",
        "kod"
})
public class AddressDto {

    @JsonProperty("ulica")
    private String ulica;
    @JsonProperty("budynek")
    private String budynek;
    @JsonProperty("miasto")
    private String miasto;
    @JsonProperty("kod")
    private String kod;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    @JsonProperty("ulica")
    public String getUlica() {
        return ulica;
    }

    @JsonProperty("budynek")
    public String getBudynek() {
        return budynek;
    }

    @JsonProperty("miasto")
    public String getMiasto() {
        return miasto;
    }

    @JsonProperty("kod")
    public String getKod() {
        return kod;
    }

    @JsonAnyGetter
    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }
}