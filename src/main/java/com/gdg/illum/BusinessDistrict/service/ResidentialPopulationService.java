package com.gdg.illum.BusinessDistrict.service;

import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class ResidentialPopulationService {

    private final String populationFilePath = "src/main/resources/csv/residential_population.csv";
    private final String incomeFilePath = "src/main/resources/csv/average_income.csv";

    public List<MergedRecord> mergeData() {
        ResidentialPopulationStorage residentialPopulationStorage = new ResidentialPopulationStorage(populationFilePath);
        AverageIncomeService incomeStorage = new AverageIncomeService(incomeFilePath);

        Map<String, MergedRecord> mergedMap = new HashMap<>();

        for (ResidentialPopulationStorage.PopulationRecord record : residentialPopulationStorage.getPopulationMap().values()) {
            String signguCd = record.getCode().substring(0, 5);
            MergedRecord mergedRecord = mergedMap.getOrDefault(signguCd,
                    new MergedRecord(record.getYear(), signguCd, record.getName(), 0, 0));
            mergedRecord.setTotalPopulation(mergedRecord.getTotalPopulation() + Integer.parseInt(record.getPopulation()));
            mergedMap.put(signguCd, mergedRecord);
        }

        for (String code : incomeStorage.getEveryCode()) {
            int income = incomeStorage.getAverageIncomeByCode(code);
            String signguCd = code.substring(0, 5);
            if (mergedMap.containsKey(signguCd)) {
                mergedMap.get(signguCd).setAverageIncomePrice(income);
            }
        }

        return new ArrayList<>(mergedMap.values());
    }

    public List<MergedRecord> mergeDataWithMinPopulation(int minPopulation) {
        List<MergedRecord> mergedRecords = mergeData();

        return mergedRecords.stream()
                .filter(record -> record.getTotalPopulation() >= minPopulation)
                .collect(Collectors.toList());
    }

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
