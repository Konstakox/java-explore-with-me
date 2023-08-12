package ru.practicum.service.compilation;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.support.PagedListHolder;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.dto.compliiation.CompilationDto;
import ru.practicum.dto.compliiation.NewCompilationDto;
import ru.practicum.dto.compliiation.UpdateCompilationRequest;
import ru.practicum.exeption.MyNotFoundException;
import ru.practicum.mapper.CompilationMapper;
import ru.practicum.model.Compilation;
import ru.practicum.model.Event;
import ru.practicum.repository.CompilationRepository;
import ru.practicum.repository.EventRepository;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;


@Service
@Transactional
@RequiredArgsConstructor
public class CompilationServiceImpl implements CompilationService {

    private final CompilationRepository compilationRepository;
    private final EventRepository eventRepository;

    @Override
    public CompilationDto createCompilation(NewCompilationDto newCompilationDto) {
        Compilation compilation = CompilationMapper.toCompilation(newCompilationDto);
        compilation.setEvents(new HashSet<>(eventRepository.findAllById(
                compilation.getEvents().stream()
                        .map(Event::getId)
                        .collect(Collectors.toList()))));

        return CompilationMapper.toCompilationDto(compilationRepository.save(compilation));
    }


    @Override
    public CompilationDto update(Integer compId, UpdateCompilationRequest updateCompilationRequest) {
        if (updateCompilationRequest.getPinned() == null) {
            updateCompilationRequest.setPinned(false);
        }
        if (updateCompilationRequest.getEvents() == null) {
            updateCompilationRequest.setEvents(new HashSet<>());
        }

        Compilation donor = CompilationMapper.toCompilationUpdate(updateCompilationRequest);
        Compilation recipient = compilationRepository.findById(compId).orElseThrow(
                () -> new MyNotFoundException("Подборка не найдена с id " + compId));
        if (donor.getEvents() != null) recipient.setEvents(donor.getEvents());
        if (donor.getPinned() != null) recipient.setPinned(donor.getPinned());
        if (donor.getTitle() != null) recipient.setTitle(donor.getTitle());

        return CompilationMapper.toCompilationDto(compilationRepository.save(recipient));
    }

    @Override
    public void delete(Integer compId) {
        compilationRepository.findById(compId).orElseThrow(
                () -> new MyNotFoundException("Подборка не найдена с id " + compId));
        compilationRepository.deleteById(compId);
    }

    @Override
    public List<CompilationDto> getAll(Boolean pinned, Integer from, Integer size) {
        PageRequest page = PageRequest.of(from, size);
        List<Compilation> result = new ArrayList<>();
        if (pinned == null) {
            result = compilationRepository.findAll();
        } else {
            result = compilationRepository.findAllByPinned(pinned, page);
        }

        PagedListHolder<CompilationDto> pageOut = new PagedListHolder<>(result
                .stream()
                .map(CompilationMapper::toCompilationDto)
                .collect(Collectors.toList()));
        pageOut.setPageSize(size);
        pageOut.setPage(from);

        return new ArrayList<>(pageOut.getPageList());
    }

    @Override
    public CompilationDto getById(Integer compId) {
        Compilation compilation = compilationRepository.findById(compId).orElseThrow(
                () -> new MyNotFoundException("Подборка событий не найдена с id " + compId));
        return CompilationMapper.toCompilationDto(compilation);
    }
}
