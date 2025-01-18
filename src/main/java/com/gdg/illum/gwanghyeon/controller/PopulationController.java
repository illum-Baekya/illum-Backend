package com.gdg.illum.gwanghyeon.controller;

import com.gdg.illum.gwanghyeon.service.PopulationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;

@RestController
@RequestMapping("/api/population")
public class PopulationController {

    private final PopulationService populationService;

    @Autowired
    public PopulationController(PopulationService populationService) {
        this.populationService = populationService;
    }

    @GetMapping
    public HashMap<String, Object> getResidentialPopulation(
            @RequestParam String year,
            @RequestParam String admCd) {
        return populationService.getResidentialPopulation(year, admCd);
    }
}
