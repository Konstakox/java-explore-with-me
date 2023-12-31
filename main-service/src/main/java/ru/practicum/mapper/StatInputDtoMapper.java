package ru.practicum.mapper;

import lombok.experimental.UtilityClass;
import ru.practicum.dto.StatInputDto;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;

@UtilityClass
public class StatInputDtoMapper {
    public StatInputDto toStatInputDto(HttpServletRequest request) {
        return StatInputDto.builder()
                .app("${stats-server.url}")
                .uri(request.getRequestURI())
                .ip(request.getRemoteAddr())
                .timestamp(LocalDateTime.now())
                .build();
    }
}
