package com.gdg.illum.BusinessDistrict.dto.res;

public class StoreListInfoDto {

    private Body body;

    public Integer getTotalCount() {
        return body.totalCount;
    }

    static class Body {
        private Integer totalCount;
    }
}
