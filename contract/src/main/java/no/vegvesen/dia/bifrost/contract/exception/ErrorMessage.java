package no.vegvesen.dia.bifrost.contract.exception;

import java.util.Optional;

public class ErrorMessage {

    // The response code that is returned.
    int status;
    // Mirror of the status unless it is catched and converted to an internalt server error. Then it is the base error code.
    int code;
    // Message to display to the end user.
    String message;
    // Message to link to the log for developer debugging.
    String developerMessage;
    // Potential link to explain more about the error message.
    String link;

    public static ErrorMessage create() {
        return new ErrorMessage();
    }

    private ErrorMessage() {

    }

    public int getStatus() {
        return status;
    }

    public ErrorMessage setStatus(int status) {
        this.status = status;
        return this;
    }

    public int getCode() {
        return code;
    }

    public ErrorMessage setCode(Integer code) {
        Optional.ofNullable(code).ifPresent(c -> this.code = c);
        return this;
    }

    public String getMessage() {
        return message;
    }

    public ErrorMessage setMessage(String message) {
        this.message = message;
        return this;
    }

    public String getDeveloperMessage() {
        return developerMessage;
    }

    public ErrorMessage setDeveloperMessage(String developerMessage) {
        this.developerMessage = developerMessage;
        return this;
    }

    public String getLink() {
        return link;
    }

    public ErrorMessage setLink(String link) {
        this.link = link;
        return this;
    }

    @Override
    public String toString() {
        return "ErrorMessage{" +
                "status=" + status +
                ", code=" + code +
                ", message='" + message + '\'' +
                ", developerMessage='" + developerMessage + '\'' +
                ", link='" + link + '\'' +
                '}';
    }
}
