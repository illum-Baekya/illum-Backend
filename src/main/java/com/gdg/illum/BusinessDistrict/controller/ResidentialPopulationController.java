package com.gdg.illum.BusinessDistrict.controller;

import com.gdg.illum.BusinessDistrict.service.ResidentialPopulationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/residential-population")
public class ResidentialPopulationController {

    private final ResidentialPopulationService residentialPopulationService;

    @Autowired
    public ResidentialPopulationController(ResidentialPopulationService residentialPopulationService) {
        this.residentialPopulationService = residentialPopulationService;
    }

    /**
     * 병합된 인구 및 소득 데이터를 반환 (최소 인구 필터링 적용)
     *
     * @param minPopulation 최소 인구 수 (필터링)
     * @return 병합된 인구 및 소득 정보 리스트
     */
    @GetMapping("/merge")
    public List<ResidentialPopulationService.MergedRecord> getMergedPopulationIncome(
            @RequestParam(defaultValue = "0") int minPopulation
    ) {
        // 최소 인구 수 이상의 지역만 필터링
        return residentialPopulationService.mergeDataWithMinPopulation(minPopulation);
    }
}
