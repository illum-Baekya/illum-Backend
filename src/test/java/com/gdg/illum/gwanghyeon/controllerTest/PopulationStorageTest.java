package com.gdg.illum.gwanghyeon.controllerTest;

import com.gdg.illum.BusinessDistrict.service.ResidentialPopulationStorage;
import org.junit.jupiter.api.Test;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

public class PopulationStorageTest {

    @Test
    void testPopulationStorageDataLoad() {
        // PopulationStorage 인스턴스 생성
        ResidentialPopulationStorage residentialPopulationStorage = new ResidentialPopulationStorage("src/main/java/com/gdg/illum/gwanghyeon/files/residential_population.csv");

        // 데이터 로드 확인
        Map<String, ResidentialPopulationStorage.PopulationRecord> populationMap = residentialPopulationStorage.getPopulationMap();

        // 데이터 검증
        assertNotNull(populationMap, "Population data map should not be null.");
        assertFalse(populationMap.isEmpty(), "Population data map should not be empty.");

        // 특정 데이터 검증
        String testCode = "11110515"; // 샘플 행정구역 코드
        assertTrue(populationMap.containsKey(testCode), "Population map should contain the test code.");

        ResidentialPopulationStorage.PopulationRecord record = populationMap.get(testCode);
        assertNotNull(record, "Record for the test code should not be null.");
        assertEquals("2024", record.getYear(), "Year should match the test data.");
        assertEquals("청운효자동", record.getName(), "Name should match the test data.");
        assertEquals(13009, record.getPopulation(), "Population should match the test data.");

        // 전체 데이터 출력
        populationMap.forEach((code, data) -> System.out.println(
                "Code: " + code +
                        ", Year: " + data.getYear() +
                        ", Name: " + data.getName() +
                        ", Population: " + data.getPopulation()
        ));
    }
}
