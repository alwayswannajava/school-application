package com.spring.schoolApplication.exception;

public class StudentIdIsLessThanZeroException extends RuntimeException {
    public StudentIdIsLessThanZeroException(String message) {
        super(message);
    }
}
