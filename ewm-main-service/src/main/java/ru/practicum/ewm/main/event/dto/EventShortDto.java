package ru.practicum.ewm.main.event.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import ru.practicum.ewm.main.category.dto.CategoryResponseDto;
import ru.practicum.ewm.main.user.dto.UserDtoWithoutEmail;

import java.time.LocalDateTime;

@Data
public class EventShortDto {
    public static final String DATE_TIME_FORMAT = "yyyy-MM-dd HH:mm:ss";

    private Long id;

    private UserDtoWithoutEmail initiator;

    private CategoryResponseDto category;

    private Integer confirmedRequests;

    private String title;

    private String annotation;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = DATE_TIME_FORMAT)
    private LocalDateTime eventDate;

    private Boolean paid;

    private Integer views;
}
