package no.vegvesen.dia.bifrost.core.target;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class TargetConfig {
    private final String target;
    private final ActionType action;
    private final String name;

    @JsonCreator
    public TargetConfig(@JsonProperty("target") String target,
                        @JsonProperty("action") ActionType action,
                        @JsonProperty("name") String name) {
        this.target = target;
        this.action = action;
        this.name = name;
    }

    public String getTarget() {
        return target;
    }

    public ActionType getAction() {
        return action;
    }

    public String getName() {
        return name;
    }
}
