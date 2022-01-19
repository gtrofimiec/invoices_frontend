package com.myprojects.invoices_frontend.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class TownFromPostcodeNotFoundException extends ResponseStatusException {

    public TownFromPostcodeNotFoundException() {
        super(HttpStatus.NOT_FOUND, "Data for this postcode not found");
    }
}