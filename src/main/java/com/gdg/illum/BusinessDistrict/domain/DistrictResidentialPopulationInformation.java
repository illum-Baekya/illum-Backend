package com.gdg.illum.BusinessDistrict.domain;

import com.google.gson.annotations.SerializedName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@AllArgsConstructor
@Getter
@Builder
public class DistrictResidentialPopulationInformation {

    @SerializedName("year")
    private final String year; // 데이터 연도

    @SerializedName("region_code")
    private final String regionCode; // SIGNGU_CD

    @SerializedName("region_name")
    private final String regionName; // 지역명

    @SerializedName("total_population")
    private final int totalPopulation; // 총 인구수

    @SerializedName("average_income")
    private final int averageIncome; // 평균 소득
}
