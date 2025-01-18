package com.gdg.illum.gwanghyeon.controller;

import com.gdg.illum.gwanghyeon.service.BusinessService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;

@RestController
@RequestMapping("/api/businesses")
public class BusinessController {

    @Autowired
    private BusinessService businessService;

    // 사업체 통계 데이터 API
    @GetMapping
    public HashMap<String, Object> getNearbyBusinesses(
            @RequestParam String accessToken,
            @RequestParam String year,
            @RequestParam String admCd) {
        return businessService.getNearbyBusinesses(accessToken, year, admCd);
    }
}
