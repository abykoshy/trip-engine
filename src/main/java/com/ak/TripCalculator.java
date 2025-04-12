package com.ak;

import com.ak.tripengine.TripEngine;
import com.ak.tripengine.TripEngineException;
import com.ak.tripengine.utility.CsvUtils;
import lombok.extern.java.Log;

import java.io.IOException;

/**
 * Trips
 * This is a runner to test the trips engine aside from the test case.
 * author: ak
 * date: 2023/10/12
 */
@Log
public class TripCalculator {
    public static void main(String[] args) throws IOException,
            TripEngineException {
        if (args.length < 2) {
            System.out.println("Missing arguments : <source csv containing " +
                    "taps> <outfile to store the trips>");
        } else {
            TripEngine tripEngine = new TripEngine();
            tripEngine.processTaps(CsvUtils.readTapsFromCsv(args[0]));
            CsvUtils.writeTripsToCsv(tripEngine.getTrips(), args[1]);
            log.info(String.format("Created the output file : %s", args[1]));
        }
    }
}
