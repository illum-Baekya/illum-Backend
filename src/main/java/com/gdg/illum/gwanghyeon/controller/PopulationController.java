package com.gdg.illum.gwanghyeon.controller;

import com.gdg.illum.gwanghyeon.service.PopulationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;

@RestController
@RequestMapping("/api/population")
public class PopulationController {

    // 의존성 주입
    @Autowired
    private PopulationService populationService;

    // 주거인구 데이터 API
    @GetMapping
    public HashMap<String, Object> getResidentialPopulation(
            @RequestParam String accessToken,
            @RequestParam String year,
            @RequestParam String admCd) {
        return populationService.getResidentialPopulation(accessToken, year, admCd);
    }
}
