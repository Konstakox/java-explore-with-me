package ru.practicum.exeption;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.CONFLICT)
public class MyCategoryNotEmpty extends RuntimeException {
    public MyCategoryNotEmpty(String message) {
        super(message);
    }
}
