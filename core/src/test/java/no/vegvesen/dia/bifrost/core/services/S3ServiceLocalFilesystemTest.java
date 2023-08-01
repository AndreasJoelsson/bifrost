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

import static org.junit.jupiter.api.Assertions.*;

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

        HttpStatus status = s3Service.upload(BUCKET_NAME, OBJECT_NAME, MEDIA_TYPE, stream);
        assertEquals(HttpStatus.OK, status, "Upload status should be OK");

        assertTrue(Files.exists(Path.of(BUCKET_NAME, OBJECT_NAME)), "File should exist after upload");
    }

    @Test
    void testMultipartFileUpload() throws IOException {
        String content = "Test content";
        MockMultipartFile file = new MockMultipartFile("data", OBJECT_NAME, MEDIA_TYPE, content.getBytes());

        HttpStatus status = s3Service.upload(BUCKET_NAME, file);
        assertEquals(HttpStatus.OK, status, "Upload status should be OK");

        assertTrue(Files.exists(Path.of(BUCKET_NAME, OBJECT_NAME)), "File should exist after upload");
    }
}
