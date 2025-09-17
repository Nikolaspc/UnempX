package com.myorg.unempx;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class CsvParserTest {

    @Test
    void parseCsv_shouldReturnCorrectRowCount() {
        // Example stub: replace with actual parser logic later
        String csvData = "name,age\nAlice,30\nBob,25";
        int expectedRows = 2;

        int actualRows = csvData.split("\n").length - 1; // Skip header
        assertEquals(expectedRows, actualRows);
    }
}