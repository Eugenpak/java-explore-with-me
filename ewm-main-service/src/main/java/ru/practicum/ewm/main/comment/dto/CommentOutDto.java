package ru.practicum.ewm.main.comment.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.practicum.ewm.main.event.dto.EventShortDto;

import java.time.LocalDateTime;

import static ru.practicum.ewm.main.event.dto.EventShortDto.DATE_TIME_FORMAT;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CommentOutDto {
    private Long id;
    private String comment;
    private String creatorName;
    private EventShortDto event;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = DATE_TIME_FORMAT)
    private LocalDateTime created;
}
