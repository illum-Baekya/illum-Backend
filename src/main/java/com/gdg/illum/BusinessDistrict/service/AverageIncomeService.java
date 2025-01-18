package com.gdg.illum.BusinessDistrict.service;

import com.gdg.illum.BusinessDistrict.domain.DistrictInformation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.*;

@Slf4j
@Component
public class AverageIncomeService {

    private final Map<String, DistrictInformation> averageIncomes = new HashMap<>();

    public AverageIncomeService(@Value("${file.storage.path}") String rootFilePath) {

        String filePath = rootFilePath + "\\average_income.csv";

        try {
            File file = new File(filePath);
            BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(file), StandardCharsets.UTF_8));

            String line;
            while ((line = br.readLine())!=null) {
                if (line.contains("VRG_INCOME_PRIC")) {
                    continue;
                }
                String[] lineArr = line.split(",(?=([^\"]*\"[^\"]*\")*[^\"]*$)",-1);

                String name = stripDoubleQuotation(lineArr[1]);
                String code = stripDoubleQuotation(lineArr[2]);
                Integer averageIncome = Integer.parseInt(stripDoubleQuotation(lineArr[3]));
                averageIncomes.put(code, DistrictInformation.builder().
                        name(name)
                        .code(code)
                        .averageIncome(averageIncome)
                        .build()
                );
            }
        } catch (Exception e) {
            log.error(e.getMessage());
        }
    }

    public Integer getAverageIncomeByCode(String code) {
        if (!averageIncomes.containsKey(code)) {
            throw new RuntimeException("해당 시군구 코드의 데이터가 존재하지 않습니다.");
        }

        return averageIncomes.get(code).getAverageIncome();
    }

    public List<String> getEverySignguCd() {
        return new ArrayList<>(averageIncomes.keySet());
    }

    private String stripDoubleQuotation(String string) {
        return string.substring(1, string.length() - 1);
    }

    public List<DistrictInformation> getFilteredDistricts(String codePrefix, int minAverageIncome) {
        return averageIncomes.entrySet().stream()
                .filter(entry -> {
                    String code = entry.getKey();
                    Integer averageIncome = entry.getValue().getAverageIncome();

                    return code.substring(0, 2).equals(codePrefix) && averageIncome >= minAverageIncome;
                })
                .map(Map.Entry::getValue)
                .toList();
    }
}
