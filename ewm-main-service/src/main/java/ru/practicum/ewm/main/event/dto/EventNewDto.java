package ru.practicum.ewm.main.event.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonSetter;
import com.fasterxml.jackson.annotation.Nulls;
import jakarta.validation.constraints.*;
import lombok.Data;
import ru.practicum.ewm.main.event.entity.Location;
import ru.practicum.ewm.main.event.entity.enums.EventState;

import java.time.LocalDateTime;

import static ru.practicum.ewm.main.event.dto.EventShortDto.DATE_TIME_FORMAT;

@Data
public class EventNewDto {
    @NotBlank
    @Size(min = 20, max = 2000)
    private String annotation;

    @NotNull
    private Long category;

    @NotBlank
    @Size(min = 20, max = 7000)
    private String description;

    @NotNull
    @Future
    @JsonFormat(pattern = DATE_TIME_FORMAT)
    private LocalDateTime eventDate;

    @NotNull
    private Location location;

    @JsonSetter(nulls = Nulls.SKIP)
    private Boolean paid = false;

    @PositiveOrZero
    @JsonSetter(nulls = Nulls.SKIP)
    private Integer participantLimit = 0;

    @JsonSetter(nulls = Nulls.SKIP)
    private Boolean requestModeration = true;

    private EventState state;

    @NotBlank
    @Size(min = 3, max = 120)
    private String title;
}
