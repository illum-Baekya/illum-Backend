package com.gdg.illum.BusinessDistrict.service;

import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class ResidentialPopulationService {

    // 1) 파일 경로를 고정(하드코딩)으로 설정
    private final String populationFilePath = "src/main/resources/csv/residential_population.csv";
    private final String incomeFilePath = "src/main/resources/csv/average_income.csv";

    /**
     * 병합된 데이터를 반환
     */
    public List<MergedRecord> mergeData() {
        ResidentialPopulationStorage residentialPopulationStorage = new ResidentialPopulationStorage(populationFilePath);
        AverageIncomeService incomeStorage = new AverageIncomeService(incomeFilePath);

        Map<String, MergedRecord> mergedMap = new HashMap<>();

        // 인구 데이터 병합
        for (ResidentialPopulationStorage.PopulationRecord record : residentialPopulationStorage.getPopulationMap().values()) {
            String signguCd = record.getCode().substring(0, 5);
            MergedRecord mergedRecord = mergedMap.getOrDefault(signguCd,
                    new MergedRecord(record.getYear(), signguCd, record.getName(), 0, 0));
            mergedRecord.setTotalPopulation(mergedRecord.getTotalPopulation() + Integer.parseInt(record.getPopulation()));
            mergedMap.put(signguCd, mergedRecord);
        }

        // 소득 데이터 병합
        for (String code : incomeStorage.getEveryCode()) {
            int income = incomeStorage.getAverageIncomeByCode(code);
            String signguCd = code.substring(0, 5);
            if (mergedMap.containsKey(signguCd)) {
                mergedMap.get(signguCd).setAverageIncomePrice(income);
            }
        }

        return new ArrayList<>(mergedMap.values());
    }

    /**
     * 최소 인구(minPopulation) 이상의 레코드만 필터링하여 반환
     */
    public List<MergedRecord> mergeDataWithMinPopulation(int minPopulation) {
        // 1) 기본 병합 로직
        List<MergedRecord> mergedRecords = mergeData();

        // 2) 필터링
        return mergedRecords.stream()
                .filter(record -> record.getTotalPopulation() >= minPopulation)
                .collect(Collectors.toList());
    }

    // ======================================
    // 병합된 데이터를 표현하는 내부 클래스
    // ======================================
    public static class MergedRecord {
        private String year;             // 데이터의 연도
        private String signguCd;         // SIGNGU_CD
        private String signguName;       // 지역명
        private int totalPopulation;     // 총 인구수
        private int averageIncomePrice;  // 평균 소득

        public MergedRecord(String year, String signguCd, String signguName, int totalPopulation, int averageIncomePrice) {
            this.year = year;
            this.signguCd = signguCd;
            this.signguName = signguName;
            this.totalPopulation = totalPopulation;
            this.averageIncomePrice = averageIncomePrice;
        }

        // Getter / Setter
        public String getYear() { return year; }
        public String getSignguCd() { return signguCd; }
        public String getSignguName() { return signguName; }
        public int getTotalPopulation() { return totalPopulation; }
        public void setTotalPopulation(int totalPopulation) { this.totalPopulation = totalPopulation; }
        public int getAverageIncomePrice() { return averageIncomePrice; }
        public void setAverageIncomePrice(int averageIncomePrice) { this.averageIncomePrice = averageIncomePrice; }

        @Override
        public String toString() {
            return "[" + year + ", " + signguCd + ", " + signguName + ", " + totalPopulation + ", " + averageIncomePrice + "]";
        }
    }
}
