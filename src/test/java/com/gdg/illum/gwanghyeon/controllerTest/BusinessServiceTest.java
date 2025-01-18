package com.gdg.illum.gwanghyeon.controllerTest;

import com.gdg.illum.gwanghyeon.service.BusinessService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

class BusinessServiceTest {

    @Mock
    private RestTemplate restTemplate;

    @InjectMocks
    private BusinessService businessService;

    @Value("${api.business.url}")
    private String businessApiUrl = "https://sgisapi.kostat.go.kr/OpenAPI3/stats/company.json";

    @BeforeEach
    void setUp() {
        // Mock 객체 초기화
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetNearbyBusinesses() {
        // 가상 URL
        String url = String.format("%s?accessToken=testToken&year=2023&adm_cd=11010", businessApiUrl);

        // Mock 응답 데이터 설정
        HashMap<String, Object> mockResponse = new HashMap<>();
        mockResponse.put("adm_cd", "11010");
        mockResponse.put("corp_cnt", "300");

        // Mock 동작 정의
        when(restTemplate.getForObject(url, HashMap.class)).thenReturn(mockResponse);

        // BusinessService 호출
        HashMap<String, Object> response = businessService.getNearbyBusinesses("testToken", "2023", "11010");

        // 데이터 출력
        System.out.println("BusinessService Response:");
        response.forEach((key, value) -> System.out.println(key + ": " + value));

        // 결과 검증
        assertEquals("11010", response.get("adm_cd"));
        assertEquals("300", response.get("corp_cnt"));
    }
}