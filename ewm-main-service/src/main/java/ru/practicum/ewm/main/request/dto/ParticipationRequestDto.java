package ru.practicum.ewm.main.request.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import ru.practicum.ewm.main.request.entity.enums.ParticipationRequestStatus;

import java.time.LocalDateTime;

import static ru.practicum.ewm.main.event.dto.EventShortDto.DATE_TIME_FORMAT;

@Data
@AllArgsConstructor
public class ParticipationRequestDto {
    private Long id;

    private Long requester;

    private Long event;

    private ParticipationRequestStatus status;

    @JsonFormat(pattern = DATE_TIME_FORMAT)
    private LocalDateTime created;
}
