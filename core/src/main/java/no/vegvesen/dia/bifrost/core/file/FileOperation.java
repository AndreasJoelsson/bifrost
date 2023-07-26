package no.vegvesen.dia.bifrost.core.file;

import io.minio.ObjectWriteResponse;
import io.minio.PutObjectArgs;
import io.minio.RemoveObjectArgs;
import io.minio.errors.*;
import no.vegvesen.dia.bifrost.contract.exception.ErrorMessage;
import no.vegvesen.dia.bifrost.contract.exception.InternalServerError;
import no.vegvesen.dia.bifrost.contract.exception.NotFoundException;
import no.vegvesen.dia.bifrost.contract.pojo.MinioObjectWriteResponse;
import no.vegvesen.dia.bifrost.core.context.ServiceContextIF;
import org.apache.logging.log4j.Logger;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.ResponseEntity;

import java.io.IOException;
import java.io.InputStream;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

public interface FileOperation {

    /**
     * Method to get the service context file.
     *
     * @return Service Context to operate on.
     */
    ServiceContextIF context();

    /**
     * Get the logger interface, helped for default implementation.
     *
     * @return Logger
     */
    Logger logger();

    /**
     * Method to get the contentype, helper for default implementaiton.
     *
     * @return String with content type.
     */
    String contentType();

    /**
     * Provide method to generate a Response from getting a bucket and path.
     *
     * @param bucket Bucket the file lies in.
     * @param path   The virtual path where the file lies under the bucket.
     * @return Response object that is returned to the user.
     * @throws Exception Handles any exceptions thrown in operations getting the object.
     */
    ResponseEntity<InputStreamResource> fetchObject(String bucket, String path) throws Exception;

    /**
     * Provide method to store a file to the bucket.
     *
     * @param bucket   Bucket the file should be stored in.
     * @param path     The virtual path where the file is to be stored under the bucket.
     * @param stream   stream to the file.
     * @param fileSize size of the file uploaded.
     * @return Response object that is returned to the user.
     * @throws Exception Handles any exceptions thrown in operations storing the object.
     */
    default ResponseEntity<MinioObjectWriteResponse> storeObject(String bucket,
                                 String path,
                                 InputStream stream,
                                 long fileSize) throws Exception {
        try {
            logger().info("Storing object to {} using path {} size {} and ctype {}",
                    bucket, path, fileSize, contentType());
            ObjectWriteResponse objectWriteResponse = context().s3client().putObject(PutObjectArgs.builder()
                    .bucket(bucket)
                    .object(path)
                    .contentType(contentType())
                    .stream(stream, fileSize, 5 * 1024 * 1024) // https://docs.min.io/docs/minio-server-limits-per-tenant.html
                    .build());
            return ResponseEntity.ok(new MinioObjectWriteResponse(objectWriteResponse));
        } catch (InvalidKeyException e) {
            logger().error("Error: " + e.getMessage(), e);
            throw new NotFoundException(ErrorMessage.create()
                    .setMessage("Given file: " + bucket + "/" + path + " don't exist on the S3 Object Storage!")
                    .setDeveloperMessage(e.getMessage()));
        } catch (ErrorResponseException | InsufficientDataException | InternalException | InvalidResponseException | IOException | XmlParserException | ServerException | NoSuchAlgorithmException e) {
            logger().error("Error: " + e.getMessage(), e);
            throw new InternalServerError(e);
        }
    }

    /**
     * Provide method to generate a Response from deleting a bucket and path.
     *
     * @param bucket Bucket the file lies in.
     * @param path   The virtual path where the file lies under the bucket.
     * @return Response object that is returned to the user.
     * @throws Exception Handles any exceptions thrown in operations getting the object.
     */
    default ResponseEntity<Void> deleteObject(String bucket, String path) throws Exception {
        try {
            context().s3client().removeObject(RemoveObjectArgs.builder()
                    .bucket(bucket)
                    .object(path)
                    .build());
            return ResponseEntity.ok().build();
        } catch (InvalidKeyException e) {
            logger().error("Error: " + e.getMessage(), e);
            throw new NotFoundException(ErrorMessage.create()
                    .setMessage("Given file: " + bucket + "/" + path + " don't exist on the S3 Object Storage!")
                    .setDeveloperMessage(e.getMessage()));
        } catch (ErrorResponseException | InsufficientDataException | InternalException | InvalidResponseException | IOException | XmlParserException | ServerException | NoSuchAlgorithmException e) {
            logger().error("Error: " + e.getMessage(), e);
            throw new InternalServerError(e);
        }
    }

}
