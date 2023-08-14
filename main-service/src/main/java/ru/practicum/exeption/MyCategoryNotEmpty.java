package ru.practicum.exeption;

public class MyCategoryNotEmpty extends RuntimeException {
    public MyCategoryNotEmpty(String message) {
        super(message);
    }
}
