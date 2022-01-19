package com.myprojects.invoices_frontend.apis.postcodeapi;

import com.myprojects.invoices_frontend.domain.dtos.PostcodeApiDto;
import com.myprojects.invoices_frontend.exceptions.TownFromPostcodeNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class PostcodeApiService {

    private static PostcodeApiService postcodeApiService;
    private static PostcodeApiClient postcodeApiClient;

    public PostcodeApiService(PostcodeApiClient postcodeApiClient) {
        PostcodeApiService.postcodeApiClient = postcodeApiClient;
    }

    public static PostcodeApiService getInstance() {
        if (postcodeApiService == null) {
            postcodeApiService = new PostcodeApiService(postcodeApiClient);
        }
        return postcodeApiService;
    }

    public PostcodeApiDto getTownFromPostcode(String postcode) throws TownFromPostcodeNotFoundException {
        return postcodeApiClient.getTown(postcode);
    }
}