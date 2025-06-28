package ru.practicum.ewm.main.compilation.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import ru.practicum.ewm.main.event.dto.EventShortDto;

import java.util.List;

@Data
@AllArgsConstructor
public class CompilationResponseDto {
    private Long id;

    private List<EventShortDto> events;

    private Boolean pinned;

    private String title;
}
