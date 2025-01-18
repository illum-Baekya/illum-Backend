package com.gdg.illum.BusinessDistrict.controller;

import com.gdg.illum.gwanghyeon.service.PopulationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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
}
