package com.spring.schoolApplication.exception;

public class GroupIdIsLessThanZeroException extends RuntimeException{
    public GroupIdIsLessThanZeroException() {
    }

    public GroupIdIsLessThanZeroException(String message) {
        super(message);
    }
}
