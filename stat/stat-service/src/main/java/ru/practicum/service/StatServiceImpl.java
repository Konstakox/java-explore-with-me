package ru.practicum.service;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.practicum.dto.StatInputDto;
import ru.practicum.dto.StatOutputDto;
import ru.practicum.exeption.MyIncorrectDataTimeException;
import ru.practicum.mapper.Mapper;
import ru.practicum.model.Statistic;
import ru.practicum.repository.StatisticRepository;

import java.time.LocalDateTime;
import java.util.List;

@Service
@AllArgsConstructor
@Slf4j
public class StatServiceImpl implements StatService {
    private final StatisticRepository statisticRepository;

    @Override
    public void saveHit(StatInputDto statInputDto) {
        Statistic statistic = Mapper.toStatistic(statInputDto);
        log.info("Запрос на сохранение объекта {}", statistic);
        statisticRepository.save(statistic);
    }

    @Override
    public List<StatOutputDto> getStats(LocalDateTime start, LocalDateTime end, List<String> uris, Boolean unique) {
        if (end.isBefore(start)) {
            log.info("Сервис, время начала позже окончания start {}, end {}", start, end);
            throw new MyIncorrectDataTimeException("время начала позже окончания");
        }
        List<StatOutputDto> result;
        if (unique) {
            result = statisticRepository.getStatDataWithUniqueIp(start, end, uris);
        } else {
            result = statisticRepository.getStatData(start, end, uris);
        }
        return result;
    }
}
