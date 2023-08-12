package ru.practicum.exeption;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.CONFLICT)
public class MyIncorrectStateException extends RuntimeException {
    public MyIncorrectStateException(String message) {
        super(message);
    }
}
