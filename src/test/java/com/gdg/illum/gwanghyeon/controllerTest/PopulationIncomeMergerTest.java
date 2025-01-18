package com.gdg.illum.gwanghyeon.controllerTest;

import com.gdg.illum.BusinessDistrict.service.PopulationIncomeMerger;
import org.junit.jupiter.api.Test;

import java.util.List;

class PopulationIncomeMergerTest {

    @Test
    void testMergeData() {
        // 파일 경로 지정
        String populationFilePath = "src/main/java/com/gdg/illum/gwanghyeon/files/residential_population.csv";
        String incomeFilePath = "src/main/java/com/gdg/illum/jun/files/average_income.csv";

        // PopulationIncomeMerger 인스턴스 생성
        PopulationIncomeMerger merger = new PopulationIncomeMerger();

        // mergeData 호출
        List<PopulationIncomeMerger.MergedRecord> mergedData = merger.mergeData(populationFilePath, incomeFilePath);

        // 결과 출력
        mergedData.forEach(System.out::println);

        // 검증
        assert !mergedData.isEmpty() : "결합된 데이터가 비어 있습니다.";
    }
}
