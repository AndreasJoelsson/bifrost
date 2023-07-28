package no.vegvesen.dia.bifrost.gateway.controllers;

import no.vegvesen.dia.bifrost.contract.exception.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class CustomGlobalExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<ErrorMessage> customHandleBadRequest(Exception ex, WebRequest request) {
        return new ResponseEntity<>(((ErrorMessageGetter)ex).getErrorMessage(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ForbiddenException.class)
    public ResponseEntity<ErrorMessage> customHandleForbiddenException(Exception ex, WebRequest request) {
        return new ResponseEntity<>(((ErrorMessageGetter)ex).getErrorMessage(), HttpStatus.FORBIDDEN);
    }

    @ExceptionHandler(InternalServerError.class)
    public ResponseEntity<ErrorMessage> customHandleInternalServerError(Exception ex, WebRequest request) {
        return new ResponseEntity<>(((ErrorMessageGetter)ex).getErrorMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<ErrorMessage> customHandleNotFoundException(Exception ex, WebRequest request) {
        return new ResponseEntity<>(((ErrorMessageGetter)ex).getErrorMessage(), HttpStatus.NOT_FOUND);
    }

}
