package com.gdg.illum.BusinessDistrict.controller;

import com.gdg.illum.BusinessDistrict.domain.DistrictStoreAmountInformation;
import com.gdg.illum.BusinessDistrict.domain.StoreType;
import com.gdg.illum.BusinessDistrict.service.StoreAmountService;
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
public class StoreAmountController {

    private final StoreAmountService storeAmountService;

    @GetMapping("/filter/amount")
    public ResponseEntity<List<DistrictStoreAmountInformation>> filterAverageIncome(
            @RequestParam String admCdPrefix,
            @RequestParam(defaultValue = "0") int minStoreAmount,
            @RequestParam(required = false) StoreType storeType) {
        List<DistrictStoreAmountInformation> result = storeAmountService.getFilteredDistricts(admCdPrefix, minStoreAmount, storeType);

        return ResponseEntity.ok(result);
    }
}
