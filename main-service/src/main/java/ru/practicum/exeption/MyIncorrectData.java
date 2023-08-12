package ru.practicum.exeption;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.CONFLICT)
public class MyIncorrectData extends RuntimeException {
    public MyIncorrectData(String message) {
        super(message);
    }
}
