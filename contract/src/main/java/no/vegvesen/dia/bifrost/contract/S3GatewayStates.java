package no.vegvesen.dia.bifrost.contract;

public enum S3GatewayStates {
    UPLOADED("object-uploaded"),
    DELETED("object-deleted"),
    FAILED("gateway-operation-failed"),
    ;

    private String state;

    S3GatewayStates(String state) {
        this.state = state;
    }
}
