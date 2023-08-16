package ru.practicum.exeption;

public class MyIncorrectCommentException extends RuntimeException {
    public MyIncorrectCommentException(String message) {
        super(message);
    }
}
