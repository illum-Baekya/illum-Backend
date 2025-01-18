package com.gdg.illum.gwanghyeon.controllerTest;

import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.nio.charset.StandardCharsets;
import java.util.*;
import com.gdg.illum.BusinessDistrict.service.ResidentialPopulationService;

@Service
public class PopulationServiceTest {

    // 하드코딩된 CSV 파일 경로
    private final String populationFilePath = "src/main/resources/csv/residential_population.csv";
    private final String incomeFilePath = "src/main/resources/csv/average_income.csv";

    // 기존 mergeData 관련 로직은 그대로 두고, 새 로직을 추가합니다.

    /**
     * 특정 연도(year)와 행정동코드(admCd)에 해당하는 CSV 행을 읽어 반환
     * CSV가 탭(\t) 구분인지, 쉼표(,) 구분인지 CSV 구조에 맞게 수정
     * 여기서는 예시로 "\t" 구분 기준
     */
    public List<Map<String, String>> getResidentialPopulation(String year, String admCd) {
        File file = new File(populationFilePath);
        if (!file.exists()) {
            throw new RuntimeException("CSV 파일이 존재하지 않습니다: " + file.getAbsolutePath());
        }

        List<Map<String, String>> rows = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(file, StandardCharsets.UTF_8))) {
            // CSV의 첫 줄(헤더) 읽기
            String headerLine = br.readLine();
            if (headerLine == null) {
                throw new RuntimeException("CSV 파일이 비어 있습니다.");
            }

            // 예) 탭(\t) 구분이라고 가정
            String[] headers = headerLine.split("\t");
            // 헤더 -> 인덱스 맵핑
            Map<String, Integer> columnIndexMap = getColumnIndexMap(headers);

            // CSV 본문(데이터) 읽기
            String line;
            while ((line = br.readLine()) != null) {
                String[] tokens = line.split("\t");
                // 컬럼 개수가 다르면 건너뛰기
                if (tokens.length != headers.length) {
                    continue;
                }

                // 특정 컬럼명이 존재한다고 가정 (STRD_YR_CD, ADSTRD_CD 등)
                String csvYear = tokens[columnIndexMap.get("STRD_YR_CD")].trim();
                String csvAdmCd = tokens[columnIndexMap.get("ADSTRD_CD")].trim();

                // year, admCd로 필터링
                if (year.equals(csvYear) && admCd.equals(csvAdmCd)) {
                    // 해당 행의 모든 컬럼 정보를 Map에 넣기
                    Map<String, String> row = new HashMap<>();
                    for (int i = 0; i < headers.length; i++) {
                        String key = headers[i].trim();
                        String value = tokens[i].trim();
                        row.put(key, value);
                    }
                    rows.add(row);
                }
            }

        } catch (Exception e) {
            throw new RuntimeException("CSV 파일 파싱 중 오류 발생: " + file.getName(), e);
        }

        return rows;
    }

    /**
     * 헤더 배열 -> 컬럼 인덱스 매핑
     */
    private Map<String, Integer> getColumnIndexMap(String[] headers) {
        Map<String, Integer> map = new HashMap<>();
        for (int i = 0; i < headers.length; i++) {
            map.put(headers[i].trim(), i);
        }
        return map;
    }

    // ================================
    // 기존 mergeData / mergeDataWithMinPopulation 등
    // ================================

    /**
     * 병합 로직 (인구+소득) - 예: 그대로 유지
     */
    public List<com.gdg.illum.BusinessDistrict.service.ResidentialPopulationService.MergedRecord> mergeData() {
        // ... (생략: 기존 로직)
        return Collections.emptyList();
    }

    public List<com.gdg.illum.BusinessDistrict.service.ResidentialPopulationService.MergedRecord> mergeDataWithMinPopulation(int minPopulation) {
        // ... (생략: 기존 로직)
        return Collections.emptyList();
    }

    // 내부 클래스 MergedRecord
    // ...
}
