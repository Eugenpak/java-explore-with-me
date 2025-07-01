package ru.practicum.ewm.main.event.mapper;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import ru.practicum.ewm.main.event.dto.LocationDto;
import ru.practicum.ewm.main.event.entity.Location;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class LocationMapper {
    public static Location mapToLocation(LocationDto dto) {
        return new Location(
                null,
                dto.getLat(),
                dto.getLon()
        );
    }

    public static LocationDto mapToLocationDto(Location location) {
        return new LocationDto(
                location.getLat(),
                location.getLon()
        );
    }
}
