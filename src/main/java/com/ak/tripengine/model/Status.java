package com.ak.tripengine.model;

public enum Status {
    COMPLETED("COMPLETED"),
    INCOMPLETE("INCOMPLETE"),
    CANCELLED("CANCELLED");

    private final String status;

    Status(String status) {
        this.status = status.trim();
    }

    @Override
    public String toString() {
        return status;
    }
}
