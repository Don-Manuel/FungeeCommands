package org.openredstone.handlers;

public class LoadException extends Exception {
    LoadException(String message, Throwable cause) {
        super(message, cause);
    }

    LoadException(String message) {
        super(message);
    }

    LoadException(Throwable cause) {
        super(cause);
    }
}
