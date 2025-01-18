package com.gdg.illum.gwanghyeon.controllerTest;

import com.gdg.illum.gwanghyeon.service.PopulationService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

class PopulationServiceTest {

    @Mock
    private RestTemplate restTemplate;

    @InjectMocks
    private PopulationService populationService;

    @BeforeEach
    void setUp() {
        // Mock 객체 초기화
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetResidentialPopulation() {
        // 테스트에 사용할 URL
        String url = "https://sgisapi.kostat.go.kr/OpenAPI3/stats/searchpopulation.json?accessToken=testToken&year=2023&adm_cd=11010";

        // Mock 응답 데이터 설정
        HashMap<String, Object> mockResponse = new HashMap<>();
        mockResponse.put("adm_cd", "11010");
        mockResponse.put("population", "5000");

        // Mock 동작 정의
        when(restTemplate.getForObject(url, HashMap.class)).thenReturn(mockResponse);

        // PopulationService 호출
        HashMap<String, Object> response = populationService.getResidentialPopulation("testToken", "2023", "11010");

        // 결과 검증
        assertEquals("11010", response.get("adm_cd"));
        assertEquals("5000", response.get("population"));
    }
}
