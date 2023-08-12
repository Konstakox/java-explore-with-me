package ru.practicum.exeption;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class MyIncorrectDataTimeException extends RuntimeException {
    public MyIncorrectDataTimeException(String message) {
        super(message);
    }
}
