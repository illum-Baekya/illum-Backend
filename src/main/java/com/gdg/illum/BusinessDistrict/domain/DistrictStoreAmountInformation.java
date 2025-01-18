package com.gdg.illum.BusinessDistrict.domain;

import com.google.gson.annotations.SerializedName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@AllArgsConstructor
@Getter
@Builder
public class DistrictStoreAmountInformation {

    private String name;
    private String code;
    @SerializedName("store_amount")
    private Integer storeAmount;
}
