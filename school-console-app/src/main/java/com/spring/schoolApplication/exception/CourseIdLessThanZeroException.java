package com.spring.schoolApplication.exception;

public class CourseIdLessThanZeroException extends RuntimeException{
    public CourseIdLessThanZeroException(String message) {
        super(message);
    }
}
