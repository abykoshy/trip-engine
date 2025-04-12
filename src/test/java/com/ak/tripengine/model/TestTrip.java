package com.ak.tripengine.model;


import static org.assertj.core.api.Assertions.assertThat;
import java.util.Date;
import org.junit.jupiter.api.Test;
import com.ak.tripengine.TripEngineException;

public class TestTrip {

    @Test
    public void testTripChargeOneStop() throws TripEngineException {
        Trip trip = new Trip();
        trip.setStarted(new Date());
        trip.setFinished(new Date());
        trip.setFromStopId("Stop1");
        trip.setToStopId("Stop2");
        trip.setCompanyId("Company1");
        trip.setBusId("Bus1");
        trip.setPan("1234567890123456");

        trip.calculateTripDetails();
        assertThat(trip.getChargeAmount()).isEqualTo(3.25f);
    }

    @Test
    public void testTripChargeTwoStops() throws TripEngineException {
        Trip trip = new Trip();
        trip.setStarted(new Date());
        trip.setFinished(new Date());
        trip.setFromStopId("Stop1");
        trip.setToStopId("Stop3");
        trip.setCompanyId("Company1");
        trip.setBusId("Bus1");
        trip.setPan("1234567890123456");

        trip.calculateTripDetails();
        assertThat(trip.getChargeAmount()).isEqualTo(7.3f);
    }

    @Test
    public void testTripChargeReverse() throws TripEngineException {
        Trip trip = new Trip();
        trip.setStarted(new Date());
        trip.setFinished(new Date());
        trip.setFromStopId("Stop2");
        trip.setToStopId("Stop1");
        trip.setCompanyId("Company1");
        trip.setBusId("Bus1");
        trip.setPan("1234567890123456");

        trip.calculateTripDetails();
        assertThat(trip.getChargeAmount()).isEqualTo(3.25f);
    }
}