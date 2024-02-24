package com.spring.schoolApplication.exception;

public class StudentAlreadyAssignToCourseException extends Exception{
    public StudentAlreadyAssignToCourseException(String message) {
        super(message);
    }
}
