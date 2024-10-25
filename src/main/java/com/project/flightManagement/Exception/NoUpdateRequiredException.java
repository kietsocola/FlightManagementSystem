package com.project.flightManagement.Exception;

public class NoUpdateRequiredException extends RuntimeException {
    public NoUpdateRequiredException(String message) {
        super(message);
    }
}
