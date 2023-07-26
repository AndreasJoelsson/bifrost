package no.vegvesen.dia.bifrost.gateway.controllers;

import io.swagger.v3.oas.annotations.ExternalDocumentation;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import no.vegvesen.dia.bifrost.core.services.S3Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/v1/payload")
@Tag(
        name = "Bifrost Gateway API",
        description = "Operations to store payloads to s3 or kafka",
        externalDocs = @ExternalDocumentation(
                description = "documentation on internal wiki",
                url = "https://www.vegvesen.no/wiki/x/L9W6D"))
public class GatewayPayloadController {
    private static final Logger log = LoggerFactory.getLogger(GatewayPayloadController.class);
    public static final String DESC_PATH_WITH_FILENAME = "path with filename";
    public static final String EXAMPLE_MYPATH_SUBPATH_FILENAME_JPG = "/mypath/subpath/filename.jpg";
    public static final String DESC_BUCKET_NAME = "bucket name";
    public static final String EXAMPLE_MYBUCKET = "mybucket";


    private final S3Service s3Service;

    @Autowired
    public GatewayPayloadController(S3Service s3Service) {
        this.s3Service = s3Service;
    }

    @PostMapping(
            value = "/{target}",
            consumes = { MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE, "application/x-yaml" })
    @Operation(
            summary = "Write payload to the target path",
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
    public ResponseEntity<Object> uploadFile(@Parameter(description = DESC_BUCKET_NAME, example = EXAMPLE_MYBUCKET) @PathVariable String target,
                                             @RequestPart("file") MultipartFile[] files) {

        log.info("uploading {} file(s) into target: {}", files.length, target);
        //HttpStatus httpStatus = s3Service.upload(bucket, path, files);
        //return ResponseEntity.status(httpStatus).build();
        return ResponseEntity.ok().build();
    }

}
