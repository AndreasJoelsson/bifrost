package no.vegvesen.dia.bifrost.core.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.yaml.snakeyaml.Yaml;
import org.yaml.snakeyaml.constructor.Constructor;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.Optional;

@Configuration
public class ConfigLoader {

    @Bean
    public Config appConfig() {
        try {
            return fromEnvironment("APP_CONFIG");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return new Config();
        }
    }

    public static Config fromStream(InputStream inputStream) {
        Yaml yaml = new Yaml(new Constructor(Config.class));
        return (Config) Optional.ofNullable(yaml.load(inputStream)).orElse(Config.empty());
    }

    public static Config fromFile(File input) throws FileNotFoundException {
        return fromStream(new FileInputStream(input));
    }

    public static Config fromEnvironment(String environment) throws FileNotFoundException {
        return fromFile(new File(System.getenv(environment)));
    }

}
