package com.ak.tripengine;


import com.ak.tripengine.model.Status;
import com.ak.tripengine.model.Tap;
import com.ak.tripengine.model.TapType;
import com.ak.tripengine.model.Trip;
import com.ak.tripengine.utility.CsvUtils;
import org.junit.jupiter.api.Test;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class TestTripEngine {

    final SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MM-yyyy " +
            "HH:mm:ss");

    @Test
    public void testSingleTrip() throws Exception {
        Tap tap1 = new Tap(1,
                simpleDateFormat.parse("2023-10-12 10:00:00"),
                TapType.ON, "Stop1", "Company1", "Bus1",
                "1234567890123456");

        Tap tap2 = new Tap(2,
                simpleDateFormat.parse("2023-10-12 10:02:00"),
                TapType.OFF, "Stop2", "Company1", "Bus1",
                "1234567890123456");
        TripEngine tripEngine = new TripEngine();
        tripEngine.processTaps(List.of(tap1, tap2));

        List<Trip> trips = tripEngine.getTrips();
        // Validate the trips
        assertThat(trips).hasSize(1);
        Trip trip = trips.get(0);
        assertThat(trip.getStarted()).isEqualTo(tap1.getDateTimeUTC());
        assertThat(trip.getFinished()).isEqualTo(tap2.getDateTimeUTC());
        assertThat(trip.getFromStopId()).isEqualTo(tap1.getStopId());
        assertThat(trip.getToStopId()).isEqualTo(tap2.getStopId());
        assertThat(trip.getCompanyId()).isEqualTo(tap1.getCompanyId());
        assertThat(trip.getBusId()).isEqualTo(tap1.getBusId());
        assertThat(trip.getPan()).isEqualTo(tap1.getPan());
        assertThat(trip.getDurationSecs()).isGreaterThan(0);
        assertThat(trip.getChargeAmount()).isGreaterThan(0);
    }

    @Test
    public void testChargeNotFoundTrip() throws ParseException {
        Tap tap1 = new Tap(1,
                simpleDateFormat.parse("2023-10-12 10:00:00"),
                TapType.ON, "Stop1", "Company1", "Bus1",
                "1234567890123456");

        Tap tap2 = new Tap(2,
                simpleDateFormat.parse("2023-10-12 10:02:00"),
                TapType.OFF, "Stop5", "Company1", "Bus1",
                "1234567890123456");
        TripEngine tripEngine = new TripEngine();
        try {
            tripEngine.processTaps(List.of(tap1, tap2));
            assert false;
        } catch (TripEngineException e) {
            // it is a pass
        }

    }

    @Test
    public void testOnOffSameStopTrip() throws Exception {
        Tap tap1 = new Tap(1,
                simpleDateFormat.parse("2023-10-12 10:00:00"),
                TapType.ON, "Stop1", "Company1", "Bus1",
                "1234567890123456");

        Tap tap2 = new Tap(2,
                simpleDateFormat.parse("2023-10-12 10:02:00"),
                TapType.OFF, "Stop1", "Company1", "Bus1",
                "1234567890123456");
        TripEngine tripEngine = new TripEngine();
        tripEngine.processTaps(List.of(tap1, tap2));

        List<Trip> trips = tripEngine.getTrips();
        // Validate the trips
        assert trips.size() == 1;
        Trip trip = trips.get(0);
        assertThat(trip.getStatus()).isEqualByComparingTo(Status.CANCELLED);
    }

    @Test
    public void testOnIncompleteTrip() throws Exception {
        Tap tap1 = new Tap(1,
                simpleDateFormat.parse("2023-10-12 10:00:00"),
                TapType.ON, "Stop1", "Company1", "Bus1",
                "1234567890123456");

        TripEngine tripEngine = new TripEngine();
        tripEngine.processTaps(List.of(tap1));

        List<Trip> trips = tripEngine.getTrips();
        // Validate the trips
        assertThat(trips).hasSize(1);
        Trip trip = trips.get(0);
        assertThat(trip.getStatus()).isEqualByComparingTo(Status.INCOMPLETE);
    }

    @Test
    public void testOffIncompleteTrip() throws Exception {
        Tap tap1 = new Tap(1,
                simpleDateFormat.parse("2023-10-12 10:00:00"),
                TapType.OFF, "Stop1", "Company1", "Bus1",
                "1234567890123456");

        TripEngine tripEngine = new TripEngine();
        tripEngine.processTaps(List.of(tap1));

        List<Trip> trips = tripEngine.getTrips();
        // Validate the trips
        assertThat(trips).hasSize(1);
        Trip trip = trips.get(0);
        assertThat(trip.getStatus()).isEqualByComparingTo(Status.INCOMPLETE);
    }

    @Test
    void processTapsFromCsv() throws Exception {

        TripEngine tripEngine = new TripEngine();
        List<Tap> taps = CsvUtils.readTapsFromCsv("src/test/resources/taps" +
                ".csv");
        tripEngine.processTaps(taps);
        List<Trip> trips = tripEngine.getTrips();
        CsvUtils.writeTripsToCsv(trips, "target/trips-out.csv");
        assertThat(trips).hasSize(4);
    }

}
