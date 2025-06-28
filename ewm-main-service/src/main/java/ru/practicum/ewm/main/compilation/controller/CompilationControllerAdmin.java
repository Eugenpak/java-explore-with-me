package ru.practicum.ewm.main.compilation.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.ewm.main.compilation.dto.CompilationRequestDto;
import ru.practicum.ewm.main.compilation.dto.CompilationResponseDto;
import ru.practicum.ewm.main.compilation.dto.UpdateCompilationDto;
import ru.practicum.ewm.main.compilation.service.CompilationService;

@Validated
@RestController
@RequiredArgsConstructor
@RequestMapping("/admin/compilations")
public class CompilationControllerAdmin {
    private final CompilationService compilationService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CompilationResponseDto createCompilation(@Valid @RequestBody CompilationRequestDto compilationRequestDto) {
        return compilationService.createCompilation(compilationRequestDto);
    }

    @DeleteMapping("/{compId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteCompilation(@PathVariable Long compId) {
        compilationService.deleteCompilation(compId);
    }

    @PatchMapping("/{compId}")
    @ResponseStatus(HttpStatus.OK)
    public CompilationResponseDto updateCompilation(@Valid @RequestBody UpdateCompilationDto updateCompilationDto,
                                                    @PathVariable Long compId) {
        return compilationService.updateCompilation(updateCompilationDto, compId);
    }
}
