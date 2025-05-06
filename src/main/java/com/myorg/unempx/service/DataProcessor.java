package com.myorg.unempx.service;

import com.myorg.unempx.model.UnemploymentRecord;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Provides analysis operations over a list of UnemploymentRecord.
 */
public class DataProcessor {
    private final List<UnemploymentRecord> records;

    public DataProcessor(List<UnemploymentRecord> records) {
        this.records = new ArrayList<>(records);
    }

    public List<UnemploymentRecord> getRecords() {
        return Collections.unmodifiableList(records);
    }

    public Map<String, Object> getMinMax() {
        double min = records.stream()
                .mapToDouble(UnemploymentRecord::getAnnualAverage)
                .min().orElse(0.0);
        double max = records.stream()
                .mapToDouble(UnemploymentRecord::getAnnualAverage)
                .max().orElse(0.0);

        int minYear = records.stream()
                .filter(r -> r.getAnnualAverage() == min)
                .findFirst()
                .map(UnemploymentRecord::getYear)
                .orElse(0);

        int maxYear = records.stream()
                .filter(r -> r.getAnnualAverage() == max)
                .findFirst()
                .map(UnemploymentRecord::getYear)
                .orElse(0);

        Map<String, Object> mm = new HashMap<>();
        mm.put("minValue", min);
        mm.put("maxValue", max);
        mm.put("minYear", minYear);
        mm.put("maxYear", maxYear);
        return mm;
    }

    public Map<String, Double> getMovingAverageWithDates(int months) {
        Map<String, Double> result = new LinkedHashMap<>();
        List<Double> flat = records.stream()
                .flatMap(r -> r.getMonthlyRates().stream())
                .collect(Collectors.toList());

        for (int i = 0; i + months <= flat.size(); i++) {
            double avg = flat.subList(i, i + months)
                    .stream()
                    .mapToDouble(Double::doubleValue)
                    .average()
                    .orElse(0.0);

            int yearIndex = (i / 12) + 1;
            int monthIndex = (i % 12) + 1;
            String label = yearIndex + "/" + monthIndex;

            result.put(label, avg);
        }
        return result;
    }

    public List<UnemploymentRecord> getTopYears(int n) {
        return records.stream()
                .sorted(Comparator.comparingDouble(UnemploymentRecord::getAnnualAverage).reversed())
                .limit(n)
                .collect(Collectors.toList());
    }
}
