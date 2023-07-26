package no.vegvesen.dia.bifrost.contract.exception;

public class BadRequestException extends RuntimeException implements ErrorMessageGetter {

    private static final long serialVersionUID = -8872126149167144347L;
    public static final int HTTP_CODE = 400;
    private final ErrorMessage errorMessage;

    public BadRequestException(ErrorMessage errorMessage) {
        this.errorMessage = errorMessage;
    }

    @Override
    public ErrorMessage getErrorMessage() {
        return this.errorMessage;
    }
}
