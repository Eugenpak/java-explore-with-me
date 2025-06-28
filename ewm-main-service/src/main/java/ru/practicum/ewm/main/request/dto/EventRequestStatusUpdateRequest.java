package ru.practicum.ewm.main.request.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;
import ru.practicum.ewm.main.request.entity.enums.ParticipationRequestStatus;

import java.util.List;

@Data
public class EventRequestStatusUpdateRequest {
    @NotNull
    private List<Long> requestIds;

    @NotNull
    private ParticipationRequestStatus status;
}
