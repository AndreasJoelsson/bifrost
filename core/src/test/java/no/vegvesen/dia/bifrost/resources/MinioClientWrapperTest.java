package no.vegvesen.dia.bifrost.resources;

import io.minio.MinioClient;
import no.vegvesen.dia.bifrost.core.config.Config;
import no.vegvesen.dia.bifrost.core.services.S3Config;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class MinioClientWrapperTest {

    private Config config;
    private S3Config s3Config;
    private MinioClientWrapper minioClientWrapper;

    @BeforeEach
    public void setUp() {
        config = mock(Config.class);
        s3Config = mock(S3Config.class);

        when(config.getS3Config()).thenReturn(s3Config);
        when(s3Config.getAccessKey()).thenReturn("accessKey123");
        when(s3Config.getSecretKey()).thenReturn("secretKey123");
        when(s3Config.getUrl()).thenReturn("http://localhost:9000");

        minioClientWrapper = new MinioClientWrapper(config);
    }

    @Test
    public void testCreateMinioClient() {
        MinioClient minioClient = minioClientWrapper.createMinioClient(config);

        assertNotNull(minioClient, "MinioClient should not be null");
    }

}