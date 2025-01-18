package com.gdg.illum.seun.controller;

import com.gdg.illum.seun.service.BusinessDistrictService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/business_district")
public class BusinessDistrictController {

    @Autowired
    private BusinessDistrictService businessDistrictService;

    @GetMapping("/filter/floating_population")
    public ResponseEntity<?> getFilteredPopulation(
            @RequestParam String admCd,
            @RequestParam int minPopulation) {
        try {
            List<Map<String, Object>> result = businessDistrictService.getFilteredAreas(admCd, minPopulation);
            return ResponseEntity.ok(result);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Collections.singletonMap("error", e.getMessage()));
        }
    }
}
