package com.gdg.illum.BusinessDistrict.controller;

import com.gdg.illum.BusinessDistrict.service.ResidentialPopulationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/population")
public class PopulationController {

    private final ResidentialPopulationService residentialPopulationService;

    @Autowired
    public PopulationController(ResidentialPopulationService residentialPopulationService) {
        this.residentialPopulationService = residentialPopulationService;
    }

    @GetMapping
    public List<Map<String, String>> getResidentialPopulation(
            @RequestParam String year,
            @RequestParam String admCd
    ) {
        return residentialPopulationService.getResidentialPopulation(year, admCd);
    }

    @GetMapping("/filter/working_population")
    public ResponseEntity<List<Map<String, Object>>> filterWorkingPopulation(
            @RequestParam String admCdPrefix,
            @RequestParam int minPopulation) {

        List<Map<String, Object>> result = residentialPopulationService.getFilteredWorkingPopulation(admCdPrefix, minPopulation);
        return ResponseEntity.ok(result);
    }

    @GetMapping("/filter/floating_population")
    public ResponseEntity<List<Map<String, Object>>> filterFloatingPopulation(
            @RequestParam String admCdPrefix,
            @RequestParam int minPopulation) {

        List<Map<String, Object>> result = residentialPopulationService.getFilteredFloatingPopulation(admCdPrefix, minPopulation);

        return ResponseEntity.ok(result);
    }
}
