package com.gdg.illum.BusinessDistrict.controller;

import com.gdg.illum.BusinessDistrict.domain.DistrictResidentialPopulationInformation;
import com.gdg.illum.BusinessDistrict.service.ResidentialPopulationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/residential-population")
public class ResidentialPopulationController {

    private final ResidentialPopulationService residentialPopulationService;

    @Autowired
    public ResidentialPopulationController(ResidentialPopulationService residentialPopulationService) {
        this.residentialPopulationService = residentialPopulationService;
    }

    @GetMapping("/filter/merge")
    public List<DistrictResidentialPopulationInformation> getMergedPopulationIncome(
            @RequestParam(defaultValue = "0") int minPopulation
    ) {
        // 1) 내부 계산용 MergedRecord 리스트
        List<ResidentialPopulationService.MergedRecord> mergedRecords =
                residentialPopulationService.mergeDataWithMinPopulation(minPopulation);

        // 2) DTO 리스트로 변환 (averageIncomePrice는 사용하지 않을 경우 빼 버림)
        return mergedRecords.stream()
                .map(m -> DistrictResidentialPopulationInformation.builder()
                        .year(m.getYear())
                        .regionCode(m.getSignguCd())
                        .regionName(m.getSignguName())
                        .totalPopulation(m.getTotalPopulation())
                        // .averageIncome(m.getAverageIncomePrice())  // <-- 여기서 안 넣으면 됨
                        .build()
                )
                .collect(Collectors.toList());
    }

    /**
     * 특정 동 코드(signguCd)의 총 인구수를 반환
     * => 기존 "/total-population" → 변경: "/filter/total-population"
     */
    @GetMapping("/filter/total-population")
    public int getTotalPopulation(@RequestParam String signguCd) {
        return residentialPopulationService.getTotalPopulationByCode(signguCd);
    }

}
