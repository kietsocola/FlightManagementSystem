package com.project.flightManagement.Exception;

public class ResourceNotFoundException extends RuntimeException {
    public ResourceNotFoundException(String resourceName, int id) {
        super(resourceName + " not found with id: " + id);
    }
}
