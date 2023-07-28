package no.vegvesen.dia.bifrost.gateway.services;

import no.vegvesen.dia.bifrost.core.services.S3ServiceUsingMinio;
import no.vegvesen.dia.bifrost.gateway.controllers.S3ServiceTestdata;
import no.vegvesen.dia.bifrost.gateway.controllers.TestMulitpartFile;
import org.springframework.http.HttpStatus;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;

class S3ServiceUsingMinioTest extends S3ServiceTestdata {

    public S3ServiceUsingMinioTest() throws IOException {
    }

    //@Test
    public void shall_upload_file() {
        // TODO bør kanskje vurdere integrasjonstester her
        S3ServiceUsingMinio s3ServiceUsingMinio = new S3ServiceUsingMinio();
        HttpStatus httpStatus = s3ServiceUsingMinio.upload(bucketName, path, new TestMulitpartFile[]{file1, file2});
        assertEquals(HttpStatus.OK, httpStatus);
    }

}
