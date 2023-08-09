package no.vegvesen.dia.bifrost.core.feature;

public interface Feature {

    /**
     * Returns the status of the feature, whether it is enabled or not.
     *
     * @return true if the feature is enabled, false otherwise
     */
    boolean isEnabled();

    /**
     * Returns the name of the feature.
     *
     * @return the name of the feature
     */
    String asString();

}
