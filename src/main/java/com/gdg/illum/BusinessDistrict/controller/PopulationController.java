package com.gdg.illum.BusinessDistrict.controller;

import com.gdg.illum.BusinessDistrict.service.PopulationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/population")
public class PopulationController {

    private final PopulationService populationService;

    @Autowired
    public PopulationController(PopulationService populationService) {
        this.populationService = populationService;
    }

    @GetMapping
    public List<Map<String, String>> getResidentialPopulation(
            @RequestParam String year,
            @RequestParam String admCd
    ) {
        return populationService.getResidentialPopulation(year, admCd);
    }

    @GetMapping("/filter/working_population")
    public ResponseEntity<List<Map<String, Object>>> filterWorkingPopulation(
            @RequestParam(required = false) String admCdPrefix,
            @RequestParam(required = false) Integer minPopulation) {
        if (minPopulation == null) {
            minPopulation = 0;
        }

        List<Map<String, Object>> result = populationService.getFilteredWorkingPopulation(admCdPrefix, minPopulation);
        return ResponseEntity.ok(result);
    }

    @GetMapping("/working")
    public ResponseEntity<List<Map<String, Object>>> getAllWorkingPopulation() {
        return ResponseEntity.ok(populationService.getAllWorkingPopulation());
    }

    @GetMapping("/filter/floating_population")
    public ResponseEntity<List<Map<String, Object>>> filterFloatingPopulation(
            @RequestParam(required = false) String admCdPrefix,
            @RequestParam(required = false) Integer minPopulation) {
        if (minPopulation == null) {
            minPopulation = 0;
        }
        List<Map<String, Object>> result = populationService.getFilteredFloatingPopulation(admCdPrefix, minPopulation);

        return ResponseEntity.ok(result);
    }

    @GetMapping("/floating")
    public ResponseEntity<List<Map<String, Object>>> getAllFloatingPopulation() {
        return ResponseEntity.ok(populationService.getAllFloatingPopulation());
    }
}
