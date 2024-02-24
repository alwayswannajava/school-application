package com.spring.schoolApplication.exception;

public class StudentDoesntExistException extends Exception{
    public StudentDoesntExistException(String message) {
        super(message);
    }
}
