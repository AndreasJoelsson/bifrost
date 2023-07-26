package no.vegvesen.dia.bifrost.contract.exception;

public class NotFoundException extends RuntimeException implements ErrorMessageGetter {

    private static final long serialVersionUID = -8872126149167144347L;
    public static final int HTTP_CODE = 404;
    private final ErrorMessage errorMessage;

    public NotFoundException(ErrorMessage errorMessage) {
        this.errorMessage = errorMessage;
    }

    @Override
    public ErrorMessage getErrorMessage() {
        return this.errorMessage;
    }
}
