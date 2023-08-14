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

/**
 * The GatewayObjectController class is responsible for managing the upload of objects to S3 buckets.
 * It provides endpoints to perform these actions, and handles the communication with the underlying services.
 * The endpoints are defined under the "/api/v1/object" base path.
 *
 * This class is marked as a REST controller, and is tagged for inclusion in generated OpenAPI documentation.
 *
 * @see <a href="https://www.vegvesen.no/wiki/x/s4UfDg">Documentation on internal wiki</a>
 */
@RestController
@RequestMapping("/api/v1/object")
@Tag(
        name = "Bifrost Gateway Object Controller",
        description = "API endpoints responsible for managing the upload and retrieval of objects to/from S3 buckets, " +
                "including images and other file types. Detailed specifications and operations can be found in the " +
                "linked documentation.",
        externalDocs = @ExternalDocumentation(
                description = "Detailed documentation on internal wiki (Access required)",
                url = "https://www.vegvesen.no/wiki/x/s4UfDg"))
public class GatewayObjectController {
    private static final Logger log = LoggerFactory.getLogger(GatewayObjectController.class);

    private final Context context;

    /**
     * Constructs a GatewayObjectController with the given context.
     *
     * @param context The context that encapsulates core functionality.
     */
    @Autowired
    public GatewayObjectController(Context context) {
        this.context = context;
    }

    /**
     * Endpoint to upload one or multiple files for a given target.
     * The endpoint consumes multipart form data and produces a JSON response.
     *
     * @param target The target for the service that should be used.
     * @param file   The file(s) to upload.
     * @return A ResponseEntity containing the status and details of the upload.
     *
     * @apiNote Successful uploads return a 200 OK status, while various error conditions are handled with appropriate HTTP status codes.
     */
    @PostMapping(
            value = "/{target}",
            consumes = MediaType.MULTIPART_FORM_DATA_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(
            summary = "Upload Object to Specified Target",
            description = "This endpoint allows for the uploading of one or more objects to a specified target in the " +
                    "S3 bucket. Objects can include images, files, and other data types. It returns detailed " +
                    "information about the uploaded objects, including location and file name.",
            parameters = {
                    @Parameter(name = "target", description = "The target directory for the service that should be " +
                            "used, e.g., 'vegbilder'.", example = "vegbilder", required = true),
                    @Parameter(name = "file", description = "The file or files to upload. Must be provided in multipart " +
                            "form data format.", required = true)
            },
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Object(s) successfully uploaded. Returns details of the uploaded objects.",
                            content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE)),
                    @ApiResponse(
                            responseCode = "400",
                            description = "Bad request. The request is missing vital information or is malformed."),
                    @ApiResponse(
                            responseCode = "500",
                            description = "Internal server error. An unexpected error occurred during the processing " +
                                    "of the request.")
            })

    public ResponseEntity<Object> uploadFile(@Parameter(description = "Target for the service that should be used.", example = "vegbilder") @PathVariable String target,
                                             @RequestPart("file") MultipartFile file) {
        S3ObjectResponse response = new S3ObjectResponse();
        try ( response ) {
            log.info("Upload json into target: {}", target);
            log.info("Received file: {}", file);
            log.info("Received file content type: {}", file.getContentType());
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
