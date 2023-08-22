package no.vegvesen.dia.bifrost.core.services;

import no.vegvesen.dia.bifrost.core.target.ActionType;
import org.springframework.http.HttpStatus;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.ResponseEntity;
import java.io.IOException;

@JsonInclude(Include.NON_NULL)
public record PublishResponse(@JsonIgnore HttpStatus httpStatus, ActionType type, String bucket, String path, String message) {

    private static final ObjectMapper objectMapper = new ObjectMapper();
    public ResponseEntity<Object> toResponseEntity() {
        try {
            String jsonContent = objectMapper.writeValueAsString(this);
            return new ResponseEntity<>(jsonContent, httpStatus);
        } catch (IOException e) {
            throw new RuntimeException("Failed to serialize PublishResponse to JSON", e);
        }
    }

    public static PublishResponse fromJson(String jsonString) throws IOException {
        return objectMapper.readValue(jsonString, PublishResponse.class);
    }

}
