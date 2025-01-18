package com.gdg.illum.gwanghyeon.controllerTest;

import com.gdg.illum.gwanghyeon.service.BusinessService;
import com.gdg.illum.gwanghyeon.service.AccessTokenService;
import org.junit.jupiter.api.Test;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.assertNotNull;

class BusinessServiceTest {

    @Test
    void testGetNearbyBusinesses() {
        RestTemplate restTemplate = new RestTemplate();
        AccessTokenService accessTokenService = new AccessTokenService(restTemplate);
        BusinessService businessService = new BusinessService(restTemplate, accessTokenService);

        String year = "2023";
        String admCd = "11010";

        HashMap<String, Object> response = businessService.getNearbyBusinesses(year, admCd);

        System.out.println("BusinessService Response: " + response);
        assertNotNull(response, "API response should not be null");
    }
}
