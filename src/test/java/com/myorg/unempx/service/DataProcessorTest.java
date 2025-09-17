package com.myorg.unempx.service;

import com.myorg.unempx.model.UnemploymentRecord;
import org.junit.jupiter.api.Test;
import java.util.List;
import java.util.Map;
import static org.junit.jupiter.api.Assertions.*;

class DataProcessorTest {

    @Test
    void minMaxIsCorrect() {
        var recs = List.of(
                new UnemploymentRecord(2020, List.of(5d,5d,5d,5d,5d,5d,5d,5d,5d,5d,5d,5d)), // avg=5
                new UnemploymentRecord(2021, List.of(4d,4d,4d,4d,4d,4d,4d,4d,4d,4d,4d,4d)), // avg=4
                new UnemploymentRecord(2022, List.of(6d,6d,6d,6d,6d,6d,6d,6d,6d,6d,6d,6d))  // avg=6
        );
        DataProcessor dp = new DataProcessor(recs);
        Map<String, Object> mm = dp.getMinMax();
        assertEquals(4.0, (Double) mm.get("minValue"));
        assertEquals(6.0, (Double) mm.get("maxValue"));
        assertEquals(2021, (Integer) mm.get("minYear"));
        assertEquals(2022, (Integer) mm.get("maxYear"));
    }

    @Test
    void getTopYearsReturnsCorrectOrder() {
        var recs = List.of(
                new UnemploymentRecord(2020, List.of(6d,6d,6d,6d,6d,6d,6d,6d,6d,6d,6d,6d)), // avg=6
                new UnemploymentRecord(2021, List.of(4d,4d,4d,4d,4d,4d,4d,4d,4d,4d,4d,4d)), // avg=4
                new UnemploymentRecord(2022, List.of(7d,7d,7d,7d,7d,7d,7d,7d,7d,7d,7d,7d))  // avg=7
        );
        DataProcessor dp = new DataProcessor(recs);
        var top = dp.getTopYears(2);
        assertEquals(2022, top.get(0).getYear());
        assertEquals(2020, top.get(1).getYear());
    }

    @Test
    void movingAverageWorks() {
        var recs = List.of(
                new UnemploymentRecord(2020, List.of(1d,2d,3d,4d,5d,6d,7d,8d,9d,10d,11d,12d)), // 12 months
                new UnemploymentRecord(2021, List.of(13d,14d,15d,16d,17d,18d,19d,20d,21d,22d,23d,24d)) // 12 months
        );
        DataProcessor dp = new DataProcessor(recs);
        var map = dp.getMovingAverageWithDates(3);
        // First average: (1+2+3)/3 = 2.0
        assertEquals(2.0, map.values().iterator().next());
        // The number of moving averages:
        assertEquals(22, map.size());
    }
}