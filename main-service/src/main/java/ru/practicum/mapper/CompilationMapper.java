package ru.practicum.mapper;

import lombok.experimental.UtilityClass;
import ru.practicum.dto.compliiation.CompilationDto;
import ru.practicum.dto.compliiation.NewCompilationDto;
import ru.practicum.dto.compliiation.UpdateCompilationRequest;
import ru.practicum.model.Compilation;
import ru.practicum.model.Event;

import java.util.stream.Collectors;

@UtilityClass
public class CompilationMapper {

    public Compilation toCompilation(NewCompilationDto dto) {
        return Compilation.builder()
                .pinned(dto.getPinned())
                .title(dto.getTitle())
                .events(dto.getEvents().stream().map(CompilationMapper::makeEvent).collect(Collectors.toSet()))
                .build();
    }

    public Compilation toCompilationUpdate(UpdateCompilationRequest dto) {
        return Compilation.builder()
                .pinned(dto.getPinned())
                .title(dto.getTitle())
                .events(dto.getEvents().stream().map(CompilationMapper::makeEvent).collect(Collectors.toSet()))
                .build();
    }

    public CompilationDto toCompilationDto(Compilation compilation) {
        return CompilationDto.builder()
                .events(compilation.getEvents().stream().map(EventMapper::toEventShortDto).collect(Collectors.toSet()))
                .id(compilation.getId())
                .pinned(compilation.getPinned())
                .title(compilation.getTitle())
                .build();
    }

    private Event makeEvent(Integer id) {
        return Event.builder()
                .id(id)
                .build();
    }
}
