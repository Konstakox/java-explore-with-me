package ru.practicum.service.event;

import ru.practicum.dto.event.*;
import ru.practicum.model.SortEvent;
import ru.practicum.model.State;

import java.time.LocalDateTime;
import java.util.List;

public interface EventService {
    List<EventFullDto> getEventsByParameters(List<Integer> users, List<State> states, List<Integer> categories,
                                             LocalDateTime rangeStart, LocalDateTime rangeEnd, Integer from, Integer size);

    EventFullDto updateEventAdmin(Integer eventId, UpdateEventAdminRequest updateEventAdminRequest);

    EventFullDto createEvent(Integer userId, NewEventDto newEventDto);

    EventFullDto updateEventUser(Integer userId, Integer eventId, UpdateEventUserRequest updateEventUserRequest);

    List<EventShortDto> getAllEventsByUser(Integer userId, Integer from, Integer size);

    EventFullDto getEventByUser(Integer userId, Integer eventId);

    List<EventShortDto> getEventsFiltered(String text, List<Integer> categories, Boolean paid,
                                          LocalDateTime rangeStart, LocalDateTime rangeEnd, Boolean onlyAvailable, SortEvent sort, Integer from, Integer size);

    EventFullDto getEventById(Integer id);
}
