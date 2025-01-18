package com.gdg.illum.BusinessDistrict.service;

import com.opencsv.CSVReader;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.*;

@Service
public class PopulationService {

    private final String filePath = "src/main/resources/csv/residential_population.csv";

    /**
     * 고정된 파일에서 데이터를 읽어와 모든 컬럼을 반환
     */
    public List<Map<String, String>> getResidentialPopulation(String year, String admCd) {
        File file = new File(filePath);
        if (!file.exists()) {
            throw new RuntimeException("파일이 존재하지 않습니다: " + file.getAbsolutePath());
        }
        return parseCsvFile(file, year, admCd);
    }

    /**
     * 특정 CSV 파일에서 데이터를 읽고 모든 컬럼을 반환
     */
    private List<Map<String, String>> parseCsvFile(File file, String year, String admCd) {
        List<Map<String, String>> rows = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(file, StandardCharsets.UTF_8))) {
            String headerLine = br.readLine();
            if (headerLine == null) {
                throw new RuntimeException("CSV 파일이 비어 있습니다.");
            }

            // 헤더 파싱 및 컬럼 인덱스 매핑
            String[] headers = headerLine.split("\t");
            Map<String, Integer> columnIndexMap = getColumnIndexMap(headers);

            String line;
            while ((line = br.readLine()) != null) {
                String[] tokens = line.split("\t");
                if (tokens.length != headers.length) {
                    continue; // 잘못된 데이터 행은 건너뜀
                }

                // year와 admCd 필터링
                if (year.equals(tokens[columnIndexMap.get("STRD_YR_CD")].trim())
                        && admCd.equals(tokens[columnIndexMap.get("ADSTRD_CD")].trim())) {
                    Map<String, String> row = new HashMap<>();
                    for (String header : headers) {
                        int columnIndex = columnIndexMap.get(header.trim());
                        row.put(header.trim(), tokens[columnIndex].trim());
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
     * 헤더 배열에서 컬럼 이름과 인덱스 매핑
     */
    private Map<String, Integer> getColumnIndexMap(String[] headers) {
        Map<String, Integer> columnIndexMap = new HashMap<>();
        for (int i = 0; i < headers.length; i++) {
            columnIndexMap.put(headers[i].trim(), i);
        }
        return columnIndexMap;
    }

    public List<Map<String, Object>> getFilteredWorkingPopulation(String admCdPrefix, int minPopulation) {
        String filePath = "csv/직장인구.csv";
        List<Map<String, Object>> filteredAreas = new ArrayList<>();

        try (CSVReader csvReader = new CSVReader(new InputStreamReader(new ClassPathResource(filePath).getInputStream()))) {
            String[] columns;
            csvReader.readNext();

            while ((columns = csvReader.readNext()) != null) {
                String admCd = columns[2];
                String admNm = columns[3];
                int totalWorkPopulation;

                try {
                    totalWorkPopulation = Integer.parseInt(columns[4]);
                } catch (NumberFormatException e) {
                    continue;
                }

                if (admCd.startsWith(admCdPrefix) && totalWorkPopulation > minPopulation) {
                    Map<String, Object> area = new HashMap<>();
                    area.put("adm_cd", admCd);
                    area.put("adm_nm", admNm);
                    area.put("total_work_population", totalWorkPopulation);
                    filteredAreas.add(area);
                }
            }
        } catch (Exception e) {
            throw new RuntimeException("CSV 파일 처리 중 오류가 발생했습니다: " + e.getMessage());
        }

        if (filteredAreas.isEmpty()) {
            throw new RuntimeException("조건에 맞는 데이터가 없습니다.");
        }

        return filteredAreas;
    }

    public List<Map<String, Object>> getFilteredFloatingPopulation2(String admCdPrefix, int minPopulation) {
        String filePath = "csv/유동인구2.csv"; // CSV 파일 경로

        try (CSVReader reader = new CSVReader(new InputStreamReader(new ClassPathResource(filePath).getInputStream(), StandardCharsets.UTF_8))) {
            List<Map<String, Object>> results = new ArrayList<>();
            String[] columns;

            reader.readNext(); // 헤더 건너뛰기

            while ((columns = reader.readNext()) != null) {
                // 데이터 검증: 최소 열 길이 확인 (ADMI_CD, ADMI_NM 포함 여부 확인)
                if (columns.length < 6) { // 최소 6개의 컬럼 필요
                    System.out.println("유효하지 않은 데이터 행 건너뜀: " + Arrays.toString(columns));
                    continue;
                }

                String admCdFull = columns[1].trim(); // ADMI_CD (행정동 코드)
                String admNm = columns[2].trim();    // ADMI_NM (행정동 이름)
                String popCountStr = columns[5].trim(); // POP_CNT (유동인구 합계)

                try {
                    int popCount = parsePopulation(popCountStr); // 유동인구 값 파싱

                    if (admCdFull.startsWith(admCdPrefix) && popCount > minPopulation) {
                        Map<String, Object> result = new HashMap<>();
                        result.put("adm_cd", admCdFull);    // 8자리 코드
                        result.put("adm_nm", admNm);        // 행정동 이름
                        result.put("pop_count", popCount);  // 유동인구 합계
                        results.add(result);
                    }
                } catch (Exception e) {
                    System.out.println("인구수 데이터 처리 중 오류 발생: " + Arrays.toString(columns));
                }
            }

            return results;
        } catch (Exception e) {
            throw new RuntimeException("CSV 파일 처리 중 오류가 발생했습니다: " + e.getMessage());
        }
    }

    private int parsePopulation(String populationStr) {
        try {
            // 숫자로 변환
            String sanitized = populationStr.replace(",", "").trim();
            return Integer.parseInt(sanitized);
        } catch (NumberFormatException e) {
            // 잘못된 값은 0으로 처리
            return 0;
        }
    }
}
