package com.spring.schoolApplication.exception;

public class StudentIdIsLessThanZeroException extends Exception{
    public StudentIdIsLessThanZeroException(String message) {
        super(message);
    }
}
