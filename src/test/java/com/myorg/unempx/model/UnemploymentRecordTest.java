package com.myorg.unempx.model;

import org.junit.jupiter.api.Test;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

class UnemploymentRecordTest {

    @Test
    void validRecordIsCreated() {
        UnemploymentRecord rec = new UnemploymentRecord(2020, List.of(5.0, 6.0, 7.0, 8.0, 5.5, 6.1, 7.2, 5.6, 6.3, 7.0, 6.8, 5.9));
        assertEquals(2020, rec.getYear());
        assertEquals(12, rec.getMonthlyRates().size());
        assertEquals(6.366666666666667, rec.getAnnualAverage(), 0.01);
    }

    @Test
    void throwsIfYearIsZeroOrNegative() {
        Exception ex = assertThrows(IllegalArgumentException.class, () ->
                new UnemploymentRecord(0, List.of(5.0, 6.0, 7.0, 8.0, 5.5, 6.1, 7.2, 5.6, 6.3, 7.0, 6.8, 5.9))
        );
        assertTrue(ex.getMessage().contains("Year must be positive"));
    }

    @Test
    void throwsIfMonthlyRatesIsNull() {
        Exception ex = assertThrows(IllegalArgumentException.class, () ->
                new UnemploymentRecord(2020, null)
        );
        assertTrue(ex.getMessage().contains("Exactly 12 monthly rates are required"));
    }

    @Test
    void throwsIfMonthlyRatesIsNot12() {
        Exception ex = assertThrows(IllegalArgumentException.class, () ->
                new UnemploymentRecord(2020, List.of(5.0, 6.0))
        );
        assertTrue(ex.getMessage().contains("Exactly 12 monthly rates are required"));
    }

    @Test
    void throwsIfMonthlyRateOutOfRange() {
        Exception ex = assertThrows(IllegalArgumentException.class, () ->
                new UnemploymentRecord(2020, List.of(5.0, 6.0, 7.0, 8.0, 5.5, 6.1, 101.0, 5.6, 6.3, 7.0, 6.8, 5.9))
        );
        assertTrue(ex.getMessage().contains("Rate out of range"));
    }
}