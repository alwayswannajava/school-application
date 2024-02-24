package com.spring.schoolApplication.exception;

public class GroupDoesntExistException extends Exception {
    public GroupDoesntExistException(String message) {
        super(message);
    }
}
