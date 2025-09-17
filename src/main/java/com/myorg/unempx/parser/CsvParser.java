package com.myorg.unempx.parser;

import com.opencsv.CSVReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

public class CsvParser {

    public List<String[]> parse(String csvFilePath) {
        List<String[]> rows = new ArrayList<>();
        try (CSVReader reader = new CSVReader(new FileReader(csvFilePath))) {
            String[] line;
            while ((line = reader.readNext()) != null) {
                rows.add(line);
            }
        } catch (Exception e) {
            // For now, just print the error.
            System.err.println("Error reading CSV: " + e.getMessage());
        }
        return rows;
    }
}