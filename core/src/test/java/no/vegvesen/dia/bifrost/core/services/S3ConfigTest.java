package no.vegvesen.dia.bifrost.core.services;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import org.junit.jupiter.api.Test;

import java.io.InputStream;

import static org.junit.jupiter.api.Assertions.*;

class S3ConfigTest {

    @Test
    public void testConstruction() {
        String accessKey = "accessKey123";
        String secretKey = "secretKey123";
        String url = "https://s3.example.com";
        S3ImplementationType implementation = S3ImplementationType.LOCAL_FILE_SYSTEM; // Replace with an actual value

        S3Config config = new S3Config(accessKey, secretKey, url, implementation);

        assertEquals(accessKey, config.getAccessKey());
        assertEquals(secretKey, config.getSecretKey());
        assertEquals(url, config.getUrl());
        assertEquals(implementation, config.getImplementation());
    }

    @Test
    public void testEmptyConstruction() {
        S3Config config = S3Config.empty();

        assertNull(config.getAccessKey());
        assertNull(config.getSecretKey());
        assertNull(config.getUrl());
        assertNull(config.getImplementation());
    }

    @Test
    public void testLoadFromYaml() throws Exception {
        ObjectMapper mapper = new ObjectMapper(new YAMLFactory());

        String yamlString = "access-key: accessKey123\n" +
                        "secret-key: secretKey123\n" +
                        "url: https://s3.example.com\n" +
                        "action: minio";
        S3Config config = mapper.readValue(yamlString, S3Config.class);

        assertEquals("accessKey123", config.getAccessKey());
        assertEquals("secretKey123", config.getSecretKey());
        assertEquals("https://s3.example.com", config.getUrl());
        assertEquals(S3ImplementationType.MINIO, config.getImplementation()); // Replace with actual value
    }

}