package no.vegvesen.dia.bifrost.core;

import com.fasterxml.jackson.databind.JsonNode;
import no.vegvesen.dia.bifrost.core.config.Config;
import org.springframework.http.HttpStatus;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;

public interface Context {

    Config config();

    HttpStatus publish(String target, MultipartFile file);

    HttpStatus publish(String target, String objectName, JsonNode node);

    HttpStatus publish(String target, String objectName, String mediaType, InputStream stream);

}
