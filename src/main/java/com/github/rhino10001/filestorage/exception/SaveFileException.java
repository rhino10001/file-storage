package com.github.rhino10001.filestorage.exception;

public class SaveFileException extends RuntimeException {

    public SaveFileException(String message) {
        super(message);
    }

    public SaveFileException(String message, Throwable cause) {
        super(message, cause);
    }
}
