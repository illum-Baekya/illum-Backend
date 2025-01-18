package com.gdg.illum.seun.service;

import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class BusinessDistrictService {

    public List<Map<String, Object>> getFilteredWorkingPopulation(String admCdPrefix, int minPopulation) {
        String filePath = "csv/직장인구.csv";
        List<Map<String, Object>> filteredAreas = new ArrayList<>();

        try {
            ClassPathResource resource = new ClassPathResource(filePath);
            BufferedReader br = new BufferedReader(new InputStreamReader(resource.getInputStream()));

            String line;
            boolean isHeader = true;

            while ((line = br.readLine()) != null) {
                if (isHeader) {
                    isHeader = false;
                    continue;
                }

                String[] columns = line.split(",");
                String admCd = columns[2]; // 행정동 코드
                String admNm = columns[3]; // 행정동 이름
                int totalWorkPopulation = Integer.parseInt(columns[4]); // 총 직장인구 수

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
}
