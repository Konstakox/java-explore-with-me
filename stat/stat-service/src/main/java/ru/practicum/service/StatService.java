package ru.practicum.service;

import ru.practicum.dto.StatInputDto;
import ru.practicum.dto.StatOutputDto;

import java.time.LocalDateTime;
import java.util.List;

public interface StatService {

    void saveHit(StatInputDto statInputDto);

    List<StatOutputDto> getStats(LocalDateTime start, LocalDateTime end, List<String> uris, Boolean unique);
}
