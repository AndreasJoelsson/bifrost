package no.vegvesen.dia.bifrost.core.services;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.mock.web.MockMultipartFile;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class S3ServiceLocalFilesystemTest {
    private static final String BUCKET_NAME = "testBucket";
    private static final String OBJECT_NAME = "testObject";
    private static final String MEDIA_TYPE = "application/json";
    private S3ServiceLocalFilesystem s3Service;

    @BeforeEach
    void setUp() {
        s3Service = new S3ServiceLocalFilesystem();
    }

    @AfterEach
    void tearDown() throws IOException {
        Files.deleteIfExists(Path.of(BUCKET_NAME, OBJECT_NAME));
        Files.deleteIfExists(Path.of(BUCKET_NAME));
    }

    @Test
    void testUpload() throws IOException {
        String content = "Test content";
        InputStream stream = new ByteArrayInputStream(content.getBytes(StandardCharsets.UTF_8));

        HttpStatus status = s3Service.publish(BUCKET_NAME, OBJECT_NAME, MEDIA_TYPE, stream);
        assertEquals(HttpStatus.OK, status, "Upload status should be OK");

        assertTrue(Files.exists(Path.of(BUCKET_NAME, OBJECT_NAME)), "File should exist after upload");
    }

    @Test
    void testMultipartFileUpload() throws IOException {
        String content = "Test content";
        MockMultipartFile file = new MockMultipartFile("data", OBJECT_NAME, MEDIA_TYPE, content.getBytes());

        HttpStatus status = s3Service.publish(BUCKET_NAME, file);
        assertEquals(HttpStatus.OK, status, "Upload status should be OK");

        assertTrue(Files.exists(Path.of(BUCKET_NAME, OBJECT_NAME)), "File should exist after upload");
    }

    @Test
    public void testPublish() throws IOException {
        String destination = "target/test-output";
        String objectName = "testfile.txt";
        String mediaType = "text/plain";
        String content = "Hello World!";

        InputStream stream = new ByteArrayInputStream(content.getBytes());

        // Run the publish method
        HttpStatus status = s3Service.publish(destination, objectName, mediaType, stream);

        // Check if the file was created successfully
        assertEquals(HttpStatus.OK, status);

        Path path = Path.of(destination, objectName);
        assertEquals(content, Files.readString(path));

        // Cleanup after test
        Files.deleteIfExists(path);
        Files.deleteIfExists(Path.of(destination));
    }

}
