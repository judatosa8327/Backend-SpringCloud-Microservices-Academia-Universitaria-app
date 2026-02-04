package com.backend.users.exception;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class UserDuplicateException extends RuntimeException {
    public UserDuplicateException(String message) {
        super(message);
    }
}
