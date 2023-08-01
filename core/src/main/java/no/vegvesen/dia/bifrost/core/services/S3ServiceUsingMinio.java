package no.vegvesen.dia.bifrost.core.services;

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
    public HttpStatus upload(String bucket, String path, MultipartFile[] files) {
        try {
            List<SnowballObject> objects = new ArrayList<>();

            for (MultipartFile mpf : files) {
                // TODO which filename to use for single files... and if only one file, then use minioClient.putObject()?
                objects.add(new SnowballObject(mpf.getOriginalFilename(), mpf.getInputStream(), mpf.getSize(), ZonedDateTime.now()));
            }
            minioClient.uploadSnowballObjects(UploadSnowballObjectsArgs.builder()
                    .bucket(bucket)
                    .objects(objects)
                    .build());
            return HttpStatus.OK;
        } catch (Exception e) {
            log.error("upload to bucket {} failed", bucket, e);
            return HttpStatus.BAD_REQUEST;
        }
    }

}
