package com.gdg.illum.seun.service;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.*;

@Service
public class BusinessDistrictService {

    private final String accessToken = "e4ec5eb5-be81-415b-a78d-3c50b0982748";
    private static final String STARTUP_BIZ_URL = "https://sgisapi.kostat.go.kr/OpenAPI3/startupbiz/startupbiz.json";
    private static final String PPL_SUMMARY_URL = "https://sgisapi.kostat.go.kr/OpenAPI3/startupbiz/pplsummary.json";

    private final RestTemplate restTemplate = new RestTemplate();

    public List<Map<String, Object>> getFilteredAreas(String admCd, int minPopulation) {
        if (admCd.length() != 5 && admCd.length() != 8) {
            throw new IllegalArgumentException("행정구역 코드는 5자리 또는 8자리여야 합니다.");
        }
        if (minPopulation < 0) {
            throw new IllegalArgumentException("최소값은 0 이상이어야 합니다.");
        }

        // Step 1: 생활업종 후보지 검색 API 호출
        List<Map<String, Object>> filteredAreas = filterByPplType(admCd);

        // Step 2: 거주인구 요약정보 API 호출 및 추가 필터링
        return filterByAgePopulation(filteredAreas, minPopulation);
    }

    private List<Map<String, Object>> filterByPplType(String admCd) {
        String url = STARTUP_BIZ_URL + "?accessToken=" + accessToken + "&adm_cd=" + admCd;

        Map<String, Object> response;
        try {
            response = restTemplate.getForObject(url, Map.class);
        } catch (Exception e) {
            throw new RuntimeException("생활업종 후보지 검색 API 호출 실패: " + e.getMessage());
        }

        if (response == null || !response.containsKey("result")) {
            throw new RuntimeException("생활업종 후보지 검색 API에서 데이터를 가져올 수 없습니다.");
        }

        List<Map<String, Object>> data = (List<Map<String, Object>>) response.get("result");

        if (data == null || data.isEmpty()) {
            throw new RuntimeException("조건에 맞는 생활업종 데이터를 찾을 수 없습니다.");
        }

        return data;
    }

    private List<Map<String, Object>> filterByAgePopulation(List<Map<String, Object>> areas, int minPopulation) {
        List<Map<String, Object>> result = new ArrayList<>();

        for (Map<String, Object> area : areas) {
            String admCd = (String) area.get("adm_cd");
            String url = PPL_SUMMARY_URL + "?accessToken=" + accessToken + "&adm_cd=" + admCd;

            Map<String, Object> response;
            try {
                response = restTemplate.getForObject(url, Map.class);
            } catch (Exception e) {
                System.err.println("API 호출 실패: " + e.getMessage());
                continue; // API 호출 실패 시 해당 지역 건너뜀
            }

            if (response == null || !response.containsKey("result")) {
                System.err.println("응답 데이터가 올바르지 않습니다: " + admCd);
                continue; // 유효한 데이터가 없으면 건너뜀
            }

            // `result` 배열에서 `adm_cd`가 일치하는 데이터를 찾음
            List<Map<String, Object>> resultList = (List<Map<String, Object>>) response.get("result");
            Optional<Map<String, Object>> targetArea = resultList.stream()
                    .filter(entry -> admCd.equals(entry.get("adm_cd")))
                    .findFirst();

            if (targetArea.isEmpty()) {
                System.err.println("행정구역 코드 " + admCd + "에 대한 데이터를 찾을 수 없습니다.");
                continue; // 일치하는 데이터가 없으면 건너뜀
            }

            // 데이터에서 연령대별 인구 합산
            Map<String, Object> data = targetArea.get();
            int teenage = Integer.parseInt(data.getOrDefault("teenage_cnt", "0").toString());
            int twenty = Integer.parseInt(data.getOrDefault("twenty_cnt", "0").toString());
            int thirty = Integer.parseInt(data.getOrDefault("thirty_cnt", "0").toString());
            int forty = Integer.parseInt(data.getOrDefault("forty_cnt", "0").toString());
            int fifty = Integer.parseInt(data.getOrDefault("fifty_cnt", "0").toString());
            int sixty = Integer.parseInt(data.getOrDefault("sixty_cnt", "0").toString());
            int seventy = Integer.parseInt(data.getOrDefault("seventy_more_than_cnt", "0").toString());

            int totalPopulation = teenage + twenty + thirty + forty + fifty + sixty + seventy;

            if (totalPopulation >= minPopulation) {
                Map<String, Object> areaInfo = new HashMap<>();
                areaInfo.put("adm_cd", admCd);
                areaInfo.put("adm_nm", data.get("adm_nm"));
                areaInfo.put("total_population", totalPopulation);
                result.add(areaInfo);
            }
        }

        if (result.isEmpty()) {
            throw new RuntimeException("조건에 맞는 거주 인구 데이터를 찾을 수 없습니다.");
        }

        return result;
    }
}
