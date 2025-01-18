package com.gdg.illum.gwanghyeon.service;

import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.nio.charset.StandardCharsets;
import java.util.*;

@Service
public class PopulationService {

    private final String filePath = "src/main/java/com/gdg/illum/gwanghyeon/files/residential_population.csv";

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
}
