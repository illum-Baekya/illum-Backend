package com.gdg.illum.jun.income;

import com.gdg.illum.BusinessDistrict.service.AverageIncomeService;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class AverageIncomeServiceTest {

    @Autowired
    private AverageIncomeService averageIncomeService;

    @Test
    void getAverageIncomeByCode() {
        Integer result = averageIncomeService.getAverageIncomeByCode("51770");

        Assertions.assertThat(result).isNotNull();

        System.out.println(result);
    }
}
