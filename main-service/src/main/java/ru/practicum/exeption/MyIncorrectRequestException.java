package ru.practicum.exeption;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.CONFLICT)
public class MyIncorrectRequestException extends RuntimeException {
    public MyIncorrectRequestException(String message) {
        super(message);
    }
}
