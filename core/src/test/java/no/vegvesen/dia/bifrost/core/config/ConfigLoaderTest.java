package no.vegvesen.dia.bifrost.core.config;

import no.vegvesen.dia.bifrost.core.services.S3ImplementationType;
import no.vegvesen.dia.bifrost.core.target.ActionType;
import no.vegvesen.dia.bifrost.core.target.TargetConfig;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.*;

class ConfigLoaderTest {

    @Test
    void fromFile() throws FileNotFoundException {
        ClassLoader loader = getClass().getClassLoader();
        File input = new File(loader.getResource("test_config.yml").getFile());
        Config config = ConfigLoader.fromFile(input);
        assertEquals("ABCD1234", config.getS3Config().getAccessKey());
        assertEquals("1234ABCD", config.getS3Config().getSecretKey());
        assertEquals("www.google.se", config.getS3Config().getUrl());
        assertEquals(S3ImplementationType.MINIO, config.getS3Config().getImplementation());
        assertEquals(2, config.getTargetConfigs().size());
        TargetConfig check = config.getTargetConfigs().get(0);
        assertEquals(ActionType.S3, check.getAction());
        assertEquals("bucket-push-test", check.getName());
        assertEquals("test-1", check.getTarget());
    }

    @Test
    void fromStream() {
        String yamlData = "s3:\n  accessKey: XYZ123\n  secretKey: 9876ZYX\n  url: www.example.com";
        InputStream inputStream = new ByteArrayInputStream(yamlData.getBytes());

        Config config = ConfigLoader.fromStream(inputStream);
        assertEquals("XYZ123", config.getS3Config().getAccessKey());
        assertEquals("9876ZYX", config.getS3Config().getSecretKey());
        assertEquals("www.example.com", config.getS3Config().getUrl());
    }

    @Test
    void fromFile_FileNotFound() {
        assertThrows(FileNotFoundException.class, () -> ConfigLoader.fromFile(new File("non_existent_file.yml")));
    }

    @Test
    void fromFile_InvalidYamlData() throws FileNotFoundException {
        File input = new File(Objects.requireNonNull(getClass().getClassLoader().getResource("invalid_config.yml")).getFile());
        Config config = ConfigLoader.fromFile(input);

        assertNull(config.getS3Config().getAccessKey());
        assertNull(config.getS3Config().getSecretKey());
        assertNull(config.getS3Config().getUrl());
    }

    @Test
    void fromStream_EmptyYamlData() {
        String yamlData = "";
        InputStream inputStream = new ByteArrayInputStream(yamlData.getBytes());

        Config config = ConfigLoader.fromStream(inputStream);
        assertNull(config.getS3Config().getAccessKey());
        assertNull(config.getS3Config().getSecretKey());
        assertNull(config.getS3Config().getUrl());
    }

    @Test
    void fromStream_DefaultConstructor() {
        Config config = ConfigLoader.fromStream(new ByteArrayInputStream(new byte[0]));
        assertNull(config.getS3Config().getAccessKey());
        assertNull(config.getS3Config().getSecretKey());
        assertNull(config.getS3Config().getUrl());
    }

}