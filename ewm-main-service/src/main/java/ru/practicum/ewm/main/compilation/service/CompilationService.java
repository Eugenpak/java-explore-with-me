package ru.practicum.ewm.main.compilation.service;

import ru.practicum.ewm.main.compilation.dto.CompilationRequestDto;
import ru.practicum.ewm.main.compilation.dto.CompilationResponseDto;
import ru.practicum.ewm.main.compilation.dto.UpdateCompilationDto;

import java.util.List;

public interface CompilationService {
    CompilationResponseDto createCompilation(CompilationRequestDto compilationRequestDto);

    void deleteCompilation(Long compId);

    CompilationResponseDto updateCompilation(UpdateCompilationDto updateCompilationDto, Long compId);

    List<CompilationResponseDto> getAllCompilations(Boolean pinned, int from, int size);

    CompilationResponseDto getCompilationById(Long compId);
}
