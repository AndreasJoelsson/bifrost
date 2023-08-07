package no.vegvesen.dia.bifrost.core.target;

import no.vegvesen.dia.bifrost.core.config.Config;
import no.vegvesen.dia.bifrost.core.services.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

import static java.util.stream.Collectors.toMap;

@Component
public class TargetFactory {

    public final Map<String, TargetConfig> factory;

    @Autowired
    public TargetFactory(Config config) {
        this(config.getTargetConfigs());
    }

    public TargetFactory(List<TargetConfig> targetConfigList) {
        factory = targetConfigList.stream().collect(toMap(TargetConfig::getTarget, t -> t));
    }

    public TargetConfig create(String target) throws InternalError {
        if(factory.containsKey(target)) {
            return factory.get(target);
        }
        throw new InternalError("Target factory contains no target with name \"" + target + "\"!");
    }

}
