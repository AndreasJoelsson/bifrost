package no.vegvesen.dia.bifrost.contract;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.Instant;

@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public class S3ObjectResponse implements AutoCloseable {

    private Long receivedTimeMillis;
    private Long storedTimeMillis;
    private String fileName;
    private String originalFilename;
    private String bucket;

    public S3ObjectResponse() {
        this("", "");
    }

    public S3ObjectResponse(String fileName) {
        this(fileName, "");
    }

    public S3ObjectResponse(String fileName, String bucket) {
        this.receivedTimeMillis = Instant.now().toEpochMilli();
        this.fileName = fileName;
        this.bucket = bucket;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public void setOriginalFilename(String fileName) {
        this.originalFilename = fileName;
    }

    public void setBucket(String bucket) {
        this.bucket = bucket;
    }

    public Long getReceivedTimeMillis() {
        return receivedTimeMillis;
    }

    public Long getStoredTimeMillis() {
        return storedTimeMillis;
    }

    public String getFileName() {
        return fileName;
    }

    public String getOriginalFilename() {
        return originalFilename;
    }

    public String getBucket() {
        return bucket;
    }

    @Override
    public void close() throws Exception {
        this.storedTimeMillis = Instant.now().toEpochMilli();
    }

    @Override
    public String toString() {
        return "S3ObjectResponse{" +
                "receivedTimeMillis=" + receivedTimeMillis +
                ", storedTimeMillis=" + storedTimeMillis +
                ", fileName='" + fileName + '\'' +
                ", originalFilename='" + originalFilename + '\'' +
                ", bucket='" + bucket + '\'' +
                '}';
    }
}
