package ru.practicum.controller.event;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.client.StatClient;
import ru.practicum.dto.event.EventFullDto;
import ru.practicum.dto.event.EventShortDto;
import ru.practicum.mapper.StatInputDtoMapper;
import ru.practicum.constant.SortEvent;
import ru.practicum.service.event.EventService;

import javax.servlet.http.HttpServletRequest;
import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;
import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/events")
@RequiredArgsConstructor
@Validated
@Slf4j
public class PublicEventController {

    private final EventService eventService;
    private final StatClient statClient;

    @GetMapping
    public List<EventShortDto> getEventsFiltered(
            @RequestParam(required = false) String text,
            @RequestParam(required = false) List<Integer> categories,
            @RequestParam(required = false) Boolean paid,
            @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") LocalDateTime rangeStart,
            @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") LocalDateTime rangeEnd,
            @RequestParam(defaultValue = "false") Boolean onlyAvailable,
            @RequestParam(required = false) SortEvent sort,
            @RequestParam(defaultValue = "0") @PositiveOrZero Integer from,
            @RequestParam(defaultValue = "10") @Positive Integer size,
            HttpServletRequest request) {
        log.info("Публичный запрос событий по параметрам: text {}, categories {}, paid {}, rangeStart {}, rangeEnd {}, " +
                        "onlyAvailable {}, sort {}, from {}, size {}",
                text, categories, paid, rangeStart, rangeEnd, onlyAvailable, sort, from, size);
        List<EventShortDto> result = eventService.getEventsFiltered(text, categories, paid, rangeStart,
                rangeEnd, onlyAvailable, sort, from, size);
        log.info("В контроллер вернулся результат {} запрос событий по параметрам", result);
        log.info("отправка в статклиент {} ", request);
        statClient.saveHit(StatInputDtoMapper.toStatInputDto(request));
        return result;
    }

    @GetMapping("{id}")
    public EventFullDto getEventById(
            @PathVariable @Positive Integer id, HttpServletRequest request) {
        log.info("Публичный запрос события по id {}", id);
        EventFullDto result = eventService.getEventById(id);
        statClient.saveHit(StatInputDtoMapper.toStatInputDto(request));
        return result;
    }
}
