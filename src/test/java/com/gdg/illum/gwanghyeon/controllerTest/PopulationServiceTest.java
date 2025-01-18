package com.gdg.illum.gwanghyeon.controllerTest;

import com.gdg.illum.gwanghyeon.service.PopulationService;
import com.gdg.illum.gwanghyeon.service.AccessTokenService;
import org.junit.jupiter.api.Test;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.assertNotNull;

class PopulationServiceTest {

    @Test
    void testGetResidentialPopulation() {
        RestTemplate restTemplate = new RestTemplate();
        AccessTokenService accessTokenService = new AccessTokenService(restTemplate);
        PopulationService populationService = new PopulationService(restTemplate, accessTokenService);

        String year = "2023";
        String admCd = "11010";

        HashMap<String, Object> response = populationService.getResidentialPopulation(year, admCd);

        System.out.println("PopulationService Response: " + response);
        assertNotNull(response, "API response should not be null");
    }
}
