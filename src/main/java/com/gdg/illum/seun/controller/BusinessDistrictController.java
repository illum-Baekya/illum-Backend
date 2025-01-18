package com.gdg.illum.seun.controller;

import com.gdg.illum.seun.service.BusinessDistrictService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/business_district")
public class BusinessDistrictController {

    @Autowired
    private BusinessDistrictService businessDistrictService;

    @GetMapping("/filter/working_population")
    public ResponseEntity<List<Map<String, Object>>> filterBusinessDistricts(
            @RequestParam String admCdPrefix,
            @RequestParam int minPopulation) {

        List<Map<String, Object>> result = businessDistrictService.getFilteredAreas(admCdPrefix, minPopulation);
        return ResponseEntity.ok(result);
    }
}
