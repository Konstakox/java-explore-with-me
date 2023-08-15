package ru.practicum.exeption;

import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import javax.validation.ConstraintViolationException;
import java.util.Map;
import java.util.Objects;

@Slf4j
@RestControllerAdvice
public class MyExceptionHandler {

    @ExceptionHandler({DataIntegrityViolationException.class, HttpMessageNotReadableException.class,
            MyCategoryNotEmpty.class, MyIncorrectData.class, MyIncorrectRequestException.class,
            MyIncorrectStateException.class})
    @ResponseStatus(HttpStatus.CONFLICT)
    public Map<String, String> handleConflictException(Throwable exception) {
        log.error(exception.getMessage(), exception);
        return Map.of("error", Objects.requireNonNull(exception.getMessage()));
    }

    @ExceptionHandler({MethodArgumentNotValidException.class, ConstraintViolationException.class,
            MissingServletRequestParameterException.class, MethodArgumentTypeMismatchException.class,
            MyIncorrectDataTimeException.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Map<String, String> handleBadRequestException(Throwable exception) {
        log.error(exception.getMessage(), exception);
        return Map.of("error", exception.getMessage());
    }

    @ExceptionHandler({MyNotFoundException.class})
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public Map<String, String> handleNotFoundException(Throwable exception) {
        log.error(exception.getMessage(), exception);
        return Map.of("error", exception.getMessage());
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.SERVICE_UNAVAILABLE)
    public Map<String, String> handleUnknownException(Throwable exception) {
        log.error(exception.getMessage(), exception);
        return Map.of("error", exception.getMessage());
    }
}
