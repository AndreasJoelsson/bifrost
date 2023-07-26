package no.vegvesen.dia.bifrost.client;

import no.vegvesen.dia.bifrost.contract.S3GatewayStates;
import org.springframework.web.multipart.MultipartFile;

public interface ObjectClient {
    S3GatewayStates upload(String bucket, String path, MultipartFile[] files);
}
