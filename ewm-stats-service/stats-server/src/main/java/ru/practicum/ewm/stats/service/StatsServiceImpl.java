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

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
@Slf4j
public class StatsServiceImpl implements StatsService {
    private final StatsRepository statsRepository;

    @Override
    @Transactional
    public EndpointHit createStat(EndpointHit endpointHit) {
        log.info("ESS createStat() с endpointHit: {}", endpointHit);
        Stat stat = statsRepository.save(StatMapper.toStats(endpointHit));
        return StatMapper.toStatsDtoInput(stat);
    }

    @Override
    public List<ViewStats> getStats(LocalDateTime start, LocalDateTime end, List<String> uris, Boolean unique) {
        log.info("ESS getStats() с start: {}, end: {}, uris: {}, unique: {}",start,end,uris,unique);

        if (start.isAfter(end)) {
            throw new ValidationException("Дата начала и дата окончания не могут быть равны друг другу.");
        }

        if (uris == null) {
            uris = Collections.emptyList();
        }

        boolean urisEmpty = uris.isEmpty();

        if (Boolean.TRUE.equals(unique)) {
            // Считаем уникальные IP
            return statsRepository.getUniqueStats(start, end, uris, urisEmpty);
        } else {
            // Считаем все запросы
            return statsRepository.getAllStats(start, end, uris, urisEmpty);
        }
    }
}