package no.vegvesen.dia.bifrost.core.file;

import org.springframework.web.multipart.MultipartFile;

import java.util.Objects;

public class Extension {

    public static String extension(MultipartFile file) {
        return switch (Objects.requireNonNull(file.getContentType())) {
            case "application/json" -> ".json";
            case "application/xml" -> ".xml";
            case "application/yaml" -> ".yml";
            default -> "";
        };
    }

}
