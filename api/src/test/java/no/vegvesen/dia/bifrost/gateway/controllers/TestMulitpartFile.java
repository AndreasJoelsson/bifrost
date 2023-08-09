package no.vegvesen.dia.bifrost.gateway.controllers;

import org.springframework.http.MediaType;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;

public class TestMulitpartFile implements MultipartFile {

    private final byte[] file;
    private final String filename;

    public TestMulitpartFile(String filename) throws IOException {
        file = Files.readAllBytes(Paths.get(filename));
        this.filename = filename.substring(filename.lastIndexOf("/") + 1);
    }

    @Override
    public String getName() {
        return "testfile";
    }

    @Override
    public String getOriginalFilename() {
        return filename;
    }

    @Override
    public String getContentType() {
        return MediaType.IMAGE_JPEG_VALUE;
    }

    @Override
    public boolean isEmpty() {
        return file.length == 0;
    }

    @Override
    public long getSize() {
        return file.length;
    }

    @Override
    public byte[] getBytes() {
        return file;
    }

    @Override
    public InputStream getInputStream() {
        return new ByteArrayInputStream(file);
    }

    @Override
    public void transferTo(File dest) {
        System.out.println("no implementation here yet...");
    }
}
