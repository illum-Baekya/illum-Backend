package com.gdg.illum.BusinessDistrict.controller;

import com.gdg.illum.BusinessDistrict.service.ResidentialPopulationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/residential-population")
public class ResidentialPopulationController {

    private final ResidentialPopulationService residentialPopulationService;

    @Autowired
    public ResidentialPopulationController(ResidentialPopulationService residentialPopulationService) {
        this.residentialPopulationService = residentialPopulationService;
    }

    @GetMapping("/merge")
    public List<ResidentialPopulationService.MergedRecord> getMergedPopulationIncome(
            @RequestParam(defaultValue = "0") int minPopulation
    ) {
        return residentialPopulationService.mergeDataWithMinPopulation(minPopulation);
    }
}
