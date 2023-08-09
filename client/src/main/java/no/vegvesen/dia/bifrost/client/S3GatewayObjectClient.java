package no.vegvesen.dia.bifrost.client;

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
