package ru.practicum.controller;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.dto.StatInputDto;
import ru.practicum.dto.StatOutputDto;
import ru.practicum.service.StatService;

import javax.validation.Valid;
import java.time.LocalDateTime;
import java.util.List;

@RestController
@AllArgsConstructor
@Slf4j
@Validated
public class StatController {
    private final StatService statService;

    @PostMapping(value = "/hit")
    @ResponseStatus(HttpStatus.CREATED)
    public void saveHit(@Valid @RequestBody StatInputDto statInputDto) {
        log.info("Контроллер, запрос на сохранение объекта {}", statInputDto);
        statService.saveHit(statInputDto);
    }

    @GetMapping("/stats")
    public List<StatOutputDto> getStats(@RequestParam(value = "start") @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") LocalDateTime start,
                                        @RequestParam(value = "end") @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") LocalDateTime end,
                                        @RequestParam(required = false) List<String> uris,
                                        @RequestParam(defaultValue = "false") Boolean unique) {
        log.info("Контроллер, запрос статистики с параметрами: data start: {}, data end: {}, List uris: {}, unique: {}", start, end, uris, unique);
        return statService.getStats(start, end, uris, unique);
    }
}
