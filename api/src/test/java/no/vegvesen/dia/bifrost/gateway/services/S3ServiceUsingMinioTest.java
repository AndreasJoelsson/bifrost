package no.vegvesen.dia.bifrost.gateway.services;

import no.vegvesen.dia.bifrost.core.config.Config;
import no.vegvesen.dia.bifrost.core.config.ConfigLoader;
import no.vegvesen.dia.bifrost.core.services.S3ServiceUsingMinio;
import no.vegvesen.dia.bifrost.gateway.controllers.S3ServiceTestdata;
import no.vegvesen.dia.bifrost.gateway.controllers.TestMulitpartFile;
import org.springframework.http.HttpStatus;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;

class S3ServiceUsingMinioTest extends S3ServiceTestdata {

    public S3ServiceUsingMinioTest() throws IOException {
    }

    //@Test
    public void shall_upload_file() throws FileNotFoundException {
        Config config = ConfigLoader.fromFile(new File(getClass().getClassLoader().getResource("test_config.yml").getFile()));
        S3ServiceUsingMinio s3ServiceUsingMinio = new S3ServiceUsingMinio(config);
        HttpStatus httpStatus = s3ServiceUsingMinio.upload(bucketName, new TestMulitpartFile[]{file1, file2});
        assertEquals(HttpStatus.OK, httpStatus);
    }

}
