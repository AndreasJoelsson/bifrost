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
@RequestMapping("/api/v1/object")
@Tag(
        name = "Bifrost Gateway API",
        description = "Operations to upload and download objects to S3 buckets",
        externalDocs = @ExternalDocumentation(
                description = "documentation on internal wiki",
                url = "https://www.vegvesen.no/wiki/x/L9W6D"))
public class GatewayObjectController {
    private static final Logger log = LoggerFactory.getLogger(GatewayObjectController.class);
    public static final String DESC_BUCKET_NAME = "bucket name";
    public static final String EXAMPLE_MYBUCKET = "mybucket";


    private final S3Service s3Service;

    @Autowired
    public GatewayObjectController(S3Service s3Service) {
        this.s3Service = s3Service;
    }

    @PostMapping(
            value = "/{target}",
            consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
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
    public ResponseEntity<Object> uploadFile(@Parameter(description = DESC_BUCKET_NAME, example = EXAMPLE_MYBUCKET) @PathVariable String target,
                                             @RequestPart("file") MultipartFile[] files) {

        log.info("uploading {} file(s) to target: {}", files.length, target);
        for (MultipartFile file : files) {
            log.info("File has attributes: {}", file.getOriginalFilename());
            log.info("File has attributes: {}", file.getSize());
            log.info("File has attributes: {}", file.getName());
            log.info("File has attributes: {}", file.getResource());
            log.info("File has attributes: {}", file.getContentType());
        }
        //HttpStatus httpStatus = s3Service.upload(bucket, path, files);
        //return ResponseEntity.status(httpStatus).build();
        return ResponseEntity.ok().build();
    }

}
