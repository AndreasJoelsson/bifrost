package no.vegvesen.dia.bifrost.core.config;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Config {
    @JsonProperty("accessKey")
    private String accessKey;

    @JsonProperty("secretKey")
    private String secretKey;
    @JsonProperty("s3Url")
    private String s3Url;

    public static Config empty() {
        return new Config();
    }

    public String getAccessKey() {
        return accessKey;
    }

    public void setAccessKey(String accessKey) {
        this.accessKey = accessKey;
    }

    public String getSecretKey() {
        return secretKey;
    }

    public void setSecretKey(String secretKey) {
        this.secretKey = secretKey;
    }

    public String getS3Url() {
        return s3Url;
    }

    public void setS3Url(String s3Url) {
        this.s3Url = s3Url;
    }

}
