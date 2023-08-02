package no.vegvesen.dia.bifrost.core.target;

import no.vegvesen.dia.bifrost.core.config.Config;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class TargetFactory {

    @Autowired
    public TargetFactory(Config config) {
        this(config.getTargetConfigs());
    }

    public TargetFactory(List<TargetConfig> targetConfigList) {

    }
}
