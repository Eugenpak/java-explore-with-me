package ru.practicum.ewm.main.event.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import ru.practicum.ewm.main.category.dto.CategoryResponseDto;
import ru.practicum.ewm.main.event.entity.Location;
import ru.practicum.ewm.main.event.entity.enums.EventState;
import ru.practicum.ewm.main.user.dto.UserDtoWithoutEmail;

import java.time.LocalDateTime;

import static ru.practicum.ewm.main.event.dto.EventShortDto.DATE_TIME_FORMAT;

@Data
public class EventFullDto {
    private Long id;

    private UserDtoWithoutEmail initiator;

    private CategoryResponseDto category;

    private Integer confirmedRequests;

    private Location location;

    private String title;

    private String annotation;

    private String description;

    private EventState state;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = DATE_TIME_FORMAT)
    private LocalDateTime eventDate;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = DATE_TIME_FORMAT)
    private LocalDateTime createdOn;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = DATE_TIME_FORMAT)
    private LocalDateTime publishedOn;

    private Integer participantLimit;

    private Boolean paid;

    private Boolean requestModeration;

    private Integer views;
}
