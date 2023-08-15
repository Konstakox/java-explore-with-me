package ru.practicum.exeption;

public class MyIncorrectData extends RuntimeException {
    public MyIncorrectData(String message) {
        super(message);
    }
}
