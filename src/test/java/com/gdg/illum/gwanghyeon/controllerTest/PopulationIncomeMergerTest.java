package com.gdg.illum.gwanghyeon.controllerTest;

import com.gdg.illum.BusinessDistrict.service.ResidentialPopulationService;
import org.junit.jupiter.api.Test;

import java.util.List;

class PopulationIncomeMergerTest {

    @Test
    void testMergeData() {
        // 1) Service 인스턴스 생성
        ResidentialPopulationService service = new ResidentialPopulationService();

        // 2) 병합만 테스트 (필터링X)
        List<ResidentialPopulationService.MergedRecord> mergedData = service.mergeData();

        // 결과 출력
        mergedData.forEach(System.out::println);

        // 검증
        assert !mergedData.isEmpty() : "결합된 데이터가 비어 있습니다.";
    }

    @Test
    void testMergeDataWithMinPopulation() {
        ResidentialPopulationService service = new ResidentialPopulationService();

        // 필터링 테스트 (예: 최소 인구 10,000)
        int minPopulation = 10000;
        List<ResidentialPopulationService.MergedRecord> filteredData
                = service.mergeDataWithMinPopulation(minPopulation);

        // 결과 출력
        filteredData.forEach(System.out::println);

        // 검증
        assert !filteredData.isEmpty() : "필터링된 데이터가 비어 있습니다.";
        assert filteredData.stream().allMatch(r -> r.getTotalPopulation() >= minPopulation)
                : "필터링된 데이터 중에서 조건에 맞지 않는 레코드가 존재합니다.";
    }
}
