package ru.practicum.ewm.main.stat;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import ru.practicum.ewm.stats.EndpointHit;
import ru.practicum.ewm.stats.StatsClient;
import ru.practicum.ewm.stats.ViewStats;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

import static ru.practicum.ewm.main.event.dto.EventShortDto.DATE_TIME_FORMAT;

@Service
@Slf4j
@RequiredArgsConstructor
public class StatsServiceImpl  implements StatsService {
    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern(DATE_TIME_FORMAT);

    private final StatsClient statsClient;

    @Override
    public void createStats(final String uri, final String ip) {
        final EndpointHit dtoInput = new EndpointHit("ewm-main-service",
                uri, ip, LocalDateTime.now());
        ResponseEntity<Object> responseEntity;
        try {
            responseEntity = statsClient.hit(dtoInput);
        } catch (Exception e) {
            // Логирование ошибки и проброс исключения
            // Например, используя SLF4J:
            log.error("Failed to send hit to stats-service", e);
            throw new RuntimeException("Failed to send hit to stats-service", e);
        }
        Object body = responseEntity.getBody();

        if (body != null) {
            log.info("EWM-Main-S createStats() statsClient отправил POST с телом EndpointHit: {}", body);
        }
    }

    @Override
    public List<ViewStats> getStats(final List<Long> eventsId, final boolean unique) {
        final String start = LocalDateTime.now().minusYears(20).format(FORMATTER);
        final String end = LocalDateTime.now().plusYears(20).format(FORMATTER);
        final List<String> uris = eventsId.stream()
                .map(id -> String.format("/events/%d", id))
                .collect(Collectors.toList());
        log.info("EWM-Main-S getStats(). statsClient отправил GET с параметрами" +
                " start:{}, end:{}, uris:{}, unique:{}",start,end,uris,unique);
        ResponseEntity<Object> responseEntity = statsClient.getStats(start, end, uris, unique); // ваш ResponseEntity
        Object responseBody = responseEntity.getBody();

        List<ViewStats> viewStatsList;
        if (responseBody instanceof List) {

            List<?> list = (List<?>) responseBody;
            viewStatsList = new ArrayList<>();
            for (int i = 0; i < list.size();i++) {
                LinkedHashMap<String, Object> linkedHashMap = new LinkedHashMap<>();
                linkedHashMap = (LinkedHashMap<String, Object>) list.get(0);
                ViewStats viewStats = new ViewStats();
                viewStats.setApp((String) linkedHashMap.get("app"));
                viewStats.setUri((String) linkedHashMap.get("uri"));
                viewStats.setHits(Long.valueOf((Integer)linkedHashMap.get("hits")));
                viewStatsList.add(viewStats);
            }

        } else {
            // Обработка случая, когда тело ответа не является списком
            viewStatsList = List.of();
        }
        log.info("EWM-Main-S getStats(). statsClient получил GET с viewStatsList:{}",viewStatsList);
        return viewStatsList;
    }

    @Override
    public Map<Long, Long> getView(List<Long> eventsId, boolean unique) {
        log.info("EWM-Main-S getView() -> getStats().");
        final List<ViewStats> stats = getStats(eventsId, unique);
        final Map<Long, Long> views = new HashMap<>();
        for (ViewStats stat : stats) {
            views.put(Long.valueOf(stat.getUri().replace("/events/", "")),
                    stat.getHits());
        }
        log.info("EWM-Main-S getView(). Получен Map<Long, Long>: {}.",views);
        return views;
    }

}