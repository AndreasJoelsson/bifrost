package no.vegvesen.dia.bifrost.core.file;

import org.junit.jupiter.api.Test;

import java.io.File;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class FileVerifierTest {

    @Test
    public void testValidJsonFile() {
        File validJsonFile = new File(getClass().getClassLoader().getResource("test_json.json").getFile());
        assertTrue(FileVerifier.isValidJsonFile(validJsonFile));
        assertFalse(FileVerifier.isValidXmlFile(validJsonFile));
        assertFalse(FileVerifier.isValidYamlFile(validJsonFile));
    }

    @Test
    public void testValidYamlFile() {
        File validYamlFile = new File(getClass().getClassLoader().getResource("test_yaml.yml").getFile());
        assertFalse(FileVerifier.isValidJsonFile(validYamlFile));
        assertFalse(FileVerifier.isValidXmlFile(validYamlFile));
        assertTrue(FileVerifier.isValidYamlFile(validYamlFile));
    }

    @Test
    public void testValidXmlFile() {
        File validXmlFile = new File(getClass().getClassLoader().getResource("test_xml.xml").getFile());
        assertFalse(FileVerifier.isValidJsonFile(validXmlFile));
        assertTrue(FileVerifier.isValidXmlFile(validXmlFile));
        assertFalse(FileVerifier.isValidYamlFile(validXmlFile));
    }

}
