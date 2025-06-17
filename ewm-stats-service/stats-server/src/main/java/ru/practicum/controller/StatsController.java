package ru.practicum.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.EndpointHit;
import ru.practicum.ViewStats;
import ru.practicum.service.StatsService;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequiredArgsConstructor
@Slf4j
@Validated
public class StatsController {
    private final StatsService statsService;

    @PostMapping("/hit")
    @ResponseStatus(HttpStatus.CREATED)
    public EndpointHit createStat(@RequestBody @Valid EndpointHit endpointHit) {
        log.info("Пришел POST запрос /hit с телом: {}", endpointHit);
        EndpointHit createdStat = statsService.createStat(endpointHit);
        log.info("Отправлен ответ /items с телом: {}", createdStat);
        return createdStat;
    }

    @GetMapping("/stats")
    @ResponseStatus(HttpStatus.OK)
    public List<ViewStats> getStats(
            @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") LocalDateTime start,
            @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") LocalDateTime end,
            @RequestParam(required = false) List<String> uris,
            @RequestParam(defaultValue = "false") Boolean unique) {
        log.info("Пришел GET запрос /stats с параметрами: start={}, end={}, uris={}, unique={}",
                start, end, uris, unique);
        List<ViewStats> stats = statsService.getStats(start, end, uris, unique);
        log.info("Отправлен ответ /stats с телом: {}", stats);
        return stats;
    }
}
