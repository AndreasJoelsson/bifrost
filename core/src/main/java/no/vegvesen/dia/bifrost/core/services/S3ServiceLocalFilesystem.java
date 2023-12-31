package no.vegvesen.dia.bifrost.core.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Objects;

@Service
@ConditionalOnProperty(name = "s3.service.type", havingValue = "local")
public class S3ServiceLocalFilesystem implements DataPublisher {
    public static final String SIMULATED_BUCKET_NAME = "vegfoto-prod-2021";
    private static final Logger log = LoggerFactory.getLogger(S3ServiceLocalFilesystem.class);

    @Autowired
    public S3ServiceLocalFilesystem() {
    }

    public static void deleteAllEmptyFolders(String folder) throws IOException {
        String[] split = folder.split("/");
        for (int i = split.length; i > 0; i--) {
            Files.deleteIfExists(calcPathToDelete(split, i));
        }
    }

    private static Path calcPathToDelete(String[] split, int numberOfSubPaths) {
        String path = "";
        for (int p = 0; p < numberOfSubPaths; p++) {
            path += split[p] + "/";
        }
        return Path.of(path);
    }

    @Override
    public HttpStatus publish(String destination, String objectName, String mediaType, InputStream stream) {
        try {
            return saveFile(stream, Path.of(destination), objectName);
        } catch (IOException e) {
            e.printStackTrace();
            return HttpStatus.INTERNAL_SERVER_ERROR;
        }
    }

    private HttpStatus saveFile(InputStream stream, Path path, String name) throws IOException {
        log.debug("path: {}, filename: {}, size: {}", path, name, stream.available());

        if (Objects.equals(chooseStrategy(path), "OVERWRITE")) {
            log.info("writing only one file with given filename");
            try {
                Files.createDirectories(path.getParent());
                FileOutputStream fileOutputStream = new FileOutputStream(path.toFile());
                fileOutputStream.write(stream.readAllBytes());
            } catch (IOException e) {
                log.error("failed to save file", e);
                return HttpStatus.INTERNAL_SERVER_ERROR;
            }
            return HttpStatus.OK;
        }

        try {
            Path directories = Files.createDirectories(path);

            FileOutputStream fileOutputStream = new FileOutputStream(directories.resolve(name).toFile());
            fileOutputStream.write(stream.readAllBytes());
        } catch (IOException e) {
            log.error("failed to save file", e);
            return HttpStatus.INTERNAL_SERVER_ERROR;
        }

        return HttpStatus.OK;
    }

    private String chooseStrategy(Path path) {
        return null;
    }

}
