package com.myorg.unempx.model;

import java.util.List;

/**
 * Represents a single year's monthly unemployment rates.
 */
public class UnemploymentRecord {
    private final int year;
    private final List<Double> monthlyRates;

    /**
     * Constructs a new record. Validates that year is positive and exactly 12 rates are provided,
     * each between 0.0 and 100.0.
     * @param year calendar year of the record (must be > 0).
     * @param monthlyRates list of 12 monthly unemployment rates between 0 and 100.
     * @throws IllegalArgumentException if validation fails.
     */
    public UnemploymentRecord(int year, List<Double> monthlyRates) {
        if (year <= 0) {
            throw new IllegalArgumentException("Year must be positive: " + year);
        }
        if (monthlyRates == null || monthlyRates.size() != 12) {
            throw new IllegalArgumentException("Exactly 12 monthly rates are required");
        }
        for (Double rate : monthlyRates) {
            if (rate < 0.0 || rate > 100.0) {
                throw new IllegalArgumentException("Rate out of range 0-100: " + rate);
            }
        }
        this.year = year;
        this.monthlyRates = List.copyOf(monthlyRates);
    }

    /**
     * Returns the year of this record.
     * @return year.
     */
    public int getYear() {
        return year;
    }

    /**
     * Returns an immutable list of monthly rates.
     * @return list of 12 rates.
     */
    public List<Double> getMonthlyRates() {
        return monthlyRates;
    }

    /**
     * Calculates the annual average unemployment rate.
     * @return average of monthly rates.
     */
    public double getAnnualAverage() {
        return monthlyRates.stream()
                .mapToDouble(Double::doubleValue)
                .average()
                .orElse(0.0);
    }
}
