package no.vegvesen.dia.bifrost.core.file;

import io.minio.GetObjectArgs;
import io.minio.errors.*;
import no.vegvesen.dia.bifrost.contract.exception.ErrorMessage;
import no.vegvesen.dia.bifrost.contract.exception.InternalServerError;
import no.vegvesen.dia.bifrost.contract.exception.NotFoundException;
import no.vegvesen.dia.bifrost.core.context.ServiceContextIF;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

import java.io.IOException;
import java.io.InputStream;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

public class OperateWithBlob implements FileOperation {

    private static final Logger LOGGER = LogManager.getLogger(OperateWithBlob.class);

    private final String contentType;
    private final ServiceContextIF serviceContextIF;

    public OperateWithBlob(String contentType, ServiceContextIF serviceContextIF) {
        this.contentType = contentType;
        this.serviceContextIF = serviceContextIF;
    }

    @Override
    public ServiceContextIF context() {
        return this.serviceContextIF;
    }

    @Override
    public Logger logger() {
        return LOGGER;
    }

    @Override
    public String contentType() {
        return contentType;
    }

    @Override
    public ResponseEntity<InputStreamResource> fetchObject(String bucket, String path) throws Exception {
        try (InputStream is = context().s3client()
                .getObject(GetObjectArgs.builder().bucket(bucket)
                        .object(path).build())) {
            return ResponseEntity.ok()
                    .contentType(MediaType.valueOf(contentType))
                    .body(new InputStreamResource(is));
        } catch (InvalidKeyException e) {
            LOGGER.error("Error: " + e.getMessage(), e);
            throw new NotFoundException(ErrorMessage.create()
                    .setMessage("Given file: " + bucket + "/" + path + " don't exist on the S3 Object Storage!")
                    .setDeveloperMessage(e.getMessage()));
        } catch (ErrorResponseException e) {
            LOGGER.error("Error: " + e.getMessage(), e);
            if( e.getMessage().toLowerCase().contains("specified key does not exist") ) {
                throw new NotFoundException(ErrorMessage.create()
                        .setMessage("Given file: " + bucket + "/" + path + " don't exist on the S3 Object Storage!")
                        .setDeveloperMessage(e.getMessage()));
            } else {
                throw new InternalServerError(e);
            }
        } catch (NoSuchAlgorithmException | InvalidResponseException | ServerException | XmlParserException | InternalException | InsufficientDataException | IOException e) {
            LOGGER.error("Error: " + e.getMessage(), e);
            throw new InternalServerError(e);
        }
    }

}
