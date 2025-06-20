package ru.practicum.ewm.stats.mapper;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import ru.practicum.ewm.stats.EndpointHit;
import ru.practicum.ewm.stats.entity.Stat;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class StatMapper {
    public static Stat toStats(EndpointHit endpointHit) {
        Stat stat = new Stat();
        stat.setApp(endpointHit.getApp());
        stat.setUri(endpointHit.getUri());
        stat.setIp(endpointHit.getIp());
        stat.setTimestamp(endpointHit.getTimestamp());
        return stat;
    }

    public static EndpointHit toStatsDtoInput(Stat stat) {
        return new EndpointHit(stat.getApp(),
                stat.getUri(),
                stat.getIp(),
                stat.getTimestamp());
    }
}
