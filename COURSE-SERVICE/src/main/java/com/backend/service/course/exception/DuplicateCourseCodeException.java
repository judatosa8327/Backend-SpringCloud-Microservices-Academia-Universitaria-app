package com.backend.service.course.exception;

public class DuplicateCourseCodeException extends RuntimeException {

    public DuplicateCourseCodeException(String message) {
        super(message);
    }
}
