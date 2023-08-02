package no.vegvesen.dia.bifrost.core.target;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class TargetConfigTest {

    @Test
    public void whenDeserializing_thenCorrectFieldsAreSet() throws Exception {
        String yamlString = "target: someTarget\naction: kafka\nname: someName\n";

        ObjectMapper mapper = new ObjectMapper(new YAMLFactory());

        TargetConfig targetConfig = mapper.readValue(yamlString, TargetConfig.class);

        assertEquals("someTarget", targetConfig.getTarget());
        assertEquals(ActionType.KAFKA, targetConfig.getAction());
        assertEquals("someName", targetConfig.getName());
    }

    @Test
    public void whenSerializing_thenCorrectYAMLIsCreated() throws Exception {
        TargetConfig targetConfig = new TargetConfig("someTarget", ActionType.S3, "someName");

        ObjectMapper mapper = new ObjectMapper(new YAMLFactory());

        String actualYamlString = mapper.writeValueAsString(targetConfig);

        String expectedYamlString = "---\ntarget: \"someTarget\"\naction: \"s3\"\nname: \"someName\"\n";

        assertEquals(expectedYamlString, actualYamlString, "actual: " + actualYamlString + ", expected: " + expectedYamlString);
    }
}