package com.gdg.illum.gwanghyeon.controllerTest;

import com.gdg.illum.gwanghyeon.service.PopulationService;
import org.junit.jupiter.api.Test;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.assertNotNull;

class PopulationServiceTest {

    @Test
    void testGetResidentialPopulation() {
        // RestTemplate 초기화
        RestTemplate restTemplate = new RestTemplate();

        // 실제 PopulationService 인스턴스 생성
        PopulationService populationService = new PopulationService(restTemplate);

        // 실제 API 호출에 사용할 파라미터
        String accessToken = "yourAccessToken"; // 유효한 토큰으로 교체
        String year = "2023";
        String admCd = "11010";

        // PopulationService 호출
        HashMap<String, Object> response = populationService.getResidentialPopulation(accessToken, year, admCd);

        // 데이터 출력
        System.out.println("PopulationService Response:");
        if (response != null) {
            response.forEach((key, value) -> System.out.println(key + ": " + value));
        } else {
            System.out.println("Response is null. Check your API parameters or accessToken.");
        }

        // 결과 검증
        assertNotNull(response, "API response should not be null");
    }
}
