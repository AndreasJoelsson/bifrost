package no.vegvesen.dia.bifrost.core.config;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import no.vegvesen.dia.bifrost.core.services.S3Config;

public class Config {

    @JsonProperty("s3")
    private final S3Config s3Config;

    public static Config empty() {
        return new Config();
    }

    private Config() {
        this.s3Config = S3Config.empty();
    }

    @JsonCreator
    public Config(@JsonProperty("s3") S3Config s3) {
        this.s3Config = s3;
    }

    public S3Config getS3Config() {
        return this.s3Config;
    }

}
