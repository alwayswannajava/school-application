package com.spring.schoolApplication.exception;

public class StudentExistsException extends Exception {
    public StudentExistsException(String message) {
        super(message);
    }
}
