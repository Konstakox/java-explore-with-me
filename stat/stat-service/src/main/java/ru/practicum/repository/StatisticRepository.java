package ru.practicum.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ru.practicum.dto.StatOutputDto;
import ru.practicum.model.Statistic;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface StatisticRepository extends JpaRepository<Statistic, Integer> {
    @Query("select new ru.practicum.dto.StatOutputDto(s.app, s.uri, count (distinct s.ip)) " +
            " from Statistic s where (s.uri in ?3 or ?3 is null) " +
            "and s.timestamp between ?1 and ?2 group by s.app, s.uri " +
            "order by count(distinct s.ip) desc")
    List<StatOutputDto> getStatDataWithUniqueIp(LocalDateTime start, LocalDateTime end, List<String> uris);

    @Query("select new ru.practicum.dto.StatOutputDto(s.app, s.uri, count (s.ip)) " +
            " from Statistic s where (s.uri in ?3 or ?3 is null) " +
            "and s.timestamp between ?1 and ?2 group by s.app, s.uri " +
            "order by count(s.ip) desc")
    List<StatOutputDto> getStatData(LocalDateTime start, LocalDateTime end, List<String> uris);
}
