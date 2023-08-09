package no.vegvesen.dia.bifrost.gateway.component;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.AbstractJackson2HttpMessageConverter;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;

/**
 * This class is responsible for handling the conversion of YAML messages within
 * the application. It extends the {@link AbstractJackson2HttpMessageConverter} class
 * and is annotated with the @Component annotation, allowing Spring to detect and manage
 * it as a component.
 */
@Component
public class YamlMessageConverter extends AbstractJackson2HttpMessageConverter {

    /**
     * Constructor to initialize the YamlMessageConverter with the supported YAML media types.
     * It uses a Jackson {@link ObjectMapper} configured with {@link YAMLFactory} to enable
     * YAML parsing and generation.
     */
    YamlMessageConverter() {
        super(new ObjectMapper(new YAMLFactory()),
                new MediaType("application", "yaml", StandardCharsets.UTF_8),
                new MediaType("text", "yaml", StandardCharsets.UTF_8),
                new MediaType("application", "*+yaml", StandardCharsets.UTF_8),
                new MediaType("text", "*+yaml", StandardCharsets.UTF_8),
                new MediaType("application", "yml", StandardCharsets.UTF_8),
                new MediaType("text", "yml", StandardCharsets.UTF_8),
                new MediaType("application", "*+yml", StandardCharsets.UTF_8),
                new MediaType("text", "*+yml", StandardCharsets.UTF_8));
    }

    /**
     * Override the {@link #setObjectMapper(ObjectMapper)} method to ensure that the
     * provided ObjectMapper is configured with an instance of YAMLFactory.
     *
     * @param objectMapper The object mapper to set.
     * @throws IllegalArgumentException if the object mapper does not use a YAMLFactory.
     */
    @Override
    public void setObjectMapper(final ObjectMapper objectMapper) {
        if (!(objectMapper.getFactory() instanceof YAMLFactory)) {
            // Sanity check to make sure we do have an ObjectMapper configured
            // with YAML support, just in case someone attempts to call
            // this method elsewhere.
            throw new IllegalArgumentException(
                    "ObjectMapper must be configured with an instance of YAMLFactory");
        }
        super.setObjectMapper(objectMapper);
    }
}
