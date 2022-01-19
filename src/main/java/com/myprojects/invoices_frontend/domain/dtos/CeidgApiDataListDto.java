package com.myprojects.invoices_frontend.domain.dtos;

import com.fasterxml.jackson.annotation.*;

import javax.annotation.Generated;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "firma"
})
@Generated("jsonschema2pojo")
public class CeidgApiDataListDto {

    @JsonProperty("firma")
    private List<CustomerDataDto> customerDataDto = null;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    @JsonProperty("firma")
    public List<CustomerDataDto> getFirma() {
        return customerDataDto;
    }

    @JsonAnyGetter
    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }
}