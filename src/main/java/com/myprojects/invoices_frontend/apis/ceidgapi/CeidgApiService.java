package com.myprojects.invoices_frontend.apis.ceidgapi;

import com.myprojects.invoices_frontend.domain.dtos.CeidgApiDto;
import com.myprojects.invoices_frontend.exceptions.CustomerDataNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CeidgApiService {

    private static CeidgApiService ceidgApiService;
    private static CeidgApiClient ceidgApiClient;

    public CeidgApiService(CeidgApiClient ceidgApiClient) {
        CeidgApiService.ceidgApiClient = ceidgApiClient;
    }

    public static CeidgApiService getInstance() {
        if (ceidgApiService == null) {
            ceidgApiService = new CeidgApiService(ceidgApiClient);
        }
        return ceidgApiService;
    }

    public CeidgApiDto getData(String nip) throws CustomerDataNotFoundException {
        return ceidgApiClient.getCeidgData(nip);
    }
}