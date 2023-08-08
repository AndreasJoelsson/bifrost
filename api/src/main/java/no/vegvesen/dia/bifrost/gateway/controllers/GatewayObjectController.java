package no.vegvesen.dia.bifrost.gateway.controllers;

import io.swagger.v3.oas.annotations.ExternalDocumentation;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import no.vegvesen.dia.bifrost.contract.S3ObjectResponse;
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
import org.springframework.web.multipart.MultipartFile;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/object")
@Tag(
        name = "Bifrost Gateway API",
        description = "Operations to upload objects to S3 buckets",
        externalDocs = @ExternalDocumentation(
                description = "documentation on internal wiki",
                url = "https://www.vegvesen.no/wiki/x/L9W6D"))
public class GatewayObjectController {
    private static final Logger log = LoggerFactory.getLogger(GatewayObjectController.class);

    private final Context context;

    @Autowired
    public GatewayObjectController(Context context) {
        this.context = context;
    }

    @PostMapping(
            value = "/{target}",
            consumes = MediaType.MULTIPART_FORM_DATA_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(
            summary = "Upload objects",
            description = "Upload one or multiple images for a given source",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "object(s) uploaded",
                            content = @Content(mediaType = MediaType.MULTIPART_FORM_DATA_VALUE)),
                    @ApiResponse(
                            responseCode = "400",
                            description = "request is missing vital information")
            })
    public ResponseEntity<Object> uploadFile(@Parameter(description = "Target for the service that should be used.", example = "vegbilder") @PathVariable String target,
                                             @RequestPart("file") MultipartFile file) {
        S3ObjectResponse response = new S3ObjectResponse();
        try ( response ) {
            log.info("Upload json into target: {}", target);
            log.info("Received file: {}", file);
            PublishResponse publishResponse = context.publish(target, file);
            switch (publishResponse.httpStatus()) {
                case OK -> {
                    response.setBucket(publishResponse.bucket());
                    response.setFileName(publishResponse.path());
                    response.setOriginalFilename(file.getOriginalFilename());
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
