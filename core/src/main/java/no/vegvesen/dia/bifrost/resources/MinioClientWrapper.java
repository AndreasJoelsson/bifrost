package no.vegvesen.dia.bifrost.resources;

import io.minio.MinioClient;
import no.vegvesen.dia.bifrost.core.config.Config;
import no.vegvesen.dia.bifrost.core.config.ConfigLoader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.FileNotFoundException;
import java.util.Objects;

public class MinioClientWrapper {
    private static final Logger log = LoggerFactory.getLogger(MinioClientWrapper.class);
    protected final MinioClient minioClient;

    private final Config appConfig;

    @Autowired
    protected MinioClientWrapper(Config config) {
        this.appConfig = config;
        String accessKey = config.getAccessKey();
        String secretKey = config.getSecretKey();
        String s3Url = config.getS3Url();

        log.debug("do we have data?:" + accessKey.charAt(0) + secretKey.charAt(1));
        minioClient = new MinioClient.Builder()
                .credentials(accessKey, secretKey)
                .endpoint(s3Url)
                .build();
    }
}
