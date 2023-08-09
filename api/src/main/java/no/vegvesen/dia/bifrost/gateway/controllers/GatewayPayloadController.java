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

/**
 * Gateway Payload Controller responsible for managing the payload interactions
 * such as storing payloads to s3 or kafka.
 *
 * <p>This class offers RESTful API operations and is mapped under the /api/v1
 * endpoint. Documentation and details of the API can be found on the internal wiki at
 * {@link "https://www.vegvesen.no/wiki/x/L9W6D"}.
 */
@RestController
@RequestMapping("/api/v1/payload")
@Tag(
        name = "Gateway Payload Controller",
        description = "Controller for managing payloads, providing operations to store them to s3 or Kafka.",
        externalDocs = @ExternalDocumentation(
                description = "For more information, refer to the internal documentation wiki.",
                url = "https://www.vegvesen.no/wiki/x/TBD"))
public class GatewayPayloadController {
    public static final String DESC_TARGET_NAME = "Target Name for config lookup.";
    public static final String EXAMPLE_TARGET = "veibilder";
    private static final Logger log = LoggerFactory.getLogger(GatewayPayloadController.class);
    private final Context context;

    /**
     * Constructs a new GatewayPayloadController with the given context.
     *
     * @param context the context required for publishing payloads
     */
    @Autowired
    public GatewayPayloadController(Context context) {
        this.context = context;
    }

    /**
     * Endpoint to write JSON, XML, or YAML data to the specified target path.
     * The data is stored to s3.
     *
     * <p>URL: POST /api/v1/payload/{target}</p>
     *
     * @param target      The target to which the payload will be published
     * @param requestBody The payload data in JSON, XML, or YAML format
     * @return ResponseEntity containing the status and details of the operation
     * @throws InternalServerError if any exception occurs while processing the request
     */
    @PostMapping(
            value = "/{target}",
            consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE, "application/yaml"},
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(
            summary = "Write payload to a target path",
            description = "Accepts JSON, XML, or YAML and stores the payload to the specified target in S3. " +
                    "Handles various response statuses based on the result of the operation.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Payload successfully uploaded to the target."),
                    @ApiResponse(responseCode = "400", description = "Request is missing vital information or is improperly formatted."),
                    @ApiResponse(responseCode = "500", description = "Internal Server Error or other unexpected server-side issues.")
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
