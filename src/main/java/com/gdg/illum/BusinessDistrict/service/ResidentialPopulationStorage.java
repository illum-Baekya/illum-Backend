package com.gdg.illum.BusinessDistrict.service;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

// 파일에서 인구 데이터를 읽어와 저장하는 클래스
public class ResidentialPopulationStorage {

    private final Map<String, PopulationRecord> populationMap = new HashMap<>(); // 지역 코드와 PopulationRecord를 저장하는 맵

    // 생성자: 파일 경로를 받아 인구 데이터를 읽어와 populationMap에 저장
    public ResidentialPopulationStorage(String filePath) {
        try (BufferedReader br = new BufferedReader(new FileReader(new File(filePath), StandardCharsets.UTF_8))) { // 파일을 읽기 위한 BufferedReader 생성
            String line;
            boolean firstLine = true; // 파일의 첫 번째 줄(헤더)을 건너뛰기 위한 플래그
            while ((line = br.readLine()) != null) { // 파일 끝까지 한 줄씩 읽기
                if (firstLine) {
                    firstLine = false; // 첫 번째 줄을 건너뛰고 플래그 변경
                    continue;
                }
                String[] tokens = line.split(","); // 한 줄을 쉼표(,) 기준으로 분리
                String year = tokens[0]; // 연도
                String code = tokens[1]; // 지역 코드
                String name = tokens[2]; // 지역 이름
                String population = tokens[3]; // 인구 수

                // 읽은 데이터를 PopulationRecord 객체로 생성하여 맵에 저장
                populationMap.put(code, new PopulationRecord(year, code, name, population));
            }
        } catch (Exception e) { // 파일 읽기 중 발생한 예외 처리
            e.printStackTrace(); // 예외 메시지 출력
        }
    }

    // Population 데이터를 반환하는 메서드
    public Map<String, PopulationRecord> getPopulationMap() {
        return populationMap; // 지역 코드와 PopulationRecord의 맵 반환
    }

    // 인구 데이터를 표현하는 내부 클래스
    public static class PopulationRecord {
        private final String year; // 데이터의 연도
        private final String code; // 지역 코드
        private final String name; // 지역 이름
        private final String population; // 인구 수

        // 생성자: 모든 필드를 초기화
        public PopulationRecord(String year, String code, String name, String population) {
            this.year = year;
            this.code = code;
            this.name = name;
            this.population = population;
        }

        // Getter 메서드: 각 필드의 값을 반환
        public String getYear() {
            return year; // 연도 반환
        }

        public String getCode() {
            return code; // 지역 코드 반환
        }

        public String getName() {
            return name; // 지역 이름 반환
        }

        public String getPopulation() {
            return population; // 인구 수 반환
        }
    }
}
