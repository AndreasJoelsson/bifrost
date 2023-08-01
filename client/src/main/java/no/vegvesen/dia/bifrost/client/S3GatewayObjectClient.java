package no.vegvesen.dia.bifrost.client;

import no.vegvesen.dia.bifrost.contract.S3GatewayStates;
import no.vegvesen.dia.bifrost.core.config.Config;
import no.vegvesen.dia.bifrost.core.services.S3Service;
import no.vegvesen.dia.bifrost.core.services.S3ServiceUsingMinio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.multipart.MultipartFile;

public class S3GatewayObjectClient implements ObjectClient {
/*    private final S3Service s3Service;

    @Autowired
    public S3GatewayObjectClient(Config config) {
        s3Service = new S3ServiceUsingMinio(config);
    }
    public S3GatewayObjectClient(S3Service s3Service) {
        this.s3Service = s3Service;
    }

    @Override
    public S3GatewayStates upload(String bucket, String path, MultipartFile[] files) {
        HttpStatus httpStatus = s3Service.upload(bucket, path, files);
        if (httpStatus.value() == 200) {
            return S3GatewayStates.UPLOADED;
        }

        return S3GatewayStates.FAILED;
    }
*/
}
