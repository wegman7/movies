package com.example.polls.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class ResourceNotFoundExceptionTest extends RuntimeException {
    public ResourceNotFoundExceptionTest() {
        super();
    }
    public ResourceNotFoundExceptionTest(String message, Throwable cause) {
        super(message, cause);
    }
    public ResourceNotFoundExceptionTest(String message) {
        super(message);
    }
    public ResourceNotFoundExceptionTest(Throwable cause) {
        super(cause);
    }
}
