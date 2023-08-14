package ru.practicum.exeption;

public class MyIncorrectRequestException extends RuntimeException {
    public MyIncorrectRequestException(String message) {
        super(message);
    }
}
