package com.gdg.illum.seun.service;

import com.opencsv.CSVReader;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;
import java.io.InputStreamReader;
import java.util.*;

@Service
public class BusinessDistrictService {

    public List<Map<String, Object>> getFilteredWorkingPopulation(String admCdPrefix, int minPopulation) {
        String filePath = "csv/직장인구.csv";
        List<Map<String, Object>> filteredAreas = new ArrayList<>();

        try (CSVReader csvReader = new CSVReader(new InputStreamReader(new ClassPathResource(filePath).getInputStream()))) {
            String[] columns;
            csvReader.readNext();

            while ((columns = csvReader.readNext()) != null) {
                String admCd = columns[2]; // 행정동 코드
                String admNm = columns[3]; // 행정동 이름
                int totalWorkPopulation;

                try {
                    totalWorkPopulation = Integer.parseInt(columns[4]); // 총 직장인구 수
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

    public List<Map<String, Object>> getFilteredFloatingPopulation(String admCdPrefix, int minPopulation) {
        String filePath = "csv/유동인구.csv";

        try (CSVReader reader = new CSVReader(new InputStreamReader(new ClassPathResource(filePath).getInputStream()))) {
            List<Map<String, Object>> results = new ArrayList<>();
            String[] columns;
            reader.readNext();

            while ((columns = reader.readNext()) != null) {
                String admCdFull = columns[8].trim(); // ADSTRD_CD (8자리)
                String admNm = columns[9].trim();    // ADSTRD_NM
                String populationStr = columns[14].trim(); // PDSTRN_POPLTN_CO (유동인구 수)

                try {
                    int totalFloatingPopulation = Integer.parseInt(populationStr);

                    if (admCdFull.startsWith(admCdPrefix) && totalFloatingPopulation > minPopulation) {
                        Map<String, Object> result = new HashMap<>();
                        result.put("adm_cd", admCdFull);
                        result.put("adm_nm", admNm);
                        result.put("total_floating_population", totalFloatingPopulation);
                        results.add(result);
                    }
                } catch (NumberFormatException e) {
                    System.out.println("인구수 데이터 변환 실패: " + populationStr);
                }
            }

            return results;
        } catch (Exception e) {
            throw new RuntimeException("CSV 파일 처리 중 오류가 발생했습니다: " + e.getMessage());
        }
    }
}
