package com.gdg.illum.BusinessDistrict.service;

import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class ResidentialPopulationService {

    // 파일 경로 하드코딩
    private final String populationFilePath = "src/main/resources/csv/residential_population.csv";
    private final String incomeFilePath = "src/main/resources/csv/average_income.csv";


    /**
     * 특정 동(행정구역) 코드로 총 인구수를 반환
     * @param signguCd 8자리 코드
     * @return 해당 동의 totalPopulation (없으면 0)
     */
    public int getTotalPopulationByCode(String signguCd) {
        // 1) 모든 병합 데이터 로드
        List<MergedRecord> mergedRecords = mergeData();

        // 2) 필요한 동 찾기
        //    방법 1) for-loop
        /*
        for (MergedRecord record : mergedRecords) {
            if (record.getSignguCd().equals(signguCd)) {
                return record.getTotalPopulation();
            }
        }
        return 0; // 못 찾으면 0
        */

        //    방법 2) Stream 이용
        return mergedRecords.stream()
                .filter(r -> r.getSignguCd().equals(signguCd))
                .mapToInt(MergedRecord::getTotalPopulation)
                .findFirst()
                .orElse(0);
    }


    /**
     * 병합된 데이터를 반환
     */
    public List<MergedRecord> mergeData() {
        // 인구/소득 CSV 읽기
        ResidentialPopulationStorage residentialPopulationStorage = new ResidentialPopulationStorage(populationFilePath);
        AverageIncomeService incomeStorage = new AverageIncomeService(incomeFilePath);

        Map<String, MergedRecord> mergedMap = new HashMap<>();

        // 인구 데이터 병합
        for (ResidentialPopulationStorage.PopulationRecord record : residentialPopulationStorage.getPopulationMap().values()) {
            // 1) 전체 코드(8자리 등)를 그대로 사용
            String fullCode = record.getCode();

            // 이미 존재하면 가져오고, 없으면 새로 생성
            MergedRecord mergedRecord = mergedMap.getOrDefault(
                    fullCode,
                    new MergedRecord(record.getYear(), fullCode, record.getName(), 0, 0)
            );

            // 해당 지역의 인구를 누적
            mergedRecord.setTotalPopulation(
                    mergedRecord.getTotalPopulation() + Integer.parseInt(record.getPopulation())
            );

            // 맵에 저장
            mergedMap.put(fullCode, mergedRecord);
        }

        // 소득 데이터 병합
        for (String code : incomeStorage.getEveryCode()) {
            // 2) 소득 코드도 전체 사용
            String fullCode = code;

            int income = incomeStorage.getAverageIncomeByCode(fullCode);

            // 만약 맵에 해당 코드가 이미 존재한다면 소득 설정
            if (mergedMap.containsKey(fullCode)) {
                mergedMap.get(fullCode).setAverageIncomePrice(income);
            } else {
                // 혹시 인구 데이터에 없고 소득 데이터만 있는 경우?
                // 필요하다면 새롭게 추가하거나, 그냥 무시할 수 있음
                // 여기서는 예시로 무시
            }
        }

        return new ArrayList<>(mergedMap.values());
    }

    /**
     * 최소 인구(minPopulation) 이상의 레코드만 필터링하여 반환
     */
    public List<MergedRecord> mergeDataWithMinPopulation(int minPopulation) {
        List<MergedRecord> mergedRecords = mergeData();

        // 최소 인구 기준 필터링
        return mergedRecords.stream()
                .filter(record -> record.getTotalPopulation() >= minPopulation)
                .collect(Collectors.toList());
    }

    // 병합 결과를 표현하는 내부 클래스
    public static class MergedRecord {
        private String year;             // 데이터의 연도
        private String signguCd;         // SIGNGU_CD (8자리 전체)
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
