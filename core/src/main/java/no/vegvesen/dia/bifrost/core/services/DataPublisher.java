package no.vegvesen.dia.bifrost.core.services;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

/**
 * An interface for services related to data publishing operations.
 * It can be implemented to support various data publishing platforms such as AWS S3, Kafka, etc.
 * It provides methods for publishing different types of data, including JSON nodes and files.
 */
public interface DataPublisher {

    /**
     * Publishes a JSON node to the specified destination (e.g., S3 bucket or Kafka topic).
     *
     * @param destination the name of the destination (e.g., S3 bucket name or Kafka topic name)
     * @param objectName  the name of the object in the destination
     * @param node        the JSON node to be published
     * @return the status of the publishing operation
     */
    default HttpStatus publish(String destination, String objectName, JsonNode node) {
        ObjectMapper mapper = new ObjectMapper();
        try {
            String jsonString = mapper.writeValueAsString(node);
            return publish(destination, objectName, MediaType.APPLICATION_JSON.toString(),
                    new ByteArrayInputStream(jsonString.getBytes(StandardCharsets.UTF_8)));
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            return HttpStatus.BAD_REQUEST;
        }
    }

    /**
     * Publishes a single file to the specified destination (e.g., S3 bucket or Kafka topic).
     *
     * @param destination the name of the destination (e.g., S3 bucket name or Kafka topic name)
     * @param objectName  the name of the object in the destination
     * @param file        the file to be published
     * @return the status of the publishing operation
     */
    default HttpStatus publish(String destination, String objectName, MultipartFile file) {
        try {
            return publish(destination, objectName, file.getContentType(), file.getInputStream());
        } catch (IOException e) {
            e.printStackTrace();
            return HttpStatus.BAD_REQUEST;
        }
    }

    /**
     * Publishes a single file to the specified destination (e.g., S3 bucket or Kafka topic).
     *
     * @param destination the name of the destination (e.g., S3 bucket name or Kafka topic name)
     * @param file        the file to be published
     * @return the status of the publishing operation
     */
    default HttpStatus publish(String destination, MultipartFile file) {
        try {
            return publish(destination, file.getOriginalFilename(), file.getContentType(), file.getInputStream());
        } catch (IOException e) {
            e.printStackTrace();
            return HttpStatus.BAD_REQUEST;
        }
    }

    /**
     * Publishes data from the input stream to the specified destination (e.g., S3 bucket or Kafka topic).
     *
     * @param destination the name of the destination (e.g., S3 bucket name or Kafka topic name)
     * @param objectName  the name of the object in the destination
     * @param mediaType   the media type of the data
     * @param stream      the input stream from which the data is read
     * @return the status of the publishing operation
     */
    HttpStatus publish(String destination, String objectName, String mediaType, InputStream stream);

}
