package no.vegvesen.dia.bifrost.core.file;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.web.multipart.MultipartFile;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Objects;

/**
 * The FileVerifier class provides utility methods to verify the validity
 * of JSON, YAML, and XML files or streams.
 */
public class FileVerifier {

    private static final Logger logger = LoggerFactory.getLogger(FileVerifier.class);

    /** ObjectMapper instance for parsing JSON content. */
    private final static ObjectMapper jsonMapper = new ObjectMapper();

    /** ObjectMapper instance for parsing YAML content. */
    private final static ObjectMapper yamlMapper = new ObjectMapper(new YAMLFactory());

    /**
     * Determines if the provided MultipartFile is valid based on its content type.
     *
     * @param file The MultipartFile to be validated.
     * @return true if the file content is valid for its content type; false otherwise.
     * @throws IOException If there's an error reading the file's content.
     */
    public static boolean isValidContent(MultipartFile file) throws IOException {
        MediaType contentType = MediaType.parseMediaType(Objects.requireNonNull(file.getContentType()));
        try (InputStream stream = file.getInputStream()) {
            return isValidContent(contentType, stream);
        }
    }

    /**
     * Determines if the provided content stream is valid based on the specified MediaType.
     *
     * @param type   The MediaType of the content to be validated (e.g. application/json, application/xml).
     * @param stream The InputStream containing the content to be verified.
     * @return true if the content is valid for the given MediaType; false otherwise.
     */
    public static boolean isValidContent(MediaType type, InputStream stream) {
        return isValidContent(type.toString(), stream);
    }

    /**
     * Verifies the content validity based on its content type.
     *
     * @param type The content type of the input.
     * @param stream The InputStream containing the content to verify.
     * @return true if the content is valid for the given type; false otherwise.
     */
    public static boolean isValidContent(String type, InputStream stream) {
        return switch (type) {
            case "application/json" -> isValidJsonStream(stream);
            case "application/yaml" -> isValidYamlStream(stream);
            case "application/xml", "text/xml" -> isValidXmlStream(stream);
            default -> throw new IllegalArgumentException("Unsupported content type: " + type);
        };
    }

    /**
     * Checks if the provided File contains valid JSON content.
     *
     * @param file The file to verify.
     * @return true if the file contains valid JSON; false otherwise.
     */
    public static boolean isValidJsonFile(File file) {
        try (InputStream is = Files.newInputStream(Paths.get(file.getAbsolutePath()))) {
            return isValidJsonStream(is);
        } catch (IOException e) {
            logger.error("Error validating JSON content for file {}: {}", file.getAbsolutePath(), e.getMessage());
            return false;
        }
    }

    /**
     * Checks if the provided File contains valid YAML content.
     *
     * @param file The file to verify.
     * @return true if the file contains valid YAML; false otherwise.
     */
    public static boolean isValidYamlFile(File file) {
        try (InputStream is = Files.newInputStream(Paths.get(file.getAbsolutePath()))) {
            return isValidYamlStream(is);
        } catch (IOException e) {
            return false;
        }
    }

    /**
     * Checks if the provided File contains valid XML content.
     *
     * @param file The file to verify.
     * @return true if the file contains valid XML; false otherwise.
     */
    public static boolean isValidXmlFile(File file) {
        try (InputStream is = Files.newInputStream(Paths.get(file.getAbsolutePath()))) {
            return isValidXmlStream(is);
        } catch (IOException e) {
            return false;
        }
    }

    /**
     * Checks if the provided InputStream contains valid JSON content.
     *
     * @param stream The InputStream to verify.
     * @return true if the stream contains valid JSON; false otherwise.
     */
    public static boolean isValidJsonStream(InputStream stream) {
        try {
            JsonNode jsonNode = jsonMapper.readTree(stream);
            return jsonNode.isObject() || jsonNode.isArray();
        } catch (IOException e) {
            return false;
        }
    }

    /**
     * Checks if the provided InputStream contains valid YAML content.
     *
     * @param stream The InputStream to verify.
     * @return true if the stream contains valid YAML; false otherwise.
     */
    public static boolean isValidYamlStream(InputStream stream) {
        try (BufferedInputStream bis = new BufferedInputStream(stream)) {
            bis.mark(4096);
            byte[] buffer = new byte[4096];
            int bytesRead = bis.read(buffer);
            bis.reset();

            String contentStart = new String(buffer, 0, bytesRead);
            if (contentStart.trim().startsWith("{") || contentStart.trim().startsWith("[")
                    || contentStart.contains("<?xml") || (contentStart.contains("<") && contentStart.contains(">"))) {
                logger.warn("The provided input stream starts with patterns indicative of JSON or XML.");
                return false;
            }

            yamlMapper.readTree(bis);
            return true;
        } catch (IOException e) {
            logger.error("Error validating YAML content: {}", e.getMessage());
            return false;
        }
    }

    /**
     * Checks if the provided InputStream contains valid XML content.
     *
     * @param stream The InputStream to verify.
     * @return true if the stream contains valid XML; false otherwise.
     */
    public static boolean isValidXmlStream(InputStream stream) {
        try {
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            dBuilder.parse(stream);
            return true;
        } catch (Exception e) {
            logger.error("Error validating XML content: {}", e.getMessage());
            return false;
        }
    }
}