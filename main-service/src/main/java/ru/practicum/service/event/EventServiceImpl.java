package ru.practicum.service.event;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.support.PagedListHolder;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import ru.practicum.client.StatClient;
import ru.practicum.constant.SortEvent;
import ru.practicum.constant.State;
import ru.practicum.dto.StatOutputDto;
import ru.practicum.dto.event.*;
import ru.practicum.exeption.MyIncorrectData;
import ru.practicum.exeption.MyIncorrectDataTimeException;
import ru.practicum.exeption.MyIncorrectStateException;
import ru.practicum.exeption.MyNotFoundException;
import ru.practicum.mapper.EventMapper;
import ru.practicum.model.*;
import ru.practicum.repository.CategoryRepository;
import ru.practicum.repository.EventRepository;
import ru.practicum.repository.LocationRepository;
import ru.practicum.repository.UserRepository;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class EventServiceImpl implements EventService {
    private final EventRepository eventRepository;
    private final LocationRepository locationRepository;
    private final UserRepository userRepository;
    private final CategoryRepository categoryRepository;
    private final StatClient statClient;

    @Override
    public List<EventFullDto> getEventsByParameters(List<Integer> users, List<State> states, List<Integer> categories,
                                                    LocalDateTime rangeStart, LocalDateTime rangeEnd, Integer from, Integer size) {

        if (rangeStart != null && rangeEnd != null && rangeStart.isAfter(rangeEnd)) {
            throw new MyIncorrectDataTimeException("Некорректное время начала " + rangeStart + "и конца" + rangeEnd);
        }

        PageRequest page = PageRequest.of(from, size);
        List<Event> events = eventRepository.findByParameters(users, states, categories,
                rangeStart, rangeEnd, page);

        PagedListHolder<EventFullDto> pageOut = new PagedListHolder<>(events
                .stream()
                .map(EventMapper::toEventFullDto)
                .collect(Collectors.toList()));
        pageOut.setPageSize(size);
        pageOut.setPage(from);

        return new ArrayList<>(pageOut.getPageList());
    }

    @Override
    public EventFullDto updateEventAdmin(Integer eventId, UpdateEventAdminRequest updateEventAdminRequest) {
        if (updateEventAdminRequest.getEventDate() != null
                && updateEventAdminRequest.getEventDate().isBefore(LocalDateTime.now().plusHours(1))) {
            throw new MyIncorrectDataTimeException("Время, не ранее чем через 1 час от текущего.");
        }

        Event recipient = eventRepository.findById(eventId).orElseThrow(
                () -> new MyNotFoundException("Событие не найдено с id" + eventId));
        if (!recipient.getState().equals(State.PENDING)) {
            throw new MyIncorrectStateException("Не требует рассмотрения");
        }

        if (updateEventAdminRequest.getParticipantLimit() != null) {
            if (updateEventAdminRequest.getParticipantLimit() != 0
                    && recipient.getConfirmedRequests() != null
                    && (updateEventAdminRequest.getParticipantLimit() <= recipient.getConfirmedRequests())) {
                throw new MyIncorrectData("Новый лимит участников долженбыть больше либо равен количеству " +
                        "уже одобренных заявок");
            }
        }
        Event donor = EventMapper.toEvent(updateEventAdminRequest);
        recipient = updateEvent(donor, recipient);

        return EventMapper.toEventFullDto(eventRepository.save(recipient));
    }

    private Event updateEvent(Event donor, Event recipient) {
        if (donor.getLocation() != null) {
            Location location = locationRepository.findByLatAndLon(donor.getLocation().getLat(),
                    donor.getLocation().getLon()).orElse(locationRepository.save(donor.getLocation()));
            donor.setLocation(location);
        }
        return EventMapper.updateEvent(donor, recipient);
    }

    @Override
    public EventFullDto createEvent(Integer userId, NewEventDto newEventDto) {
        Event event = EventMapper.toEvent(newEventDto);
        User user = userRepository.findById(userId).orElseThrow(
                () -> new MyNotFoundException("Пользователь не найден с id " + userId));
        Location location = locationRepository.findByLatAndLon(event.getLocation().getLat(),
                event.getLocation().getLon()).orElse(locationRepository.save(event.getLocation()));
        Category category = categoryRepository.findById(event.getCategory().getId()).orElseThrow(
                () -> new MyNotFoundException("Категория не найдена с id " + event.getCategory().getId()));

        event.setInitiator(user);
        event.setConfirmedRequests(0);
        event.setLocation(location);
        event.setCategory(category);
        event.setCreatedOn(LocalDateTime.now());
        event.setPublishedOn(LocalDateTime.now());
        event.setState(State.PENDING);
        event.setViews(0);

        return EventMapper.toEventFullDto(eventRepository.save(event));
    }

    @Override
    public EventFullDto updateEventUser(Integer userId, Integer eventId, UpdateEventUserRequest updateEventUserRequest) {
        if (updateEventUserRequest.getEventDate() != null
                && updateEventUserRequest.getEventDate().isBefore(LocalDateTime.now().plusHours(2))) {
            throw new MyIncorrectDataTimeException("Изменяемое время должно быть не ранее чем через 2 часа от текущего.");
        }
        User user = userRepository.findById(userId).orElseThrow(
                () -> new MyNotFoundException("Пользователь не найден с id " + userId));
        Event donor = EventMapper.toEvent(updateEventUserRequest);
        Event recipient = eventRepository.findById(eventId).orElseThrow(
                () -> new MyNotFoundException("Событие не найдено с id" + eventId));
        if (!recipient.getInitiator().getId().equals(user.getId())) {
            throw new MyNotFoundException("У пользователя с id " + userId + " нет события с id " + recipient.getId());
        }
        if (recipient.getState() == State.PUBLISHED) {
            throw new MyIncorrectStateException("Событие опубликовано, невозможно изменить");
        }

        recipient = updateEvent(donor, recipient);

        return EventMapper.toEventFullDto(eventRepository.save(recipient));
    }

    @Override
    public List<EventShortDto> getAllEventsByUser(Integer userId, Integer from, Integer size) {
        User user = userRepository.findById(userId).orElseThrow(
                () -> new MyNotFoundException("Пользователь не найден с id " + userId));
        PageRequest page = PageRequest.of(from, size);
        List<Event> events = eventRepository.findAllByInitiatorId(user.getId(), page);

        return events.stream()
                .map(EventMapper::toEventShortDto).collect(Collectors.toList());
    }

    @Override
    public EventFullDto getEventByUser(Integer userId, Integer eventId) {
        userRepository.findById(userId).orElseThrow(
                () -> new MyNotFoundException("Пользователь не найден с id " + userId));

        Event event = eventRepository.findById(eventId).orElseThrow(
                () -> new MyNotFoundException("Событие не найдено с id" + eventId));

        return EventMapper.toEventFullDto(event);
    }

    @Override
    public List<EventShortDto> getEventsFiltered(String text, List<Integer> categories, Boolean paid,
                                                 LocalDateTime rangeStart, LocalDateTime rangeEnd, Boolean onlyAvailable,
                                                 SortEvent sort, Integer from, Integer size) {
        if (rangeStart != null && rangeEnd != null && rangeStart.isAfter(rangeEnd)) {
            throw new MyIncorrectDataTimeException("Некорректное время начала " + rangeStart + "и конца" + rangeEnd);
        }
        if (rangeStart == null) rangeStart = LocalDateTime.now();
        if (rangeEnd == null) rangeEnd = LocalDateTime.now().plusYears(100);
        if (text != null) text = text.toLowerCase();

        PageRequest page = PageRequest.of(from, size);
        List<Event> result;
        if (sort == null || sort.equals(SortEvent.EVENT_DATE)) {
            result = eventRepository.findByParametersForPublicSortEventDate(
                    text,
                    categories,
                    paid,
                    rangeStart,
                    rangeEnd,
                    onlyAvailable,
                    page);

        } else {
            result = eventRepository.findByParametersForPublicSortViews(
                    text,
                    categories,
                    paid,
                    rangeStart,
                    rangeEnd,
                    onlyAvailable,
                    page);
        }

        Map<Integer, Integer> hits = getStatisticFromListEvents(result);
        result.forEach(event -> event.setViews(hits.get(event.getId())));

        PagedListHolder<EventShortDto> pageOut = new PagedListHolder<>(result
                .stream()
                .map(EventMapper::toEventShortDto)
                .collect(Collectors.toList()));
        pageOut.setPageSize(size);
        pageOut.setPage(from);

        return new ArrayList<>(pageOut.getPageList());
    }

    @Override
    public EventFullDto getEventById(Integer eventId) {
        Event event = eventRepository.findById(eventId).orElseThrow(
                () -> new MyNotFoundException("Событие не найдено с id" + eventId));

        if (!event.getState().equals(State.PUBLISHED)) {
            throw new MyNotFoundException("Событие не опубликовано с id " + eventId);
        }

        Map<Integer, Integer> hits = getStatisticFromListEvents(List.of(event));
        Integer views = hits.get(event.getId());
        event.setViews(Objects.requireNonNullElse(views, 0));
        log.info("Событие с актуальной статистикой с id{} hits {} event {}", eventId, hits, event);

        return EventMapper.toEventFullDto(event);
    }

    private Map<Integer, Integer> getStatisticFromListEvents(List<Event> events) {
        List<Integer> idEvents = events.stream()
                .map(Event::getId)
                .collect(Collectors.toList());

        String start = "1000-01-01 00:01:01";
        String end = "3000-01-01 00:01:01";

        String eventsUri = "/events/";
        List<String> uris = idEvents.stream().map(id -> eventsUri + id).collect(Collectors.toList());
        List<StatOutputDto> response = statClient.getStats(start, end, uris, true);
        Map<Integer, Integer> hits = new HashMap<>();

        for (StatOutputDto statOutputDto : response) {
            String uri = statOutputDto.getUri();
            hits.put(Integer.parseInt(uri.substring(eventsUri.length())), Math.toIntExact(statOutputDto.getHits()));
        }

        return hits;
    }
}
