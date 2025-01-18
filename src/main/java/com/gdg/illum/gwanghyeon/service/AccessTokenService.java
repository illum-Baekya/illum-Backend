package com.gdg.illum.gwanghyeon.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;

@Service
public class AccessTokenService {

    private final RestTemplate restTemplate;

    @Value("${api.serviceId}")
    private String serviceId;

    @Value("${api.secretKey}")
    private String secretKey;

    @Value("${api.authUrl}")
    private String authUrl;

    private String accessToken;
    private long accessTimeout;

    public AccessTokenService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public String getAccessToken() {
        if (accessToken == null || System.currentTimeMillis() / 1000 > accessTimeout) {
            refreshAccessToken();
        }
        return accessToken;
    }

    private void refreshAccessToken() {
        String url = String.format("%s?consumer_key=%s&consumer_secret=%s", authUrl, serviceId, secretKey);

        try {
            HashMap<String, Object> response = restTemplate.getForObject(url, HashMap.class);
            if (response != null && "success".equals(response.get("result"))) {
                this.accessToken = (String) response.get("accessToken");
                this.accessTimeout = Long.parseLong(response.get("accessTimeout").toString());
            } else {
                throw new RuntimeException("Failed to fetch access token: " + response);
            }
        } catch (Exception e) {
            throw new RuntimeException("Error while refreshing access token: " + e.getMessage(), e);
        }
    }
}
