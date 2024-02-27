package com.spring.schoolApplication.exception;

public class GroupExistsException extends RuntimeException {
    public GroupExistsException(String message) {
        super(message);
    }
}
