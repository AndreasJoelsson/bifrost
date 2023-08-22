package no.vegvesen.dia.bifrost.gateway.controllers;

import no.vegvesen.dia.bifrost.contract.S3ObjectResponse;
import no.vegvesen.dia.bifrost.core.Context;
import no.vegvesen.dia.bifrost.core.services.PublishResponse;
import no.vegvesen.dia.bifrost.core.target.ActionType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import java.util.Objects;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class GatewayObjectControllerTest {

    private Context context;
    private GatewayObjectController controller;

    @BeforeEach
    void setUp() {
        context = mock(Context.class);
        controller = new GatewayObjectController(context);
    }

    @Test
    void uploadFile_Success() throws Exception {
        String target = "vegbilder";
        MultipartFile file = mock(MultipartFile.class);
        PublishResponse publishResponse = new PublishResponse(HttpStatus.OK, ActionType.S3, "testBucket", "testPath", null);

        when(context.publish(target, file)).thenReturn(publishResponse);
        when(file.getOriginalFilename()).thenReturn("test.jpg");

        ResponseEntity<Object> response = controller.uploadFile(target, file);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        S3ObjectResponse responseBody = (S3ObjectResponse) response.getBody();
        assertEquals("testBucket", responseBody.getBucket());
        assertEquals("testPath", responseBody.getFileName());
        assertEquals("test.jpg", responseBody.getOriginalFilename());
    }

    @Test
    void uploadFile_BadRequest() throws Exception {
        String target = "vegbilder";
        MultipartFile file = mock(MultipartFile.class);
        PublishResponse publishResponse = new PublishResponse(HttpStatus.BAD_REQUEST, null, null, null, "Invalid request");

        when(context.publish(target, file)).thenReturn(publishResponse);

        ResponseEntity<Object> response = controller.uploadFile(target, file);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        PublishResponse publishResponseExpected = PublishResponse.fromJson((String)response.getBody());
        assertEquals("Invalid request", publishResponseExpected.message());
    }

}