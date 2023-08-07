package no.vegvesen.dia.bifrost.core.services;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import no.vegvesen.dia.bifrost.core.target.ActionType;

public class S3Config {

    private final String accessKey;
    private final String secretKey;
    private final String url;
    private final S3ImplementationType implementation;


    private S3Config() {
        this.accessKey = null;
        this.secretKey = null;
        this.url = null;
        this.implementation = null;
    }

    @JsonCreator
    public S3Config(
            @JsonProperty("access-key") String accessKey,
            @JsonProperty("secret-key") String secretKey,
            @JsonProperty("url") String url,
            @JsonProperty("action") S3ImplementationType implementation) {
        this.accessKey = accessKey;
        this.secretKey = secretKey;
        this.url = url;
        this.implementation = implementation;
    }

    public static S3Config empty() {
        return new S3Config();
    }

    public String getAccessKey() {
        return accessKey;
    }

    public String getSecretKey() {
        return secretKey;
    }

    public String getUrl() {
        return url;
    }

    public S3ImplementationType getImplementation() {
        return this.implementation;
    }

}
