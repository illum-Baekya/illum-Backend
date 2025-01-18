package com.gdg.illum.jun.store;

import com.gdg.illum.BusinessDistrict.domain.StoreType;
import com.gdg.illum.BusinessDistrict.service.StoreListService;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class StoreListServiceTest {

    @Autowired
    StoreListService storeListService;

    @Test
    void getTotalAmount() {
        Integer result = storeListService.getTotalAmountOfStoreByCode("41281", StoreType.ART_SPORTS);

        Assertions.assertThat(result).isNotNull();
        System.out.println(result);
    }
}
