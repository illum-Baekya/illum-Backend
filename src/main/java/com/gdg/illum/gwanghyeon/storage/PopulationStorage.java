package com.gdg.illum.gwanghyeon.storage;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

public class PopulationStorage {

    private final Map<String, PopulationRecord> populationMap = new HashMap<>();

    public PopulationStorage(String filePath) {
        try (BufferedReader br = new BufferedReader(new FileReader(new File(filePath), StandardCharsets.UTF_8))) {
            String line;
            boolean firstLine = true;
            while ((line = br.readLine()) != null) {
                if (firstLine) {
                    firstLine = false; // Skip header
                    continue;
                }
                String[] tokens = line.split(",");
                String year = tokens[0];
                String code = tokens[1];
                String name = tokens[2];
                String population = tokens[3]; // Parse as String

                populationMap.put(code, new PopulationRecord(year, code, name, population));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public Map<String, PopulationRecord> getPopulationMap() {
        return populationMap;
    }

    public static class PopulationRecord {
        private final String year;
        private final String code;
        private final String name;
        private final String population;

        public PopulationRecord(String year, String code, String name, String population) {
            this.year = year;
            this.code = code;
            this.name = name;
            this.population = population;
        }

        public String getYear() {
            return year;
        }

        public String getCode() {
            return code;
        }

        public String getName() {
            return name;
        }

        public String getPopulation() {
            return population;
        }
    }
}
