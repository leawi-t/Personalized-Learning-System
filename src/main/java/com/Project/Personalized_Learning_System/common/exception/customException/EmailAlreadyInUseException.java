package com.Project.Personalized_Learning_System.common.exception.customException;

public class EmailAlreadyInUseException extends RuntimeException {
    public EmailAlreadyInUseException(String message) {
        super(message);
    }
}