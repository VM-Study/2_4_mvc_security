package org.example.app.exceptions;

// Наследуемся от extends Exception
public class BookShelfLoginException extends Exception {
    private final String message;

    public BookShelfLoginException(String message) {
        this.message = message;
    }

    @Override
    public String getMessage() {
        return message;
    }
}
