package ru.practicum.exeption;

public class MyNotFoundException extends RuntimeException {
    public MyNotFoundException(String message) {
        super(message);
    }
}
