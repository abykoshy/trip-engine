package com.ak.tripengine;

import lombok.extern.java.Log;

@Log
public class TripEngineException extends Exception {
    public TripEngineException(String message) {
        super(message);
        log.severe(message);
    }
}
