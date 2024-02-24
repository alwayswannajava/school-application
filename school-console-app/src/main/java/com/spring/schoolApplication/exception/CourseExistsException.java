package com.spring.schoolApplication.exception;

public class CourseExistsException extends Exception {

    public CourseExistsException(String message){
        super(message);
    }
}
