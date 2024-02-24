package com.spring.schoolApplication.exception;

public class GroupIdIsLessThanZeroException extends Exception{
    public GroupIdIsLessThanZeroException() {
    }

    public GroupIdIsLessThanZeroException(String message) {
        super(message);
    }
}
