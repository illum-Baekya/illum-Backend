package com.gdg.illum.jun.store;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import com.google.gson.Gson;

import java.net.URI;

@Component
public class StoreListService {

    private final String URL;
    private final String SERVICE_KEY;
    private static final String PAGE_NO = "1";
    private static final String NUM_OF_ROWS = "1";
    private static final String DIV_ID = "signguCd";
    private static final String TYPE = "json";

    public StoreListService(@Value("${jun.store-list.request-url}") String url, @Value("${jun.store-list.service-key}") String serviceKey) {
        this.URL = url;
        this.SERVICE_KEY = serviceKey;
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
}
