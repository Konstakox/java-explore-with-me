package ru.practicum.client;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import ru.practicum.dto.StatInputDto;
import ru.practicum.dto.StatOutputDto;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

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

    public List<StatOutputDto> getStats(String start, String end, List<String> uris, Boolean unique) {
        log.info("Запрос на получение статистики с {} по {}, идентификатор сервиса {}, уникальность IP - {}", start, end, uris, unique);

        StringBuilder uriBuilder = new StringBuilder(url + "/stats?start={start}&end={end}");
        Map<String, Object> parameters = Map.of(
                "start", start,
                "end", end);

        if (uris != null && !uris.isEmpty()) {
            for (String uri : uris) {
                uriBuilder.append("&uris=").append(uri);
            }
        }

        if (unique != null) {
            uriBuilder.append("&unique=").append(unique);
        }

        Object responseBody = restTemplate.getForEntity(
                uriBuilder.toString(),
                Object.class, parameters).getBody();

        List<StatOutputDto> stats = new ArrayList<>();
        if (responseBody != null) {
            List<Map<String, Object>> body = (List<Map<String, Object>>) responseBody;
            if (body != null && body.size() > 0) {
                for (Map<String, Object> s : body) {
                    StatOutputDto statOutputDto = StatOutputDto.builder()
                            .app(s.get("app").toString())
                            .uri(s.get("uri").toString())
                            .hits(((Number) s.get("hits")).longValue())
                            .build();
                    stats.add(statOutputDto);
                }
            }
        }
        return stats;
    }
}
