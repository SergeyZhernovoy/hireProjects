package ru.biblio.web.registration;

public class EmailExistsException extends Throwable {
    public EmailExistsException(String message) {
        super(message);
    }
}
