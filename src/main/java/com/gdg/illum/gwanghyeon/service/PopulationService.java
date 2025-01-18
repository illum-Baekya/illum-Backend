package com.gdg.illum.gwanghyeon.service;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;

@Service // Spring Bean으로 등록
public class PopulationService {

    private final RestTemplate restTemplate;

    public PopulationService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public HashMap<String, Object> getResidentialPopulation(String accessToken, String year, String admCd) {
        String url = String.format(
                "https://sgisapi.kostat.go.kr/OpenAPI3/stats/searchpopulation.json?accessToken=%s&year=%s&adm_cd=%s",
                accessToken, year, admCd
        );

        return restTemplate.getForObject(url, HashMap.class);
    }
}
