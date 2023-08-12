package ru.practicum.client;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import ru.practicum.dto.StatInputDto;
import ru.practicum.dto.StatOutputDto;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

@Service
@Slf4j
public class StatClient {
    private final String url;
    private final RestTemplate restTemplate;

    public StatClient(@Value("${stats-server.url}") String url, RestTemplateBuilder builder) {
        this.url = url;
        this.restTemplate = builder.build();
    }

    public void saveHit(StatInputDto statInputDto) {
        log.info("Запрос на сохранение {}", statInputDto);
        restTemplate.postForLocation(url + "/hit", statInputDto);
    }

//    public void saveHit(StatInputDto statInputDto) {
//        HttpHeaders headers = new HttpHeaders();
//        headers.setContentType(MediaType.APPLICATION_JSON);
//
//        HttpEntity<StatInputDto> request = new HttpEntity<>(statInputDto, headers);
//        restTemplate.exchange(url + "/hit", HttpMethod.POST, request, StatInputDto.class);
//    }

    public List<StatOutputDto> getStats(LocalDateTime start, LocalDateTime end, List<String> uris, boolean unique) {
        log.info("Запрос на получение статистики с {} по {}, идентификатор сервиса {}, уникальность IP - {}", start, end, uris, unique);

        ResponseEntity<StatOutputDto[]> list = restTemplate
                .getForEntity(url + "/stats?start={start}&end={end}&uris={uris}&unique={unique}",
                        StatOutputDto[].class);
        return Arrays.asList(Objects.requireNonNull(list.getBody()));
    }
}
