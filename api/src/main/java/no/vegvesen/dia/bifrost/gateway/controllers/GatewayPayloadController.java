package no.vegvesen.dia.bifrost.gateway.controllers;

import com.fasterxml.jackson.databind.JsonNode;
import io.swagger.v3.oas.annotations.ExternalDocumentation;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import no.vegvesen.dia.bifrost.core.services.DataPublisher;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1")
@Tag(
        name = "Bifrost Gateway API",
        description = "Operations to store payloads to s3 or kafka",
        externalDocs = @ExternalDocumentation(
                description = "documentation on internal wiki",
                url = "https://www.vegvesen.no/wiki/x/L9W6D"))
public class GatewayPayloadController {
    public static final String DESC_PATH_WITH_FILENAME = "path with filename";
    public static final String EXAMPLE_MYPATH_SUBPATH_FILENAME_JPG = "/mypath/subpath/filename.jpg";
    public static final String DESC_BUCKET_NAME = "bucket name";
    public static final String EXAMPLE_MYBUCKET = "mybucket";
    private static final Logger log = LoggerFactory.getLogger(GatewayPayloadController.class);
    private final DataPublisher dataPublisher;

    @Autowired
    public GatewayPayloadController(DataPublisher dataPublisher) {
        this.dataPublisher = dataPublisher;
    }

    @PostMapping(
            value = "/payload/{target}",
            consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE, "application/yaml"})
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
    public ResponseEntity<Object> payloadAny(@Parameter(description = DESC_BUCKET_NAME, example = EXAMPLE_MYBUCKET) @PathVariable String target,
                                             @RequestBody JsonNode requestBody) {
        log.info("Upload json into target: {}", target);
        log.info("Recived payload: {}", requestBody);
        log.info("Instantiating the controller with: {}", dataPublisher.getClass().descriptorString());

        //HttpStatus httpStatus = s3Service.upload(bucket, path, files);
        //return ResponseEntity.status(httpStatus).build();
        return ResponseEntity.ok().build();
    }

}
