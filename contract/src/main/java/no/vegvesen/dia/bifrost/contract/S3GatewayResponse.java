package no.vegvesen.dia.bifrost.contract;

import java.util.List;

public class S3GatewayResponse {
    private final List<Bucket> buckets;

    S3GatewayResponse(final List<Bucket> buckets) {
        this.buckets = buckets;
    }

    public static S3GatewayResponseBuilder builder() {
        return new S3GatewayResponseBuilder();
    }

    public List<Bucket> getBuckets() {
        return this.buckets;
    }

    public static class S3GatewayResponseBuilder {
        private List<Bucket> buckets;

        S3GatewayResponseBuilder() {
        }

        public S3GatewayResponseBuilder buckets(final List<Bucket> buckets) {
            this.buckets = buckets;
            return this;
        }

        public S3GatewayResponse build() {
            return new S3GatewayResponse(this.buckets);
        }

        public String toString() {
            return "S3GatewayResponse.S3GatewayResponseBuilder(buckets=" + this.buckets + ")";
        }
    }
}
