package no.vegvesen.dia.bifrost.core;

import com.fasterxml.jackson.databind.JsonNode;
import no.vegvesen.dia.bifrost.core.config.Config;
import no.vegvesen.dia.bifrost.core.services.PublishResponse;
import org.springframework.http.HttpStatus;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;

public interface Context {

    Config config();

    PublishResponse publish(String target, MultipartFile file);

    PublishResponse publish(String target, String objectName, JsonNode node);

    PublishResponse publish(String target, String objectName, String mediaType, InputStream stream);

}
