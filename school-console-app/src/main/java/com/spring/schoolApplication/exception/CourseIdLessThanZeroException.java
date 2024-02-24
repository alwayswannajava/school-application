package com.spring.schoolApplication.exception;

public class CourseIdLessThanZeroException extends Exception{
    public CourseIdLessThanZeroException(String message) {
        super(message);
    }
}
