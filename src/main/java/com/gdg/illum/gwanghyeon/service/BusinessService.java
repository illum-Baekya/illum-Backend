package com.gdg.illum.gwanghyeon.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;

@Service
public class BusinessService {

    @Autowired
    private RestTemplate restTemplate;

    @Value("${api.business.url}")
    private String businessApiUrl;

    // 사업체 통계 데이터 가져오기
    public HashMap<String, Object> getNearbyBusinesses(String accessToken, String year, String admCd) {
        String url = String.format(
                "%s?accessToken=%s&year=%s&adm_cd=%s",
                businessApiUrl, accessToken, year, admCd
        );

        // API 호출 후 결과 반환
        return restTemplate.getForObject(url, HashMap.class);
    }
}
