package com.ak.tripengine;

import com.ak.tripengine.model.Status;
import com.ak.tripengine.model.Tap;
import com.ak.tripengine.model.Trip;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

public class TripEngine {

    // The key is the PAN (Primary Account Number) of the card + Company ID +
    // Bus ID
    // The value is the Trip object
    // Acts as a temp place to hold trips. Completed will be moved to the
    // processedTrips
    private final HashMap<String, Trip> tripMap;

    // Holds the complete/cancelled trips
    private final List<Trip> processedTrips;

    public TripEngine() {
        tripMap = new HashMap<>();

        processedTrips = new ArrayList<>();
    }

    /**
     * Based on the tap, this will keep a memory of the same person tapping on
     * and off.
     *
     * @param tap current tap
     * @throws TripEngineException If any exception occurs calculating the trip
     *                             details
     */
    private void processTap(Tap tap) throws TripEngineException {
        Trip trip;
        if (tripMap.containsKey(tap.getKey())) {
            trip = tripMap.get(tap.getKey());
        } else {
            trip = new Trip();
            tripMap.put(tap.getKey(), trip);
        }
        trip.addTapDetails(tap);
        if ((trip.getFromStopId() != null) && (trip.getToStopId() != null)) {
            processedTrips.add(trip);
            tripMap.remove(tap.getKey());
        }
    }

    public List<Trip> getTrips() {
        return processedTrips;
    }

    /**
     * Takes the taps and processes them individually and then
     * merges the complete/cancelled trips and the incomplete one
     *
     * @param taps The list of taps
     * @throws TripEngineException If any charge calculation error occurs
     */
    public void processTaps(List<Tap> taps) throws TripEngineException {
        for (Tap tap : taps) {
            processTap(tap);
        }
        processedTrips.addAll(tripMap.values().stream().toList());
        List<Trip> trips = getTrips();
        if (trips != null) {
            trips.forEach(trip -> {

                if (trip.getChargeAmount() > 0) {
                    trip.setStatus(Status.COMPLETED);
                } else if (Objects.equals(trip.getFromStopId(),
                        trip.getToStopId())) {
                    trip.setStatus(Status.CANCELLED);
                } else {
                    trip.setStatus(Status.INCOMPLETE);
                }
            });
        }
    }
}
