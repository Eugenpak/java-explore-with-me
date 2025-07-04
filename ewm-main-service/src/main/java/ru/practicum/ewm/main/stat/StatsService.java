package ru.practicum.ewm.main.stat;

import ru.practicum.ewm.stats.ViewStats;

import java.util.List;
import java.util.Map;

public interface StatsService {
    void createStats(String uri, String ip);

    List<ViewStats> getStats(List<Long> eventsId, boolean unique);

    Map<Long, Long> getView(List<Long> eventsId, boolean unique);
}
