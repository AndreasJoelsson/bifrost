package no.vegvesen.dia.bifrost.core;

import com.fasterxml.jackson.databind.JsonNode;
import no.vegvesen.dia.bifrost.core.config.Config;
import no.vegvesen.dia.bifrost.core.file.Extension;
import no.vegvesen.dia.bifrost.core.file.Valid;
import no.vegvesen.dia.bifrost.core.services.*;
import no.vegvesen.dia.bifrost.core.target.TargetConfig;
import no.vegvesen.dia.bifrost.core.target.TargetFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.net.http.HttpResponse;
import java.util.Objects;
import java.util.UUID;

@Service
public class YamlConfigContext implements Context {

    private final Config config;
    private final TargetFactory targetFactory;
    private final DataPublisher s3publisher;

    @Autowired
    public YamlConfigContext(Config config) {
        this.config = config;
        this.targetFactory = new TargetFactory(config);
        S3Config s3Config = Objects.requireNonNull(config.getS3Config());
        switch (Objects.requireNonNull(s3Config.getImplementation())) {
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
    public PublishResponse publish(String target, MultipartFile file) {
        TargetConfig targetConfig = this.targetFactory.create(target);
        if (!Valid.validMultiPartFile(file)) {
            return new PublishResponse(HttpStatus.NOT_IMPLEMENTED, null, null, null,
                    "Invalid content type: " + file.getContentType() + "!");
        }
        switch (Objects.requireNonNull(targetConfig.getAction())) {
            case S3 -> {
                String fileName = UUID.randomUUID() + Extension.extension(file);
                return new PublishResponse(this.s3publisher.publish(targetConfig.getName(), fileName, file),
                        targetConfig.getAction(), targetConfig.getName(), fileName, "");
            }
            case KAFKA -> {
                return new PublishResponse(HttpStatus.BAD_REQUEST, null, null, null,
                        "Kafka support not implemented yet.");
            }
            default -> {
                return new PublishResponse(HttpStatus.INTERNAL_SERVER_ERROR, null, null, null,
                        "No implementation found for " + targetConfig.getAction().toString() + ".");
            }
        }
    }

    @Override
    public PublishResponse publish(String target, String objectName, JsonNode node) {
        TargetConfig targetConfig = this.targetFactory.create(target);
        switch (Objects.requireNonNull(targetConfig.getAction())) {
            case S3 -> {
                return new PublishResponse(this.s3publisher.publish(targetConfig.getName(), objectName, node),
                        targetConfig.getAction(), targetConfig.getName(), objectName, "");
            }
            case KAFKA -> {
                return new PublishResponse(HttpStatus.BAD_REQUEST, null, null, null,
                        "Kafka support not implemented yet.");
            }
            default -> {
                return new PublishResponse(HttpStatus.INTERNAL_SERVER_ERROR, null, null, null,
                        "No implementation found for " + targetConfig.getAction().toString() + ".");
            }
        }
    }

    @Override
    public PublishResponse publish(String target, String objectName, String mediaType, InputStream stream) {
        TargetConfig targetConfig = this.targetFactory.create(target);
        switch (Objects.requireNonNull(targetConfig.getAction())) {
            case S3 -> {
                return new PublishResponse(this.s3publisher.publish(targetConfig.getName(), objectName, mediaType, stream),
                        targetConfig.getAction(), targetConfig.getName(), objectName, "");
            }
            case KAFKA -> {
                return new PublishResponse(HttpStatus.BAD_REQUEST, null, null, null,
                        "Kafka support not implemented yet.");
            }
            default -> {
                return new PublishResponse(HttpStatus.INTERNAL_SERVER_ERROR, null, null, null,
                        "No implementation found for " + targetConfig.getAction().toString() + ".");
            }
        }
    }
}
