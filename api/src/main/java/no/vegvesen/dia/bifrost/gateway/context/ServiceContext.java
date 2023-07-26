package no.vegvesen.dia.bifrost.s3gateway.context;

import io.minio.MinioClient;
import no.vegvesen.dia.bifrost.core.context.ServiceContextIF;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Optional;

public enum ServiceContext implements ServiceContextIF {
    INSTANCE;

    private final Logger LOGGER = LogManager.getLogger(ServiceContext.class);

    // https://www.vegvesen.no/wiki/pages/viewpage.action?pageId=91393085
    // Big nono for production. get it from vault.
    private final static String ENDPOINT = "s3-oslo.ostore.utv.vegvesen.no";
    private final static String ACCESS_KEY = "00f533a8075ef6e791e8";
    private final static String ACCESS_TOKEN = "IAHXftiqrvEfUQsHleF+g6m3o5ZvVgb/gQ1FnJcH";

    private final MinioClient minioClient;

    ServiceContext() {
        try {
            final String S3_ENDPOINT = Optional.ofNullable(System.getenv("S3_ENDPOINT")).orElse(ENDPOINT);
            final String S3_ACCESS_KEY = Optional.ofNullable(System.getenv("S3_ACCESS_KEY")).orElse(ACCESS_KEY);
            final String S3_ACCESS_TOKEN = Optional.ofNullable(System.getenv("S3_ACCESS_TOKEN")).orElse(ACCESS_TOKEN);
            this.minioClient = MinioClient.builder()
                    .endpoint(S3_ENDPOINT)
                    .credentials(S3_ACCESS_KEY, S3_ACCESS_TOKEN)
                    .build();
        } catch (Exception e) {
            LOGGER.error("Exception: " + e.getMessage());
            throw new RuntimeException(e.getMessage(), e);
        }
    }

    @Override
    public MinioClient s3client() {
        return this.minioClient;
    }

    @Override
    public String toString() {
        return "ServiceContext{" +
                "minioClient=" + minioClient +
                '}';
    }
}
