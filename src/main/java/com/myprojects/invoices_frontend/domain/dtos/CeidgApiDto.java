package com.myprojects.invoices_frontend.domain.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonPropertyOrder({
        "fullName",
        "street",
        "building",
        "postCode",
        "town"
})
public class CeidgApiDto {

    @JsonProperty("fullName")
    private String fullName;
    @JsonProperty("street")
    private String street;
    @JsonProperty("building")
    private String building;
    @JsonProperty("postCode")
    private String postCode;
    @JsonProperty("town")
    private String town;

    public CeidgApiDto() {
    }

    public CeidgApiDto(String fullName, String street, String building, String postCode, String town) {
        this.fullName = fullName;
        this.street = street;
        this.building = building;
        this.postCode = postCode;
        this.town = town;
    }

    public String getFullName() {
        return fullName;
    }

    public String getStreet() {
        return street;
    }

    public String getBuilding() {
        return building;
    }

    public String getPostCode() {
        return postCode;
    }

    public String getTown() {
        return town;
    }
}