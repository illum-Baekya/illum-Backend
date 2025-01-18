package com.gdg.illum.gwanghyeon.controllerTest;

import com.gdg.illum.BusinessDistrict.service.ResidentialPopulationService;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;

class ResidentialPopulationServiceTest {

    @Test
    void testGetTotalPopulationByCode() {
        ResidentialPopulationService service = new ResidentialPopulationService();

        String testCode = "11110515";  // CSV에 존재하는 코드 (예시)
        int totalPop = service.getTotalPopulationByCode(testCode);

        System.out.println("Code: " + testCode + ", Total Population: " + totalPop);

        // 검증
        assertTrue(totalPop > 0, "총 인구수가 0보다 커야 합니다.");
    }
}