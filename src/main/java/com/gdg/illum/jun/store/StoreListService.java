package com.gdg.illum.jun.store;

import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import com.google.gson.Gson;

import java.net.URI;

@Component
public class StoreListService {

    private static final String URL = "http://apis.data.go.kr/B553077/api/open/sdsc2/storeListInDong";
    private static final String SERVICE_KEY = "CvywT0zIK%2Fcwacxmb7mfWNxuy0k%2Fo%2FKonAqYJ7G1Lb2EOGr7iXJh66sm%2BUwqXB0DG2ETI%2BM22JoY4fO3xVTtiw%3D%3D";
    private static final String PAGE_NO = "1";
    private static final String NUM_OF_ROWS = "1";
    private static final String DIV_ID = "signguCd";
    private static final String TYPE = "json";

    public Integer getTotalAmountOfStoreBySignguCode(String code, StoreType storeType) {

        StringBuilder urlBuilder = new StringBuilder(URL)
                .append("?serviceKey=").append(SERVICE_KEY)
                .append("&pageNo=").append(PAGE_NO)
                .append("&numOfRows=").append(NUM_OF_ROWS)
                .append("&divId=").append(DIV_ID)
                .append("&type=").append(TYPE)
                .append("&key=").append(code);
        if (storeType != StoreType.NULL) {
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
