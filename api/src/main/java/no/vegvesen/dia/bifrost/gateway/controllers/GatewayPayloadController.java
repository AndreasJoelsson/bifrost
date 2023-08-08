package no.vegvesen.dia.bifrost.gateway.controllers;

import com.fasterxml.jackson.databind.JsonNode;
import io.swagger.v3.oas.annotations.ExternalDocumentation;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import no.vegvesen.dia.bifrost.contract.S3PayloadResponse;
import no.vegvesen.dia.bifrost.contract.exception.InternalServerError;
import no.vegvesen.dia.bifrost.core.Context;
import no.vegvesen.dia.bifrost.core.services.PublishResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1")
@Tag(
        name = "Bifrost Gateway API",
        description = "Operations to store payloads to s3 or kafka",
        externalDocs = @ExternalDocumentation(
                description = "documentation on internal wiki",
                url = "https://www.vegvesen.no/wiki/x/L9W6D"))
public class GatewayPayloadController {
    public static final String DESC_TARGET_NAME = "Target Name for config lookup.";
    public static final String EXAMPLE_TARGET = "veibilder";
    private static final Logger log = LoggerFactory.getLogger(GatewayPayloadController.class);
    private final Context context;

    @Autowired
    public GatewayPayloadController(Context context) {
        this.context = context;
    }

    @PostMapping(
            value = "/payload/{target}",
            consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE, "application/yaml"},
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(
            summary = "Write json/xml/yaml to the target path",
            description = "Store payload to s3.",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "json uploaded"),
                    @ApiResponse(
                            responseCode = "400",
                            description = "request is missing vital information")
            })
    public ResponseEntity<Object> payloadAny(@Parameter(description = DESC_TARGET_NAME, example = EXAMPLE_TARGET) @PathVariable String target,
                                             @RequestBody JsonNode requestBody) {
        S3PayloadResponse response = new S3PayloadResponse();
        try ( response ) {
            log.info("Upload json into target: {}", target);
            log.info("Received payload: {}", requestBody);
            PublishResponse publishResponse = context.publish(target, UUID.randomUUID().toString() + ".json",
                    requestBody);
            switch (publishResponse.httpStatus()) {
                case OK -> {
                    response.setBucket(publishResponse.bucket());
                    response.setFileName(publishResponse.path());
                }
                case BAD_REQUEST -> {
                    return ResponseEntity.badRequest().body(publishResponse.message());
                }
                case INTERNAL_SERVER_ERROR -> {
                    return ResponseEntity.internalServerError().body(publishResponse.message());
                }
                default -> {
                    return ResponseEntity.internalServerError().body("Unknown status code: " + publishResponse.httpStatus());
                }
            }
        } catch (Exception e) {
            throw new InternalServerError(e.getMessage());
        }
        log.info("returning: " + response);
        return ResponseEntity.ok(response);
    }

}
