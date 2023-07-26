package no.vegvesen.dia.bifrost.core.context;

import io.minio.MinioClient;
import no.vegvesen.dia.bifrost.core.feature.Feature;

import java.util.function.BiConsumer;

/**
 * The ServiceContextIF interface defines the contract for services that need to maintain context.
 * <p>
 * It provides methods to retrieve a {@link FileCipher} for encryption and decryption,
 * <p>
 * check for enabled features, retrieve a {@link MinioClient} for S3 operations,
 * <p>
 * a {@link SignatureHolder} for signature verification and
 * <p>
 * a method for populating data using a {@link BiConsumer}.
 * <p>
 * It also provides methods to check if a key is present, put and get {@link SignatureHandler} values.
 */
public interface ServiceContextIF {

    /**
     * Check if the specified {@link Feature} is enabled.
     *
     * @param feature the feature to check
     * @return true if the feature is enabled, false otherwise
     */
    default boolean isFeatureEnabled(Feature feature) {
        return feature.isEnabled();
    }

    /**
     * Retrieves the {@link MinioClient} for S3 operations.
     *
     * @return the {@link MinioClient}
     */
    MinioClient s3client();

}
