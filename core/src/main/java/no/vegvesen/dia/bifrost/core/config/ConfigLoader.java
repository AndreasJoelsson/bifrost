package no.vegvesen.dia.bifrost.core.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.*;

/**
 * Class responsible for loading application configuration from different sources.
 * It can load configuration from an InputStream, a File, or from an environment variable
 * that points to a file.
 * The configuration is then parsed using the SnakeYAML library.
 */
@Configuration
public class ConfigLoader {

    /**
     * Loads configuration from an InputStream.
     * The contents of the InputStream should be in YAML format and map to the properties of the Config class.
     *
     * @param inputStream the InputStream to load the configuration from.
     * @return the loaded configuration, or an empty Config object if the InputStream is null.
     */
    public static Config fromStream(InputStream inputStream) {
        ObjectMapper mapper = new ObjectMapper(new YAMLFactory());
        try {
            return mapper.readValue(inputStream, Config.class);
        } catch (IOException e) {
            e.printStackTrace();
            return Config.empty();
        }
    }

    /**
     * Loads configuration from a File.
     * The contents of the file should be in YAML format and map to the properties of the Config class.
     *
     * @param input the File to load the configuration from.
     * @return the loaded configuration, or an empty Config object if the file cannot be found.
     * @throws FileNotFoundException if the specified File cannot be found.
     */
    public static Config fromFile(File input) throws FileNotFoundException {
        return fromStream(new FileInputStream(input));
    }

    /**
     * Loads configuration from an environment variable.
     * The value of the environment variable should be the path to a configuration file.
     * The contents of the file should be in YAML format and map to the properties of the Config class.
     *
     * @param environment the name of the environment variable.
     * @return the loaded configuration, or an empty Config object if the configuration file cannot be found.
     * @throws FileNotFoundException if the configuration file specified by the environment variable cannot be found.
     */
    public static Config fromEnvironment(String environment) throws FileNotFoundException {
        return fromFile(new File(System.getenv(environment)));
    }

    /**
     * Loads the application's configuration.
     * The configuration is expected to be a file path specified by the environment variable "APP_CONFIG".
     * If the configuration file cannot be found or an error occurs while reading it, an empty Config object is returned.
     *
     * @return the loaded configuration, or an empty Config object if the configuration file cannot be found.
     */
    @Bean
    public Config appConfig() {
        try {
            return fromEnvironment("APP_CONFIG");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return Config.empty();
        }
    }

}
