package no.vegvesen.dia.bifrost.resources;

import io.minio.MinioClient;
import no.vegvesen.dia.bifrost.core.config.Config;
import no.vegvesen.dia.bifrost.core.config.ConfigLoader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.FileNotFoundException;
import java.util.Objects;

public class MinioClientWrapper {
    private static final Logger log = LoggerFactory.getLogger(MinioClientWrapper.class);
    protected final MinioClient minioClient;

    protected MinioClientWrapper() {
        String accessKey = System.getenv("S3_ACCESS_KEY");
        String secretKey = System.getenv("S3_SECRET_KEY");
        String s3Url = "https://s3-oslo.ostore.vegvesen.no";

        if (Objects.isNull(accessKey) || Objects.isNull(secretKey)) {
            try {
                Config config = ConfigLoader.fromEnvironment("APP_CONFIG");
                accessKey = config.getAccessKey();
                secretKey = config.getSecretKey();
                s3Url = config.getS3Url();
            } catch (FileNotFoundException e) {
                throw new RuntimeException(e);
            }
        }

        log.debug("do we have data?:" + accessKey.charAt(0) + secretKey.charAt(1));
        minioClient = new MinioClient.Builder()
                .credentials(accessKey, secretKey)
                .endpoint(s3Url)
                .build();
    }
}
