package ru.practicum.exeption;

public class MyIncorrectDataTimeException extends RuntimeException {
    public MyIncorrectDataTimeException(String message) {
        super(message);
    }
}
