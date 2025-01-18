package com.gdg.illum.gwanghyeon.controllerTest;

import com.gdg.illum.BusinessDistrict.service.PopulationService;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class PopulationServiceTest {

    @Test
    void testGetResidentialPopulation() {
        // PopulationService 인스턴스 생성
        PopulationService populationService = new PopulationService();

        // 테스트 데이터
        String year = "2024";
        String admCd = "11110515"; // CSV에 존재하는 행정동코드 (존재한다고 가정)

        // 서비스 호출
        List<Map<String, String>> response = populationService.getResidentialPopulation(year, admCd);

        // 결과 출력 및 검증
        System.out.println("PopulationService Response: " + response);
        assertNotNull(response, "응답 데이터는 null이 아니어야 합니다.");
        assertFalse(response.isEmpty(), "응답 데이터는 최소 1개 이상의 항목을 포함해야 합니다.");

        // 각 데이터의 필드 검증
        for (Map<String, String> row : response) {
            assertNotNull(row.get("STRD_YR_CD"), "기준년도가 null이 아니어야 합니다.");
            assertEquals(year, row.get("STRD_YR_CD"), "기준년도가 요청한 값과 일치해야 합니다.");
            assertNotNull(row.get("ADSTRD_CD"), "행정동코드가 null이 아니어야 합니다.");
            assertEquals(admCd, row.get("ADSTRD_CD"), "행정동코드가 요청한 값과 일치해야 합니다.");

            // 추가 필드 검증
            assertNotNull(row.get("ADSTRD_CD_NM"), "행정동 이름은 null이 아니어야 합니다.");
            assertNotNull(row.get("TOTL_RESID_PUL_CNT"), "총 상주인구수는 null이 아니어야 합니다.");

            // 모든 컬럼의 데이터가 유효한지 확인
            for (String key : row.keySet()) {
                assertNotNull(row.get(key), "컬럼 [" + key + "]의 값은 null이 아니어야 합니다.");
                assertFalse(row.get(key).isEmpty(), "컬럼 [" + key + "]의 값은 비어있지 않아야 합니다.");
            }
        }
    }
}
