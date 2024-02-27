package com.spring.schoolApplication.exception;

public class StudentDoesntExistException extends RuntimeException{
    public StudentDoesntExistException(String message) {
        super(message);
    }
}
