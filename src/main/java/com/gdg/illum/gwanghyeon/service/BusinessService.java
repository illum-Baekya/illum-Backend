package com.gdg.illum.gwanghyeon.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;

@Service
public class BusinessService {

    private final RestTemplate restTemplate;
    private final AccessTokenService accessTokenService;

    @Value("${api.businessUrl}")
    private String businessApiUrl;

    public BusinessService(RestTemplate restTemplate, AccessTokenService accessTokenService) {
        this.restTemplate = restTemplate;
        this.accessTokenService = accessTokenService;
    }

    public HashMap<String, Object> getNearbyBusinesses(String year, String admCd) {
        String accessToken = accessTokenService.getAccessToken();
        String url = String.format(
                "%s?accessToken=%s&year=%s&adm_cd=%s",
                businessApiUrl, accessToken, year, admCd
        );

        try {
            return restTemplate.getForObject(url, HashMap.class);
        } catch (Exception e) {
            throw new RuntimeException("Failed to fetch business data: " + e.getMessage(), e);
        }
    }
}
