package no.vegvesen.dia.bifrost.core.config;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import no.vegvesen.dia.bifrost.core.services.S3Config;
import no.vegvesen.dia.bifrost.core.target.TargetConfig;

import java.util.Collections;
import java.util.List;

public class Config {

    @JsonProperty("s3")
    private final S3Config s3Config;
    @JsonProperty("targets")
    private final List<TargetConfig> targetConfigs;

    private Config() {
        this.s3Config = S3Config.empty();
        this.targetConfigs = Collections.EMPTY_LIST;
    }

    @JsonCreator
    public Config(@JsonProperty("s3") S3Config s3,
                  @JsonProperty("targets") List<TargetConfig> targetConfigs) {
        this.s3Config = s3;
        this.targetConfigs = targetConfigs;
    }

    public static Config empty() {
        return new Config();
    }

    public S3Config getS3Config() {
        return this.s3Config;
    }

    public List<TargetConfig> getTargetConfigs() {
        return targetConfigs;
    }
}
