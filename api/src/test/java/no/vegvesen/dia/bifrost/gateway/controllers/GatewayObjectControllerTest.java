package no.vegvesen.dia.bifrost.gateway.controllers;

import no.vegvesen.dia.bifrost.core.services.S3ServiceLocalFilesystem;
import org.junit.jupiter.api.Test;
import org.springframework.http.ResponseEntity;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;

class GatewayObjectControllerTest extends S3ServiceTestdata {

    private final S3ServiceLocalFilesystem s3ServiceLocalFilesystem = new S3ServiceLocalFilesystem();
    private GatewayObjectController s3GatewayController;


    GatewayObjectControllerTest() throws IOException {
    }

    @Test
    void shall_upload_multiple_files() throws Exception {
        s3GatewayController = new GatewayObjectController(s3ServiceLocalFilesystem);
        String target = "bob";
        ResponseEntity<Object> response = s3GatewayController.uploadFile(target, new TestMulitpartFile[]{file1, file2});
        assertEquals(200, response.getStatusCode().value());

        // cleanup
        S3ServiceLocalFilesystem.deleteAllEmptyFolders(bucketName + path);
    }

    @Test
    void shall_upload_file_and_replace_filename_when_file_name_is_provided() throws Exception {
        String target = "ken";
        s3GatewayController = new GatewayObjectController(s3ServiceLocalFilesystem);
        ResponseEntity<Object> response = s3GatewayController.uploadFile(target, new TestMulitpartFile[]{file1});
        assertEquals(200, response.getStatusCode().value());
    }

}