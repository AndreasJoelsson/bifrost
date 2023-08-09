package no.vegvesen.dia.bifrost.gateway.controllers;

import no.vegvesen.dia.bifrost.contract.exception.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

/**
 * CustomGlobalExceptionHandler class provides centralized exception handling across
 * all the controller methods within the application. It extends the
 * {@link ResponseEntityExceptionHandler} to provide customized responses based
 * on different exception types.
 *
 * The class is annotated with @ControllerAdvice, making it a centralized point for
 * exception handling. This ensures a consistent response structure and behavior
 * across different parts of the application.
 */
@ControllerAdvice
public class CustomGlobalExceptionHandler extends ResponseEntityExceptionHandler {

    /**
     * Handles the BadRequestException and returns a customized error message.
     *
     * @param ex      The exception to handle.
     * @param request The current web request.
     * @return A ResponseEntity containing the error message and the BAD_REQUEST status code.
     */
    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<ErrorMessage> customHandleBadRequest(Exception ex, WebRequest request) {
        return new ResponseEntity<>(((ErrorMessageGetter) ex).getErrorMessage(), HttpStatus.BAD_REQUEST);
    }

    /**
     * Handles the ForbiddenException and returns a customized error message.
     *
     * @param ex      The exception to handle.
     * @param request The current web request.
     * @return A ResponseEntity containing the error message and the FORBIDDEN status code.
     */
    @ExceptionHandler(ForbiddenException.class)
    public ResponseEntity<ErrorMessage> customHandleForbiddenException(Exception ex, WebRequest request) {
        return new ResponseEntity<>(((ErrorMessageGetter) ex).getErrorMessage(), HttpStatus.FORBIDDEN);
    }

    /**
     * Handles the InternalServerError and returns a customized error message.
     *
     * @param ex      The exception to handle.
     * @param request The current web request.
     * @return A ResponseEntity containing the error message and the INTERNAL_SERVER_ERROR status code.
     */
    @ExceptionHandler(InternalServerError.class)
    public ResponseEntity<ErrorMessage> customHandleInternalServerError(Exception ex, WebRequest request) {
        return new ResponseEntity<>(((ErrorMessageGetter) ex).getErrorMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    /**
     * Handles the NotFoundException and returns a customized error message.
     *
     * @param ex      The exception to handle.
     * @param request The current web request.
     * @return A ResponseEntity containing the error message and the NOT_FOUND status code.
     */
    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<ErrorMessage> customHandleNotFoundException(Exception ex, WebRequest request) {
        return new ResponseEntity<>(((ErrorMessageGetter) ex).getErrorMessage(), HttpStatus.NOT_FOUND);
    }

}
