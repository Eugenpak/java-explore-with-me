package ru.practicum.ewm.main.compilation.mapper;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import ru.practicum.ewm.main.compilation.dto.CompilationRequestDto;
import ru.practicum.ewm.main.compilation.dto.CompilationResponseDto;
import ru.practicum.ewm.main.compilation.entity.Compilation;
import ru.practicum.ewm.main.event.dto.EventShortDto;
import ru.practicum.ewm.main.event.entity.Event;

import java.util.List;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class CompilationMapper {
    public static Compilation mapToCompilation(CompilationRequestDto dto, List<Event> events) {
        return new Compilation(
                null,
                events,
                dto.getPinned(),
                dto.getTitle()
        );
    }

    public static CompilationResponseDto mapToCompilationDto(Compilation compilation, List<EventShortDto> eventShortDtoList) {
        return new CompilationResponseDto(
                compilation.getId(),
                eventShortDtoList,
                compilation.getPinned(),
                compilation.getTitle()
        );
    }
}
