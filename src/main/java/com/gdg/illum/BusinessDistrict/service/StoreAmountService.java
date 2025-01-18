package com.gdg.illum.BusinessDistrict.service;

import com.gdg.illum.BusinessDistrict.domain.DistrictAverageIncomeInformation;
import com.gdg.illum.BusinessDistrict.domain.DistrictStoreAmountInformation;
import com.gdg.illum.BusinessDistrict.domain.StoreType;
import com.gdg.illum.BusinessDistrict.dto.res.StoreListInfoDto;
import com.gdg.illum.BusinessDistrict.service.utils.CodeUtil;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Component
public class StoreAmountService {

    private final String URL;
    private final String SERVICE_KEY;
    private static final String PAGE_NO = "1";
    private static final String NUM_OF_ROWS = "1";
    private static final String DIV_ID = "signguCd";
    private static final String TYPE = "json";
    private final AverageIncomeService averageIncomeService;

    public StoreAmountService(@Value("${jun.store-list.request-url}") String url, @Value("${jun.store-list.service-key}") String serviceKey, AverageIncomeService averageIncomeService) {
        this.URL = url;
        this.SERVICE_KEY = serviceKey;
        this.averageIncomeService = averageIncomeService;
    }

    /**
     * 해당 시군구에 위치한 가게의 수를 반환하는 메서드
     * @param code 시군구 코드
     * @param storeType 가게 분류
     * @return
     */
    public Integer getTotalAmountOfStoreByCode(String code, StoreType storeType) {

        StringBuilder urlBuilder = new StringBuilder(URL)
                .append("?serviceKey=").append(SERVICE_KEY)
                .append("&pageNo=").append(PAGE_NO)
                .append("&numOfRows=").append(NUM_OF_ROWS)
                .append("&divId=").append(DIV_ID)
                .append("&type=").append(TYPE)
                .append("&key=").append(code);
        if (storeType != null && storeType != StoreType.NULL) {
            urlBuilder.append("&indsLclsCd=").append(storeType.getCode());
        }

        String url = urlBuilder.toString();

        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        RequestEntity<String> requestEntity = new RequestEntity<>(headers, HttpMethod.GET, URI.create(url));
        ResponseEntity<String> responseEntity = restTemplate.exchange(requestEntity, String.class);

        String json = responseEntity.getBody();
        Gson gson = new Gson();

        return gson.fromJson(json, StoreListInfoDto.class).getTotalCount();
    }

    public List<DistrictStoreAmountInformation> getFilteredDistricts(String codePrefix, int minStoreAmount, StoreType storeType) {
        // 장소 데이터는 average_income.csv를 통해 받아옴
        Map<String, DistrictAverageIncomeInformation> informations = averageIncomeService.getDistrictInformations();

        List<DistrictStoreAmountInformation> result = new ArrayList<>();

        informations.entrySet().stream()
                .filter(entry -> {
                    String code = entry.getKey();
                    return CodeUtil.isSamePrefix(code, codePrefix);
                })
                .forEach(entry -> {
                    String code = entry.getKey();
                    Integer storeAmount = getTotalAmountOfStoreByCode(code, storeType);
                    if (storeAmount >= minStoreAmount) {
                        result.add(DistrictStoreAmountInformation.builder()
                                .code(code)
                                .name(entry.getValue().getName())
                                .storeAmount(storeAmount)
                                .build());
                    }
                });

        return result;
    }
}
