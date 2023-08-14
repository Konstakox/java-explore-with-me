package ru.practicum.controller.event;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.dto.event.*;
import ru.practicum.dto.event.EventRequestStatusUpdateResultDto;
import ru.practicum.dto.request.ParticipationRequestDto;
import ru.practicum.service.event.EventService;
import ru.practicum.service.request.RequestService;

import javax.validation.Valid;
import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;
import java.util.List;

@RestController
@RequestMapping("/users/{userId}/events")
@RequiredArgsConstructor
@Validated
@Slf4j
public class PrivateEventController {

    private final EventService eventService;
    private final RequestService requestService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public EventFullDto createEvent(
            @PathVariable @Positive Integer userId,
            @RequestBody @Valid NewEventDto newEventDto) {
        log.info("Запрос на создание события {}", newEventDto);
        return eventService.createEvent(userId, newEventDto);
    }

    @PatchMapping("{eventId}")
    public EventFullDto updateEventUser(
            @PathVariable @Positive Integer userId,
            @PathVariable @Positive Integer eventId,
            @RequestBody @Valid UpdateEventUserRequest updateEventUserRequest) {
        log.info("Запрос пользователя с id {} на изменение события с id {}, на {}", userId, eventId, updateEventUserRequest);
        return eventService.updateEventUser(userId, eventId, updateEventUserRequest);
    }

    @PatchMapping("{eventId}/requests")
    public EventRequestStatusUpdateResultDto updateEventRequestsStatus(
            @PathVariable @Positive Integer userId,
            @PathVariable @Positive Integer eventId,
            @RequestBody @Valid EventRequestStatusUpdateRequest eventRequestStatusUpdateRequest) {
        log.info("Запрос пользователя с id {} на изменение статуса события с id {}, на {}", userId, eventId, eventRequestStatusUpdateRequest);
        return requestService.updateEventRequestsStatus(userId, eventId, eventRequestStatusUpdateRequest);
    }

    @GetMapping
    public List<EventShortDto> getAllEventsByUser(
            @PathVariable @Positive Integer userId,
            @RequestParam(defaultValue = "0") @PositiveOrZero Integer from,
            @RequestParam(defaultValue = "10") @Positive Integer size) {
        log.info("Запрос своих событий пользователем id {}, from {}, size {}", userId, from, size);
        return eventService.getAllEventsByUser(userId, from, size);
    }

    @GetMapping("{eventId}")
    public EventFullDto getEventByUser(
            @PathVariable @Positive Integer userId,
            @PathVariable @Positive Integer eventId) {
        log.info("Запрос своего события пользователем id {}, событие id {}", userId, eventId);
        return eventService.getEventByUser(userId, eventId);
    }

    @GetMapping("/{eventId}/requests")
    public List<ParticipationRequestDto> getUserEventRequests(
            @PathVariable @Positive Integer userId,
            @PathVariable @Positive Integer eventId) {
        return requestService.getEventRequests(userId, eventId);
    }
}
