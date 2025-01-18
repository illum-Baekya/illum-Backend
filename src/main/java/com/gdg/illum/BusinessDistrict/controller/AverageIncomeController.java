package com.gdg.illum.BusinessDistrict.controller;

import com.gdg.illum.BusinessDistrict.domain.DistrictAverageIncomeInformation;
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
    public ResponseEntity<List<DistrictAverageIncomeInformation>> filterAverageIncome(
            @RequestParam(required = false) String admCdPrefix,
            @RequestParam(defaultValue = "0") int minAverageIncome) {
        List<DistrictAverageIncomeInformation> result = averageIncomeService.getFilteredDistricts(admCdPrefix, minAverageIncome);

        return ResponseEntity.ok(result);
    }
}
