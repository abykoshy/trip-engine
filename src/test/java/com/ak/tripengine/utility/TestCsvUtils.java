package com.ak.tripengine.utility;

import java.nio.file.Path;
import java.text.SimpleDateFormat;
import java.util.List;

import com.ak.tripengine.model.Status;
import org.junit.jupiter.api.Test;
import com.ak.tripengine.model.Tap;
import com.ak.tripengine.model.Trip;

import static org.assertj.core.api.Assertions.assertThat;

class TestCsvUtils {

    final SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");

    @Test
    void loadCsvToBeans() throws Exception {
        List<Tap> taps = CsvUtils.readTapsFromCsv("src/test/resources/taps.csv");
        assertThat(taps).hasSize(6);
        assertThat(taps.get(0).getDateTimeUTC()).isInstanceOf(java.util.Date.class);
    }

    @Test
    void writeBeansToCsv() throws Exception {
        List<Trip> trips = List.of(new Trip(simpleDateFormat.parse("2023-10-12 10:00:00"), simpleDateFormat.parse("2023-10-12 10:30:00"), 1800,
                "Stop1", "Stop2", 5.00f, "CompanyA", "Bus1", "1234567890", Status.COMPLETED));


        Path file = Path.of("src/test/resources/trips_out.csv");
        CsvUtils.writeTripsToCsv(trips, file.toString());

        assertThat(file).exists();
        assertThat(file).content().isNotEmpty();

    }
}
