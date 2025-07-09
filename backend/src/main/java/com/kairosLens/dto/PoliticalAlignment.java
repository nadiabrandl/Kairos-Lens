package com.kairosLens.dto;

import com.fasterxml.jackson.annotation.JsonValue;

public enum PoliticalAlignment {
    LEFT("Left"),
    CENTER_LEFT("Center-Left"),
    CENTER("Center"),
    CENTER_RIGHT("Center-Right"),
    RIGHT("Right");

    private final String label;

    PoliticalAlignment(String label) {
        this.label = label;
    }

    @JsonValue
    public String getLabel() {
        return label;
    }
}

