package ru.practicum.client;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;
import ru.practicum.dto.StatInputDto;
import ru.practicum.dto.StatOutputDto;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

@RequiredArgsConstructor
@Slf4j
public class StatClient {
    @Value("${stat-client.url}")
    private final String url;
    private final RestTemplate restTemplate = new RestTemplate();

    public void saveHit(StatInputDto statInputDto) {
        log.info("Запрос на сохранение {}", statInputDto);
        restTemplate.postForLocation(url + "/hit", statInputDto);
    }

    public List<StatOutputDto> getStats(LocalDateTime start, LocalDateTime end, List<String> uris, boolean unique) {
        log.info("Запрос на получение статистики с {} по {}, идентификатор сервиса {}, уникальность IP - {}", start, end, uris, unique);

        ResponseEntity<StatOutputDto[]> list = restTemplate
                .getForEntity(url + "/stats?start={start}&end={end}&uris={uris}&unique={unique}",
                        StatOutputDto[].class);
        return Arrays.asList(Objects.requireNonNull(list.getBody()));

    }
}
