package no.vegvesen.dia.bifrost.core.services;

import com.fasterxml.jackson.databind.JsonNode;
import io.minio.PutObjectArgs;
import io.minio.SnowballObject;
import io.minio.UploadSnowballObjectsArgs;
import no.vegvesen.dia.bifrost.core.config.Config;
import no.vegvesen.dia.bifrost.resources.MinioClientWrapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@Primary()
public class S3ServiceUsingMinio extends MinioClientWrapper implements S3Service {
    private static final Logger log = LoggerFactory.getLogger(S3ServiceUsingMinio.class);

    @Autowired
    public S3ServiceUsingMinio(Config config) {
        super(config);
    }

    @Override
    public HttpStatus upload(String bucketName, String objectName, String contentType, InputStream stream) {
        try {
            minioClient.putObject(
                    PutObjectArgs.builder()
                            .bucket(bucketName)
                            .object(objectName)
                            .stream(stream, stream.available(), -1)
                            .contentType(contentType)
                            .build()
            );
            return HttpStatus.OK;
        } catch (Exception e) {
            e.printStackTrace();
            return HttpStatus.INTERNAL_SERVER_ERROR;
        }
    }
}
