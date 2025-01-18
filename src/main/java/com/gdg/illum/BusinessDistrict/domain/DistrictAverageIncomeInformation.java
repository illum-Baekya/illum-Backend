package com.gdg.illum.BusinessDistrict.domain;

import com.google.gson.annotations.SerializedName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Builder
@AllArgsConstructor
@Getter
public class DistrictAverageIncomeInformation {

    @SerializedName("adm_nm")
    private String name;
    @SerializedName("adm_cd")
    private String code;
    @SerializedName("average_income")
    private Integer averageIncome;
}
