package no.vegvesen.dia.bifrost.core.feature;

import java.util.Optional;

/**
 * Enumeration class representing different features that can be enabled or disabled.
 * <p>
 * The features are read from the environment variables and the value of the variable
 * <p>
 * should be "true" to enable the feature.
 */
public enum FeatureFromEnvironment implements Feature {

    /**
     * Feature for write operations.
     */
    FEATURE_WRITE("FEATURE_WRITE"),
    /**
     * Feature for delete operations.
     */
    FEATURE_DELETE("FEATURE_DELETE"),
    /**
     * Feature for file encryption.
     */
    FEATURE_FILE_ENCRYPTION("FEATURE_FILE_ENCRYPTION"),
    /**
     * Feature for file encryption without hashing.
     */
    FEATURE_FILE_ENCRYPTION_NO_HASH("FEATURE_FILE_ENCRYPTION_NO_HASH");
    private final String feature;
    private final boolean enabled;

    /**
     * Creates a new FeatureFromEnvironment object with the given feature name.
     * The feature will be enabled if the corresponding environment variable is set
     * to "true".
     *
     * @param feature the name of the feature
     */
    FeatureFromEnvironment(final String feature) {
        this.feature = feature;
        this.enabled = Optional.ofNullable(System.getenv(feature)).orElse("")
                .equalsIgnoreCase("true");
    }

    /**
     * Returns whether the feature is enabled or not.
     *
     * @return true if the feature is enabled, false otherwise
     */
    @Override
    public boolean isEnabled() {
        return enabled;
    }

    /**
     * Returns the name of the feature.
     *
     * @return the name of the feature
     */
    @Override
    public String asString() {
        return this.feature;
    }

    /**
     * Returns a string representation of the feature, including its name and
     * whether it is enabled or not.
     *
     * @return a string representation of the feature
     */
    @Override
    public String toString() {
        return "Feature: " + asString() + " is " + (isEnabled() ? "Enabled" : "Disabled");
    }

}
