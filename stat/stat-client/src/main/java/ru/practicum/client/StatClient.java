package ru.practicum.client;

import ru.practicum.dto.StatInputDto;
import ru.practicum.dto.StatOutputDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
@Slf4j

public class StatClient {
//    @Value("${stats-server.uri}")
    private final String local = "http://localhost:9090";
    private final RestTemplate restTemplate = new RestTemplate();
    DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    public void saveHit(StatInputDto statInputDto) {
        log.info("Запрос на сохранение {}", statInputDto);
        restTemplate.postForLocation(local + "/hit", statInputDto);
    }

//    public List<StatOutputDto> getStats(LocalDateTime start, LocalDateTime end, List<String> uris, boolean unique) {
//        String startFormat = start.format(dateTimeFormatter);
//        String endFormat = end.format(dateTimeFormatter);
//        log.info("Запрос на получение статистики с {} по {}, уникальность IP - {}", startFormat, endFormat, unique);
//
//        ResponseEntity<StatOutputDto[]> list = restTemplate.getForEntity(local + "/stats?start=" + startFormat +
//                        "&end=" + endFormat + "&uris=" + uris + "&unique=" + unique,
//                StatOutputDto[].class);
//        return Arrays.asList(Objects.requireNonNull(list.getBody()));

        public List<StatOutputDto> getStats(LocalDateTime start, LocalDateTime end, List<String> uris, boolean unique) {
            log.info("Запрос на получение статистики с {} по {}, уникальность IP - {}", start, end, unique);

            ResponseEntity<StatOutputDto[]> list = restTemplate
                    .getForEntity(local + "/stats?start={start}&end={end}&uris={uris}&unique={unique}",
                    StatOutputDto[].class);
            return Arrays.asList(Objects.requireNonNull(list.getBody()));

        }
}
