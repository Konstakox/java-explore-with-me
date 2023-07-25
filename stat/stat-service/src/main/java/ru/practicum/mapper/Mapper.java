package ru.practicum.mapper;

import lombok.experimental.UtilityClass;
import ru.practicum.dto.StatInputDto;
import ru.practicum.model.Statistic;

@UtilityClass
public class Mapper {
    public Statistic toStatistic(StatInputDto statInputDto) {
        return Statistic.builder()
                .app(statInputDto.getApp())
                .uri(statInputDto.getUri())
                .ip(statInputDto.getIp())
                .timestamp(statInputDto.getTimestamp())
                .build();
    }
}
