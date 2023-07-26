package no.vegvesen.dia.bifrost.core.services;

import org.springframework.http.HttpStatus;
import org.springframework.web.multipart.MultipartFile;

public interface S3Service {

    HttpStatus upload(String bucket, String path, MultipartFile[] files);

}
