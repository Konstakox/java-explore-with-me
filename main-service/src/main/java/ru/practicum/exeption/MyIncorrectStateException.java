package ru.practicum.exeption;

public class MyIncorrectStateException extends RuntimeException {
    public MyIncorrectStateException(String message) {
        super(message);
    }
}
