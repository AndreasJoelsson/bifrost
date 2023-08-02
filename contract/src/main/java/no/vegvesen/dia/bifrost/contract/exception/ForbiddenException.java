package no.vegvesen.dia.bifrost.contract.exception;

public class ForbiddenException extends RuntimeException implements ErrorMessageGetter {

    public static final int HTTP_CODE = 403;
    private static final long serialVersionUID = -8872126149167144347L;
    private final ErrorMessage errorMessage;

    public ForbiddenException(ErrorMessage errorMessage) {
        this.errorMessage = errorMessage;
    }

    @Override
    public ErrorMessage getErrorMessage() {
        return this.errorMessage;
    }
}
