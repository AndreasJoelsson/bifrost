package no.vegvesen.dia.bifrost.gateway.controllers;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import no.vegvesen.dia.bifrost.core.Context;
import no.vegvesen.dia.bifrost.core.services.PublishResponse;
import no.vegvesen.dia.bifrost.core.target.ActionType;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class GatewayPayloadControllerTest {

    @Mock
    private Context context;

    @InjectMocks
    private GatewayPayloadController controller;

    @Test
    void testPayloadAny_OK() {
        // Arrange
        String target = "testTarget";
        JsonNode requestBody = new ObjectMapper().createObjectNode();
        PublishResponse publishResponse = new PublishResponse(HttpStatus.OK, ActionType.S3, "bucket", "path", null);

        when(context.publish(eq(target), anyString(), eq(requestBody)))
                .thenReturn(publishResponse);

        // Act
        ResponseEntity<Object> responseEntity = controller.payloadAny(target, requestBody);

        // Assert
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        verify(context, times(1)).publish(eq(target), anyString(), eq(requestBody));
    }

    @Test
    void testPayloadAny_BAD_REQUEST() {
        // Arrange
        String target = "testTarget";
        JsonNode requestBody = new ObjectMapper().createObjectNode();
        PublishResponse publishResponse = new PublishResponse(HttpStatus.BAD_REQUEST, ActionType.S3, null, null, "Bad request");

        when(context.publish(eq(target), anyString(), eq(requestBody)))
                .thenReturn(publishResponse);

        // Act
        ResponseEntity<Object> responseEntity = controller.payloadAny(target, requestBody);

        // Assert
        assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
        assertEquals("Bad request", responseEntity.getBody());
    }

}