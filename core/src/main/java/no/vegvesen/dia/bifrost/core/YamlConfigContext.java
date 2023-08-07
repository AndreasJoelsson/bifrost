package no.vegvesen.dia.bifrost.core;

import com.fasterxml.jackson.databind.JsonNode;
import no.vegvesen.dia.bifrost.core.config.Config;
import no.vegvesen.dia.bifrost.core.services.DataPublisher;
import no.vegvesen.dia.bifrost.core.services.S3Config;
import no.vegvesen.dia.bifrost.core.services.S3ServiceLocalFilesystem;
import no.vegvesen.dia.bifrost.core.services.S3ServiceUsingMinio;
import no.vegvesen.dia.bifrost.core.target.TargetConfig;
import no.vegvesen.dia.bifrost.core.target.TargetFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.util.Objects;

@Service
public class YamlConfigContext implements Context {

    private final Config config;
    private final TargetFactory targetFactory;
    private final DataPublisher s3publisher;

    @Autowired
    public YamlConfigContext(Config config) {
        this.config = config;
        this.targetFactory = new TargetFactory(config);
        S3Config s3Config = config.getS3Config();
        switch (s3Config.getImplementation()) {
            case MINIO -> this.s3publisher = new S3ServiceUsingMinio(config);
            case LOCAL_FILE_SYSTEM -> this.s3publisher = new S3ServiceLocalFilesystem();
            default -> throw new RuntimeException("No valid config provided for S3 Implementation.");
        }
    }

    @Override
    public Config config() {
        return this.config;
    }

    @Override
    public HttpStatus publish(String target, MultipartFile file) {
        TargetConfig targetConfig = this.targetFactory.create(target);
        switch (Objects.requireNonNull(targetConfig.getAction())) {
            case S3 -> {
                return this.s3publisher.publish(targetConfig.getName(), file);
            }
            case KAFKA -> {
                return HttpStatus.BAD_REQUEST;
            }
            default -> {
                return HttpStatus.INTERNAL_SERVER_ERROR;
            }
        }
    }

    @Override
    public HttpStatus publish(String target, String objectName, JsonNode node) {
        TargetConfig targetConfig = this.targetFactory.create(target);
        switch (Objects.requireNonNull(targetConfig.getAction())) {
            case S3 -> {
                return this.s3publisher.publish(targetConfig.getName(), objectName, node);
            }
            case KAFKA -> {
                return HttpStatus.BAD_REQUEST;
            }
            default -> {
                return HttpStatus.INTERNAL_SERVER_ERROR;
            }
        }
    }

    @Override
    public HttpStatus publish(String target, String objectName, String mediaType, InputStream stream) {
        TargetConfig targetConfig = this.targetFactory.create(target);
        switch (Objects.requireNonNull(targetConfig.getAction())) {
            case S3 -> {
                return this.s3publisher.publish(targetConfig.getName(), objectName, mediaType, stream);
            }
            case KAFKA -> {
                return HttpStatus.BAD_REQUEST;
            }
            default -> {
                return HttpStatus.INTERNAL_SERVER_ERROR;
            }
        }
    }
}