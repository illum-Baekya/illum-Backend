package com.gdg.illum.jun.income;

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
public class AverageIncomeStorage {

    private final Map<String, Integer> averageIncomes = new HashMap<>();

    public AverageIncomeStorage(@Value("${file.storage.path}") String rootFilePath) {

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

                String key = stripDoubleQuotation(lineArr[2]);
                Integer value = Integer.parseInt(stripDoubleQuotation(lineArr[3]));
                averageIncomes.put(key, value);
            }
        } catch (Exception e) {
            log.error(e.getMessage());
        }
    }

    public Integer getAverageIncomeByCode(String code) {
        if (!averageIncomes.containsKey(code)) {
            throw new RuntimeException("해당 시군구 코드의 데이터가 존재하지 않습니다.");
        }

        return averageIncomes.get(code);
    }

    public List<String> getEverySignguCd() {
        return new ArrayList<>(averageIncomes.keySet());
    }

    /**
     * 테스트용 메서드
     */
    private void testPrint() {
        for (Map.Entry<String, Integer> entry : averageIncomes.entrySet()) {

            System.out.println("key = " + entry.getKey() + ", value = " + entry.getValue());
        }
    }

    private String stripDoubleQuotation(String string) {
        return string.substring(1, string.length() - 1);
    }
}
