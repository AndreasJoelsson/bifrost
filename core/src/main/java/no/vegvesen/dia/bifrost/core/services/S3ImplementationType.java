package no.vegvesen.dia.bifrost.core.services;

import com.fasterxml.jackson.annotation.JsonValue;

public enum S3ImplementationType {
    MINIO,
    LOCAL_FILE_SYSTEM;

    private final String lowercase;

    S3ImplementationType() {
        this.lowercase = this.name().toLowerCase();
    }

    @JsonValue
    public String getLowercase() {
        return this.lowercase;
    }

}
