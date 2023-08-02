package no.vegvesen.dia.bifrost.contract.exception;

import org.springframework.web.ErrorResponseException;

public class InternalServerError extends RuntimeException implements ErrorMessageGetter {
    private static final long serialVersionUID = 6437180524337670648L;

    private static final int HTTP_CODE = 500;
    private final ErrorMessage errorMessage;

    public InternalServerError(Throwable exception) {
        this.errorMessage = ErrorMessage.create()
                .setStatus(HTTP_CODE)
                .setCode(getCode(exception))
                .setMessage("Something bad happened. Please try again !!")
                .setDeveloperMessage(exception.getMessage());
    }

    public InternalServerError(String message) {
        this.errorMessage = ErrorMessage.create()
                .setStatus(HTTP_CODE)
                .setMessage(message);
    }

    static Integer getCode(Throwable throwable) {
        if (throwable instanceof ErrorResponseException) {
            try {
                return ((ErrorResponseException) throwable).getStatusCode().value();
            } catch (NullPointerException e) {
                return null;
            }
        }
        return null;
    }

    @Override
    public ErrorMessage getErrorMessage() {
        return this.errorMessage;
    }
}
