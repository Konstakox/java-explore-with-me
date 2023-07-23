package ru.practicum.mapper;

import ru.practicum.dto.StatInputDto;
import ru.practicum.dto.StatOutputDto;
import lombok.experimental.UtilityClass;
import ru.practicum.model.Statistic;
import ru.practicum.model.StatisticDto;

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

    public StatOutputDto toStatOutputDto(StatisticDto statisticDto) {
        return StatOutputDto.builder()
                .app(statisticDto.getApp())
                .uri(statisticDto.getUri())
                .hits(Math.toIntExact(statisticDto.getHits()))
                .build();
    }
}
