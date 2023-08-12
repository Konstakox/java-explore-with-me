package ru.practicum.service.compilation;

import ru.practicum.dto.compliiation.CompilationDto;
import ru.practicum.dto.compliiation.NewCompilationDto;
import ru.practicum.dto.compliiation.UpdateCompilationRequest;

import java.util.List;

public interface CompilationService {

    List<CompilationDto> getAll(Boolean pinned, Integer from, Integer size);

    CompilationDto createCompilation(NewCompilationDto newCompilationDto);

    CompilationDto update(Integer compId, UpdateCompilationRequest updateCompilationRequest);

    void delete(Integer compId);

    CompilationDto getById(Integer compId);
}
