package ru.practicum.controller.request;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.dto.request.ParticipationRequestDto;
import ru.practicum.service.request.RequestService;

import javax.validation.constraints.Positive;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/users/{userId}/requests")
@Validated
@Slf4j
public class PrivateRequestController {

    private final RequestService requestService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ParticipationRequestDto createRequest(@PathVariable @Positive Integer userId,
                                                 @RequestParam @Positive Integer eventId) {
        log.info("Запрос на создание заявки от пользователя с id {} для события с id {}", userId, eventId);
        return requestService.createRequest(userId, eventId);
    }

    @GetMapping
    public List<ParticipationRequestDto> getUserRequests(@PathVariable @Positive Integer userId) {
        log.info("Запрос заявок пользователя с id {}", userId);
        return requestService.getAll(userId);
    }

    @PatchMapping("{requestId}/cancel")
    public ParticipationRequestDto cancelRequest(@PathVariable @Positive Integer userId,
                                                 @PathVariable @Positive Integer requestId) {
        log.info("Запрос пользователя с id {} на отмену заявки с id {}", userId, requestId);
        return requestService.cancelRequest(userId, requestId);
    }
}
