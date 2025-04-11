package com.ak.tripengine.utility;

import java.util.List;
import org.junit.jupiter.api.Test;
import com.ak.tripengine.model.Tap;

class TestCsvUtils {

    @Test
    void loadCsvToBeans() throws Exception {
        List<Tap> taps = CsvUtils.readTapsFromCsv("src/test/resources/taps.csv");
        assert taps.size() == 2;
        assert taps.get(0).getDateTimeUTC() instanceof java.util.Date;
    }
}
