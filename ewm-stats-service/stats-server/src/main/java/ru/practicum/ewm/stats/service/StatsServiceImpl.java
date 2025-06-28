package ru.practicum.ewm.stats.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.ewm.stats.EndpointHit;
import ru.practicum.ewm.stats.ViewStats;
import ru.practicum.ewm.stats.dao.StatsRepository;
import ru.practicum.ewm.stats.entity.Stat;
import ru.practicum.ewm.stats.exception.ValidationException;
import ru.practicum.ewm.stats.mapper.StatMapper;

import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Collections;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
@Slf4j
public class StatsServiceImpl implements StatsService {
    private final StatsRepository statsRepository;
    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    @Override
    @Transactional
    public EndpointHit createStat(EndpointHit endpointHit) {
        log.info("ESS createStat() с endpointHit: {}", endpointHit);
        Stat stat = statsRepository.save(StatMapper.toStats(endpointHit));
        return StatMapper.toStatsDtoInput(stat);
    }

    @Override
    public List<ViewStats> getStats(String startStr, String endStr, List<String> uris, Boolean unique) {
        String decodedStart = URLDecoder.decode(startStr, StandardCharsets.UTF_8);
        String decodedEnd = URLDecoder.decode(endStr, StandardCharsets.UTF_8);

        LocalDateTime start = LocalDateTime.parse(decodedStart, FORMATTER);
        LocalDateTime end = LocalDateTime.parse(decodedEnd, FORMATTER);
        log.info("ESS getStats() с start: {}, end: {}, uris: {}, unique: {}",start,end,uris,unique);

        if (start.isAfter(end)) {
            throw new ValidationException("Дата начала и дата окончания не могут быть равны друг другу.");
        }

        if (uris == null) {
            uris = Collections.emptyList();
        }

        boolean urisEmpty = uris.isEmpty();
        List<ViewStats> viewStatsList;

        if (Boolean.TRUE.equals(unique)) {
            // Считаем уникальные IP
            viewStatsList = statsRepository.getUniqueStats(start, end, uris, urisEmpty);
        } else {
            // Считаем все запросы
            viewStatsList = statsRepository.getAllStats(start, end, uris, urisEmpty);
        }
        log.info("ESS getStats() отправлено viewStatsList: {}",viewStatsList);
        return viewStatsList;
    }
}