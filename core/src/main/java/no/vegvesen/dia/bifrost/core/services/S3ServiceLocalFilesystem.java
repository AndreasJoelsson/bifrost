package no.vegvesen.dia.bifrost.core.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Objects;

@Service
public class S3ServiceLocalFilesystem implements S3Service {
    private static final Logger log = LoggerFactory.getLogger(S3ServiceLocalFilesystem.class);
    public static final String SIMULATED_BUCKET_NAME = "vegfoto-prod-2021";

    @Autowired
    public S3ServiceLocalFilesystem() {
    }

    @Override
    public HttpStatus upload(String bucket, String path, MultipartFile[] files) {

        for (MultipartFile mpf : files) {
            // TODO where is the best place to use the encryption service?
            /*
            if (doEncryption()) {
                InputStream inputStream = mpf.getInputStream();
                CipherInputStream cipherInputStream = fileCipher.encryptStream(inputStream);
                saveFile(inputStream, Path.of(bucket,path));
            }
            */

            // TODO and how to wrap the encryption functionality??
            /*
            if (doEncryption()) {
                InputStream inputStream = mpf.getInputStream();
                FileOutputStream outputStream = new FileOutputStream(bucket + path);
                encryptService.encrypt(inputStream, outputStream);
                saveFile(outputStream);
            }
             */

            HttpStatus httpStatus = saveFile(mpf, Path.of(bucket, path));
            if (httpStatus != HttpStatus.OK) {
                return httpStatus;
            }
        }
        return HttpStatus.OK;
    }

    private HttpStatus saveFile(MultipartFile file, Path path) {
        log.debug("path: {}, original filename: {}, filename: {}, size: {}", path, file.getOriginalFilename(), file.getName(), file.getSize());

        if (Objects.equals(chooseStrategy(path), "OVERWRITE")) {
            log.info("writing only one file with given filename");
            try {
                Files.createDirectories(path.getParent());
                FileOutputStream fileOutputStream = new FileOutputStream(path.toFile());
                fileOutputStream.write(file.getBytes());
            } catch (IOException e) {
                log.error("failed to save file", e);
                return HttpStatus.INTERNAL_SERVER_ERROR;
            }
            return HttpStatus.OK;
        }

        try {
            Path directories = Files.createDirectories(path);

            FileOutputStream fileOutputStream = new FileOutputStream(directories.resolve(file.getOriginalFilename()).toFile());
            fileOutputStream.write(file.getBytes());
        } catch (IOException e) {
            log.error("failed to save file", e);
            return HttpStatus.INTERNAL_SERVER_ERROR;
        }

        return HttpStatus.OK;
    }

    private String chooseStrategy(Path path) {
        return null;
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
}
