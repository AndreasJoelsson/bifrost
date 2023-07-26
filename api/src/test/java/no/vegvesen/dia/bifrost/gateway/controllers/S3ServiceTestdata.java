package no.vegvesen.dia.bifrost.gateway.controllers;

import no.vegvesen.dia.bifrost.core.services.S3ServiceLocalFilesystem;

import java.io.IOException;

public class S3ServiceTestdata {
    public static final String TESTFILENAME_1 = "bilde.jpg";
    public static final String PATH_TO_TESTFILE_1 = "src/test/resources/" + TESTFILENAME_1;
    public static final String TESTFILENAME_2 = "pauseplass.jpg";
    public static final String PATH_TO_TESTFILE_2 = "src/test/resources/" + TESTFILENAME_2;
    protected final TestMulitpartFile file1 = new TestMulitpartFile(PATH_TO_TESTFILE_1);
    protected final TestMulitpartFile file2 = new TestMulitpartFile(PATH_TO_TESTFILE_2);

    // URL example from existing gateway:
    // https://s3vegbilder.atlas.vegvesen.no/vegfoto-prod-2021/FV00076/S2/D1/F2_2021_05_26/FV00076_S2D1_m07069_f2.jpg
    protected final String bucketName = S3ServiceLocalFilesystem.SIMULATED_BUCKET_NAME;
    protected final String path = "/FV00076/S2/D1/F2_2021_05_26/";
    protected final String providedFilename = "FV00076_S2D1_m07069_f2.jpg";

    public S3ServiceTestdata() throws IOException {
    }
}
