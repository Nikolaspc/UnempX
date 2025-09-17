package com.myorg.unempx.service;

import java.util.List;

public class UnemploymentService {

    public double calculateAverageUnemployment(List<String[]> data) {
        double total = 0;
        int count = 0;
        for (String[] row : data) {
            try {
                double value = Double.parseDouble(row[1]); // Assume unemployment is in column 2
                total += value;
                count++;
            } catch (NumberFormatException | ArrayIndexOutOfBoundsException ignored) { }
        }
        return count > 0 ? total / count : 0.0;
    }
}