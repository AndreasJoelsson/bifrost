package no.vegvesen.dia.bifrost.client;

import no.vegvesen.dia.bifrost.contract.S3GatewayStates;
import no.vegvesen.dia.bifrost.core.services.S3ServiceLocalFilesystem;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.web.multipart.MultipartFile;

class S3GatewayObjectClientTest {

  /*  private final S3ServiceLocalFilesystem localFilesystem = new S3ServiceLocalFilesystem();
    @Test
    void shall_upload_object() {
        MultipartFile[] files = new MultipartFile[0];
        S3GatewayStates clientResponse = new S3GatewayObjectClient(localFilesystem).upload("mybucket", "mypath/subpath/testfile.jpg", files);
        Assertions.assertEquals(S3GatewayStates.UPLOADED, clientResponse);
    }*/
}