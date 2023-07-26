package no.vegvesen.dia.bifrost.client;

import no.vegvesen.dia.bifrost.contract.S3GatewayStates;
import org.springframework.web.multipart.MultipartFile;

// TODO this is only an empty shell right now in order to discuss the client interface
public class S3GatewayAPIClient implements ObjectClient{
    @Override
    public S3GatewayStates upload(String bucket, String path, MultipartFile[] files) {
        return S3GatewayStates.UPLOADED;
    }

}
