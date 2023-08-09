package no.vegvesen.dia.bifrost.contract.pojo;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.minio.ObjectWriteResponse;

import java.io.Serializable;
import java.util.Objects;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class MinioObjectWriteResponse implements Serializable {

    @JsonProperty("etag")
    private final String etag;
    @JsonProperty("versionId")
    private final String versionId;
    @JsonProperty("bucket")
    private final String bucket;
    @JsonProperty("object")
    private final String object;
    @JsonProperty("region")
    private final String region;
    @JsonProperty("headers")
    private final MinioHeaders headers;

    public MinioObjectWriteResponse() {
        etag = null;
        versionId = null;
        bucket = null;
        object = null;
        region = null;
        headers = new MinioHeaders();
    }

    public MinioObjectWriteResponse(ObjectWriteResponse response) {
        etag = response.etag();
        versionId = response.versionId();
        bucket = response.bucket();
        object = response.object();
        region = response.region();
        headers = new MinioHeaders(response.headers());
    }

    public String getEtag() {
        return etag;
    }

    public String getVersionId() {
        return versionId;
    }

    public String getBucket() {
        return bucket;
    }

    public String getObject() {
        return object;
    }

    public String getRegion() {
        return region;
    }

    public MinioHeaders getHeaders() {
        return headers;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        MinioObjectWriteResponse that = (MinioObjectWriteResponse) o;

        if (!Objects.equals(etag, that.etag)) return false;
        if (!Objects.equals(versionId, that.versionId)) return false;
        if (!Objects.equals(bucket, that.bucket)) return false;
        if (!Objects.equals(object, that.object)) return false;
        if (!Objects.equals(region, that.region)) return false;
        return Objects.equals(headers, that.headers);
    }

    @Override
    public int hashCode() {
        int result = etag != null ? etag.hashCode() : 0;
        result = 31 * result + (versionId != null ? versionId.hashCode() : 0);
        result = 31 * result + (bucket != null ? bucket.hashCode() : 0);
        result = 31 * result + (object != null ? object.hashCode() : 0);
        result = 31 * result + (region != null ? region.hashCode() : 0);
        result = 31 * result + (headers != null ? headers.hashCode() : 0);
        return result;
    }
}
