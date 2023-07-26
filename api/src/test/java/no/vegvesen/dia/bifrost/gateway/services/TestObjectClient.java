package no.vegvesen.dia.bifrost.gateway.services;

import io.minio.MinioClient;

public class TestObjectClient { //implements ObjectClient {

    public MinioClient client() {
        // TODO https://hub.docker.com/r/minio/minio
        return new MinioClient.Builder()
                .credentials("myKey", "mySecret")
                .endpoint("url-til-dockercontainer")
                .build();
    }
}
