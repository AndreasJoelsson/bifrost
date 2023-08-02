package no.vegvesen.dia.bifrost.resources;

import io.minio.MinioClient;
import no.vegvesen.dia.bifrost.core.config.Config;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

public class MinioClientWrapper {
    private static final Logger log = LoggerFactory.getLogger(MinioClientWrapper.class);
    protected final MinioClient minioClient;

    @Autowired
    protected MinioClientWrapper(Config config) {
        String accessKey = config.getS3Config().getAccessKey();
        String secretKey = config.getS3Config().getSecretKey();
        String s3Url = config.getS3Config().getUrl();

        log.debug("do we have data?:" + accessKey.charAt(0) + secretKey.charAt(1));
        minioClient = new MinioClient.Builder()
                .credentials(accessKey, secretKey)
                .endpoint(s3Url)
                .build();
    }
}
