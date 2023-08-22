package no.vegvesen.dia.bifrost.core.file;

import org.springframework.web.multipart.MultipartFile;

import java.util.Objects;

public class Valid {

    public static boolean validMultiPartFile(MultipartFile file) {
        switch (Objects.requireNonNull(file.getContentType())) {
            case "application/json":
            case "application/xml":
            case "application/yaml":
            case "text/xml":
            {
                return true;
            }
            default:
                return false;
        }
    }

}
