package com.spring.schoolApplication.exception;

public class StudentCourseDoesntExistException extends RuntimeException {
    public StudentCourseDoesntExistException(String message) {
        super(message);
    }
}
