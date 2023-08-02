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
import java.util.Arrays;
import java.util.Comparator;

/**
 * An interface for services related to operations on AWS S3.
 * It provides methods for uploading different types of data to the S3.
 */
public interface S3Service {

    /**
     * Uploads a JSON node to the specified S3 bucket.
     *
     * @param bucketName the name of the S3 bucket
     * @param objectName the name of the object in the bucket
     * @param node       the JSON node to be uploaded
     * @return the status of the upload operation
     */
    default HttpStatus upload(String bucketName, String objectName, JsonNode node) {
        ObjectMapper mapper = new ObjectMapper();
        try {
            String jsonString = mapper.writeValueAsString(node);
            return upload(bucketName, objectName, MediaType.APPLICATION_JSON.toString(),
                    new ByteArrayInputStream(jsonString.getBytes(StandardCharsets.UTF_8)));
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            return HttpStatus.BAD_REQUEST;
        }
    }

    /**
     * Uploads multiple files to the specified S3 bucket.
     *
     * @param bucketname the name of the S3 bucket
     * @param files      array of files to be uploaded
     * @return the status of the upload operation. Returns the status code of the most severe response if there are multiple files.
     */
    default HttpStatus upload(String bucketname, MultipartFile[] files) {
        return Arrays.stream(files).map(file -> this.upload(bucketname, file))
                .max(Comparator.comparingInt(HttpStatus::value)).orElse(HttpStatus.INTERNAL_SERVER_ERROR);
    }

    /**
     * Uploads a single file to the specified S3 bucket.
     *
     * @param bucketname the name of the S3 bucket
     * @param file       the file to be uploaded
     * @return the status of the upload operation
     */
    default HttpStatus upload(String bucketname, MultipartFile file) {
        try {
            return upload(bucketname, file.getOriginalFilename(), file.getContentType(), file.getInputStream());
        } catch (IOException e) {
            e.printStackTrace();
            return HttpStatus.BAD_REQUEST;
        }
    }

    /**
     * Uploads data from the input stream to the specified S3 bucket.
     *
     * @param bucketName the name of the S3 bucket
     * @param objectName the name of the object in the bucket
     * @param mediaType  the media type of the data
     * @param stream     the input stream from which the data is read
     * @return the status of the upload operation
     */
    HttpStatus upload(String bucketName, String objectName, String mediaType, InputStream stream);

}
