package no.vegvesen.dia.bifrost.contract.pojo;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import okhttp3.Headers;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class MinioHeaders {

    @JsonProperty("headers")
    private final Map<String, List<String>> headers;

    public MinioHeaders() {
        this.headers = new HashMap<>();
    }

    public MinioHeaders(Headers headers) {
        this.headers = headers.toMultimap();
    }

    public MinioHeaders(Map<String, List<String>> headers) {
        this.headers = headers;
    }

    public Map<String, List<String>> getHeaders() {
        return headers;
    }
}
