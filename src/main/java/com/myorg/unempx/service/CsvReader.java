package com.myorg.unempx.service;

import com.myorg.unempx.model.UnemploymentRecord;
import com.myorg.unempx.util.Config;
import com.opencsv.bean.CsvBindByName;
import com.opencsv.bean.CsvToBeanBuilder;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Reads unemployment records from CSV, filtering out invalid years.
 */
public class CsvReader {

    /**
     * Loads data from the external CSV file, or if missing, from classpath resource.
     * @return list of valid UnemploymentRecord instances.
     * @throws Exception on IO or parse errors.
     */
    public List<UnemploymentRecord> readData() throws Exception {
        Path dataPath = Config.getDataFilePath();
        InputStream input = Files.exists(dataPath)
                ? Files.newInputStream(dataPath)
                : getClass().getClassLoader().getResourceAsStream("unemployment_data.csv");
        if (input == null) {
            throw new IOException("Data file not found in resource or " + dataPath);
        }

        return new CsvToBeanBuilder<CsvReader.CsvRecord>(new InputStreamReader(input, StandardCharsets.UTF_8))
                .withType(CsvRecord.class)
                .build()
                .parse()
                .stream()
                .filter(CsvRecord::isValid)
                .map(r -> new UnemploymentRecord(r.getYear(), r.getMonthlyRates()))
                .collect(Collectors.toList());
    }

    /**
     * Internal bean for CSV parsing.
     */
    public static class CsvRecord {
        @CsvBindByName(column = "Year") private int year;
        @CsvBindByName(column = "Jan") private String jan;
        @CsvBindByName(column = "Feb") private String feb;
        @CsvBindByName(column = "Mar") private String mar;
        @CsvBindByName(column = "Apr") private String apr;
        @CsvBindByName(column = "May") private String may;
        @CsvBindByName(column = "Jun") private String jun;
        @CsvBindByName(column = "Jul") private String jul;
        @CsvBindByName(column = "Aug") private String aug;
        @CsvBindByName(column = "Sep") private String sep;
        @CsvBindByName(column = "Oct") private String oct;
        @CsvBindByName(column = "Nov") private String nov;
        @CsvBindByName(column = "Dec") private String dec;

        /** Filters out any records beyond year 2024. */
        public boolean isValid() {
            return year <= 2024;
        }

        public int getYear() {
            return year;
        }

        public List<Double> getMonthlyRates() {
            return List.of(
                    parseRate(jan), parseRate(feb), parseRate(mar),
                    parseRate(apr), parseRate(may), parseRate(jun),
                    parseRate(jul), parseRate(aug), parseRate(sep),
                    parseRate(oct), parseRate(nov), parseRate(dec)
            );
        }

        private Double parseRate(String rate) {
            if (rate == null || rate.isEmpty()) return 0.0;
            try {
                return Double.parseDouble(rate.replace(",", "."));
            } catch (NumberFormatException e) {
                return 0.0;
            }
        }
    }
}
