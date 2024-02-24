package com.spring.schoolApplication.exception;

public class StudentCourseDoesntExistException extends Exception {
    public StudentCourseDoesntExistException(String message) {
        super(message);
    }
}
