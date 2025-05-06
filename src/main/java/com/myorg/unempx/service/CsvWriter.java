package com.myorg.unempx.service;

import com.myorg.unempx.model.UnemploymentRecord;
import com.myorg.unempx.util.Config;
import com.opencsv.CSVWriter;

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

/**
 * Writes unemployment records to the external CSV file.
 */
public class CsvWriter {

    /**
     * Overwrites the data file with the provided records.
     * @param records list of records to write.
     * @throws IOException on write errors.
     */
    public void writeData(List<UnemploymentRecord> records) throws IOException {
        Path dataPath = Config.getDataFilePath();
        try (CSVWriter writer = new CSVWriter(
                new OutputStreamWriter(Files.newOutputStream(dataPath), StandardCharsets.UTF_8))) {
            String[] header = {
                    "Year","Jan","Feb","Mar","Apr","May","Jun",
                    "Jul","Aug","Sep","Oct","Nov","Dec"
            };
            writer.writeNext(header);

            for (UnemploymentRecord rec : records) {
                String[] line = new String[13];
                line[0] = String.valueOf(rec.getYear());
                List<Double> rates = rec.getMonthlyRates();
                for (int i = 0; i < 12; i++) {
                    line[i + 1] = String.format("%.1f", rates.get(i)).replace('.', ',');
                }
                writer.writeNext(line);
            }
        }
    }
}
