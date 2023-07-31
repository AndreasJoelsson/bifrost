package no.vegvesen.dia.bifrost.core.config;

import org.junit.jupiter.api.Test;
import org.yaml.snakeyaml.constructor.ConstructorException;

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
        assertEquals("ABCD1234", config.getAccessKey());
        assertEquals("1234ABCD", config.getSecretKey());
        assertEquals("www.google.se", config.getS3Url());
    }

    @Test
    void fromStream() {
        String yamlData = "accessKey: XYZ123\nsecretKey: 9876ZYX\ns3Url: www.example.com";
        InputStream inputStream = new ByteArrayInputStream(yamlData.getBytes());

        Config config = ConfigLoader.fromStream(inputStream);
        assertEquals("XYZ123", config.getAccessKey());
        assertEquals("9876ZYX", config.getSecretKey());
        assertEquals("www.example.com", config.getS3Url());
    }

    @Test
    void fromFile_FileNotFound() {
        assertThrows(FileNotFoundException.class, () -> ConfigLoader.fromFile(new File("non_existent_file.yml")));
    }

    @Test
    void fromFile_InvalidYamlData() throws FileNotFoundException {
        File input = new File(Objects.requireNonNull(getClass().getClassLoader().getResource("invalid_config.yml")).getFile());
        assertThrows(ConstructorException.class, () -> ConfigLoader.fromFile(input));
    }

    @Test
    void fromStream_EmptyYamlData() {
        String yamlData = "";
        InputStream inputStream = new ByteArrayInputStream(yamlData.getBytes());

        Config config = ConfigLoader.fromStream(inputStream);
        assertNull(config.getAccessKey());
        assertNull(config.getSecretKey());
        assertNull(config.getS3Url());
    }

    @Test
    void fromStream_DefaultConstructor() {
        Config config = ConfigLoader.fromStream(new ByteArrayInputStream(new byte[0]));
        assertNull(config.getAccessKey());
        assertNull(config.getSecretKey());
        assertNull(config.getS3Url());
    }

}