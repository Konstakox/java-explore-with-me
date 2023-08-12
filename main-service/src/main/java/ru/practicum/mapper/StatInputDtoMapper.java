package ru.practicum.mapper;

import ru.practicum.dto.StatInputDto;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;

public class StatInputDtoMapper {
    public static StatInputDto toStatInputDto(HttpServletRequest request) {
        return StatInputDto.builder()
                .app("${stats-server.url}")
                .uri(request.getRequestURI())
                .ip(request.getRemoteAddr())
                .timestamp(LocalDateTime.now())
                .build();
    }
}
