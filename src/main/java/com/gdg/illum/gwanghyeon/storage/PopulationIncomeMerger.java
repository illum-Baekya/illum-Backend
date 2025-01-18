package com.gdg.illum.gwanghyeon.storage;

import com.gdg.illum.BusinessDistrict.service.AverageIncomeService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service // Spring Service 어노테이션으로 이 클래스가 서비스 역할을 수행함을 명시
public class PopulationIncomeMerger {

    // 데이터를 병합하여 List<MergedRecord>로 반환하는 메서드
    public List<MergedRecord> mergeData(String populationFilePath, String incomeFilePath) {
        // PopulationStorage와 AverageIncomeStorage 객체를 파일 경로를 기반으로 초기화
        PopulationStorage populationStorage = new PopulationStorage(populationFilePath);
        AverageIncomeService incomeStorage = new AverageIncomeService(incomeFilePath);

        Map<String, MergedRecord> mergedMap = new HashMap<>(); // SIGNGU_CD별로 데이터를 병합하기 위한 맵 초기화

        // PopulationStorage 데이터를 SIGNGU_CD 기준으로 그룹화
        for (PopulationStorage.PopulationRecord record : populationStorage.getPopulationMap().values()) {
            String signguCd = record.getCode().substring(0, 5); // SIGNGU_CD 추출 (앞 5자리)
            // 이미 병합된 기록이 있으면 가져오고, 없으면 새로 생성
            MergedRecord mergedRecord = mergedMap.getOrDefault(signguCd, new MergedRecord(record.getYear(), signguCd, record.getName(), 0, 0));
            // 해당 지역의 인구수를 누적
            mergedRecord.setTotalPopulation(mergedRecord.getTotalPopulation() + Integer.parseInt(record.getPopulation()));
            // 병합된 데이터를 맵에 추가
            mergedMap.put(signguCd, mergedRecord);
        }

        // AverageIncomeStorage 데이터를 SIGNGU_CD 기준으로 추가
        for (String code : incomeStorage.getEveryCode()) {
            Integer income = incomeStorage.getAverageIncomeByCode(code); // 해당 지역의 평균 소득 데이터 가져오기
            String signguCd = code.substring(0, 5); // SIGNGU_CD 추출 (앞 5자리)

            // 병합된 데이터가 이미 존재하면 평균 소득 데이터를 추가
            if (mergedMap.containsKey(signguCd)) {
                mergedMap.get(signguCd).setAverageIncomePrice(income);
            }
        }

        // 병합된 데이터를 리스트로 변환하여 반환
        return new ArrayList<>(mergedMap.values());
    }

    // 병합된 데이터를 표현하는 내부 클래스
    public static class MergedRecord {
        private String year; // 데이터의 연도
        private String signguCd; // SIGNGU_CD
        private String signguName; // 지역명
        private int totalPopulation; // 총 인구수
        private int averageIncomePrice; // 평균 소득

        // 생성자를 통해 모든 필드를 초기화
        public MergedRecord(String year, String signguCd, String signguName, int totalPopulation, int averageIncomePrice) {
            this.year = year;
            this.signguCd = signguCd;
            this.signguName = signguName;
            this.totalPopulation = totalPopulation;
            this.averageIncomePrice = averageIncomePrice;
        }

        // Getter 및 Setter 메서드
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

        // MergedRecord 객체를 문자열로 표현하는 메서드
        @Override
        public String toString() {
            return "[" + year + ", " + signguCd + ", " + signguName + ", " + totalPopulation + ", " + averageIncomePrice + "]";
        }

    }
}
