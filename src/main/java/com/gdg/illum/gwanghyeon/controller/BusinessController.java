package com.gdg.illum.gwanghyeon.controller;

import com.gdg.illum.gwanghyeon.service.BusinessService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;

@RestController
@RequestMapping("/api/businesses")
public class BusinessController {

    private final BusinessService businessService;

    @Autowired
    public BusinessController(BusinessService businessService) {
        this.businessService = businessService;
    }

    @GetMapping
    public HashMap<String, Object> getNearbyBusinesses(
            @RequestParam String year,
            @RequestParam String admCd) {
        return businessService.getNearbyBusinesses(year, admCd);
    }
}
