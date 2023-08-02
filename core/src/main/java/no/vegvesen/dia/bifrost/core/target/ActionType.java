package no.vegvesen.dia.bifrost.core.target;

import com.fasterxml.jackson.annotation.JsonValue;

public enum ActionType {
    S3,
    KAFKA;

    private final String lowercase;

    ActionType() {
        this.lowercase = this.name().toLowerCase();
    }

    @JsonValue
    public String getLowercase() {
        return this.lowercase;
    }
}
