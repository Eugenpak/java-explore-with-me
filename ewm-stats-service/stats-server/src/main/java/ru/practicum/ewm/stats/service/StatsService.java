package ru.practicum.ewm.stats.service;

import ru.practicum.ewm.stats.EndpointHit;
import ru.practicum.ewm.stats.ViewStats;


import java.util.List;

public interface StatsService {
    EndpointHit createStat(EndpointHit endpointHit);

    List<ViewStats> getStats(String start, String end, List<String> uris, Boolean unique);
}
