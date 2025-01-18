package com.gdg.illum.BusinessDistrict.controller;

import com.gdg.illum.BusinessDistrict.domain.DistrictInformation;
import com.gdg.illum.BusinessDistrict.service.AverageIncomeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/business_district")
@RequiredArgsConstructor
public class AverageIncomeController {

    private final AverageIncomeService averageIncomeService;

    @GetMapping("/filter/income")
    public ResponseEntity<List<DistrictInformation>> filterAverageIncome(
            @RequestParam String admCdPrefix,
            @RequestParam int minAverageIncome) {
        List<DistrictInformation> result = averageIncomeService.getFilteredDistricts(admCdPrefix, minAverageIncome);

        return ResponseEntity.ok(result);
    }
}
