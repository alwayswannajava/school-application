package com.spring.schoolApplication.exception;

public class GroupDoesntExistException extends RuntimeException {
    public GroupDoesntExistException(String message) {
        super(message);
    }
}
