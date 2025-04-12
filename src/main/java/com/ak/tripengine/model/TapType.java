package com.ak.tripengine.model;

public enum TapType {
    ON(" ON"),
    OFF(" OFF");

    private final String type;

    TapType(String type) {
        this.type = type.trim();
    }

    @Override
    public String toString() {
        return type;
    }
}
