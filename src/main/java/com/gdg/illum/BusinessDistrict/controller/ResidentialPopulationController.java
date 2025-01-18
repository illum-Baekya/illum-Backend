package com.gdg.illum.BusinessDistrict.controller;

import com.gdg.illum.BusinessDistrict.service.ResidentialPopulationIncomeMerger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/residential-population")
public class ResidentialPopulationController {

    private final ResidentialPopulationIncomeMerger residentialPopulationIncomeMerger;

    @Autowired
    public ResidentialPopulationController(ResidentialPopulationIncomeMerger residentialPopulationIncomeMerger) {
        this.residentialPopulationIncomeMerger = residentialPopulationIncomeMerger;
    }

    /**
     * 병합된 인구 및 소득 데이터를 반환
     *
     * @param populationFilePath 인구 데이터 파일 경로
     * @param incomeFilePath     소득 데이터 파일 경로
     * @return 병합된 인구 및 소득 정보 리스트
     */
    @GetMapping("/merge")
    public List<ResidentialPopulationIncomeMerger.MergedRecord> getMergedPopulationIncome(
            @RequestParam String populationFilePath,
            @RequestParam String incomeFilePath
    ) {
        // PopulationIncomeMerger를 통해 병합된 데이터를 반환
        return residentialPopulationIncomeMerger.mergeData(populationFilePath, incomeFilePath);
    }
}
