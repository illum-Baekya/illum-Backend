package com.gdg.illum.gwanghyeon.storage;

import com.gdg.illum.jun.income.AverageIncomeStorage;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class PopulationIncomeMerger {

    public List<MergedRecord> mergeData(String populationFilePath, String incomeFilePath) {
        // PopulationStorage와 AverageIncomeStorage를 각각 파일 경로로 초기화
        PopulationStorage populationStorage = new PopulationStorage(populationFilePath);
        AverageIncomeStorage incomeStorage = new AverageIncomeStorage(incomeFilePath);

        Map<String, MergedRecord> mergedMap = new HashMap<>();

        // PopulationStorage 데이터를 그룹화
        for (PopulationStorage.PopulationRecord record : populationStorage.getPopulationMap().values()) {
            String signguCd = record.getCode().substring(0, 5); // SIGNGU_CD 추출
            MergedRecord mergedRecord = mergedMap.getOrDefault(signguCd, new MergedRecord(record.getYear(), signguCd, record.getName(), 0, 0));
            mergedRecord.setTotalPopulation(mergedRecord.getTotalPopulation() + Integer.parseInt(record.getPopulation()));
            mergedMap.put(signguCd, mergedRecord);
        }

        // AverageIncomeStorage 데이터를 추가
        for (String code : incomeStorage.getEverySignguCd()) {
            Integer income = incomeStorage.getAverageIncomeByCode(code);
            String signguCd = code.substring(0, 5); // SIGNGU_CD 추출

            if (mergedMap.containsKey(signguCd)) {
                mergedMap.get(signguCd).setAverageIncomePrice(income);
            }
        }

        return new ArrayList<>(mergedMap.values());
    }

    public static class MergedRecord {
        private String year;
        private String signguCd;
        private String signguName;
        private int totalPopulation;
        private int averageIncomePrice;

        public MergedRecord(String year, String signguCd, String signguName, int totalPopulation, int averageIncomePrice) {
            this.year = year;
            this.signguCd = signguCd;
            this.signguName = signguName;
            this.totalPopulation = totalPopulation;
            this.averageIncomePrice = averageIncomePrice;
        }

        public String getYear() {
            return year;
        }

        public String getSignguCd() {
            return signguCd;
        }

        public String getSignguName() {
            return signguName;
        }

        public int getTotalPopulation() {
            return totalPopulation;
        }

        public void setTotalPopulation(int totalPopulation) {
            this.totalPopulation = totalPopulation;
        }

        public int getAverageIncomePrice() {
            return averageIncomePrice;
        }

        public void setAverageIncomePrice(int averageIncomePrice) {
            this.averageIncomePrice = averageIncomePrice;
        }

        @Override
        public String toString() {
            return "MergedRecord{" +
                    "year='" + year + '\'' +
                    ", signguCd='" + signguCd + '\'' +
                    ", signguName='" + signguName + '\'' +
                    ", totalPopulation=" + totalPopulation +
                    ", averageIncomePrice=" + averageIncomePrice +
                    '}';
        }
    }
}
