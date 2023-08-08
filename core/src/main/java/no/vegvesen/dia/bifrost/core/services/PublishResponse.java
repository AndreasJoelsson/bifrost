package no.vegvesen.dia.bifrost.core.services;

import no.vegvesen.dia.bifrost.core.target.ActionType;
import org.springframework.http.HttpStatus;

public record PublishResponse(HttpStatus httpStatus, ActionType type, String bucket, String path, String message) {

}
