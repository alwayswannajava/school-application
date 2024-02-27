package com.spring.schoolApplication.exception;

public class StudentAlreadyAssignToCourseException extends RuntimeException{
    public StudentAlreadyAssignToCourseException(String message) {
        super(message);
    }
}
