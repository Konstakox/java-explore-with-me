package ru.practicum.service.request;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.practicum.dto.event.EventRequestStatusUpdateRequest;
import ru.practicum.dto.event.EventRequestStatusUpdateResult;
import ru.practicum.dto.request.EventRequestStatusUpdateResultDto;
import ru.practicum.dto.request.ParticipationRequestDto;
import ru.practicum.exeption.MyIncorrectData;
import ru.practicum.exeption.MyIncorrectRequestException;
import ru.practicum.exeption.MyNotFoundException;
import ru.practicum.mapper.RequestMapper;
import ru.practicum.model.*;
import ru.practicum.repository.EventRepository;
import ru.practicum.repository.RequestRepository;
import ru.practicum.repository.UserRepository;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class RequestServiceImpl implements RequestService {

    private final RequestRepository requestRepository;
    private final UserRepository userRepository;
    private final EventRepository eventRepository;

    @Override
    public List<ParticipationRequestDto> getAll(Integer userId) {
        userRepository.findById(userId).orElseThrow(
                () -> new MyNotFoundException("Пользователь не найден с id " + userId));
        List<Request> result = requestRepository.findAllByRequesterId(userId);
        return result.stream()
                .map(RequestMapper::toParticipationRequestDto).collect(Collectors.toList());
    }

    @Override
    public ParticipationRequestDto createRequest(Integer userId, Integer eventId) {
        User user = userRepository.findById(userId).orElseThrow(
                () -> new MyNotFoundException("Пользователь не найден с id " + userId));
        Event event = eventRepository.findById(eventId).orElseThrow(
                () -> new MyNotFoundException("Событие не найдено с id" + eventId));

        if (event.getInitiator().equals(user)) {
            throw new MyIncorrectRequestException("Нельзя создать запрос на собственное событие");
        }
        if (!event.getState().equals(State.PUBLISHED)) {
            throw new MyIncorrectRequestException("Нельзя создать запрос на неопубликованое событие");
        }
        if (event.getConfirmedRequests() != null && event.getParticipantLimit() != 0
                && Long.valueOf(event.getParticipantLimit()) <= event.getConfirmedRequests()) {
            throw new MyIncorrectRequestException("Достигнут лимит запросов на участие в событии");
        }
        if (requestRepository.findByEventIdAndRequesterId(eventId, userId).isPresent()) {
            throw new MyIncorrectRequestException("Нельза создать повторный запрос");
        }

        Request newRequest = Request.builder()
                .created(Timestamp.valueOf(LocalDateTime.now()))
                .requester(user)
                .event(event)
                .status(Status.PENDING)
                .build();
        if (!event.getRequestModeration() || event.getParticipantLimit() == 0) {
            newRequest.setStatus(Status.CONFIRMED);
        }

        newRequest = requestRepository.save(newRequest);

        event.setConfirmedRequests(event.getConfirmedRequests() + 1);
        eventRepository.save(event);

        return RequestMapper.toParticipationRequestDto(newRequest);
    }

    @Override
    public List<ParticipationRequestDto> getEventRequests(Integer userId, Integer eventId) {
        userRepository.findById(userId).orElseThrow(
                () -> new MyNotFoundException("Пользователь не найден с id " + userId));
        eventRepository.findById(eventId).orElseThrow(
                () -> new MyNotFoundException("Событие не найдено с id" + eventId));

        List<Request> result = requestRepository.findAllByEventId(eventId);
        return result.stream()
                .map(RequestMapper::toParticipationRequestDto).collect(Collectors.toList());
    }

    @Override
    public EventRequestStatusUpdateResultDto updateEventRequestsStatus(Integer userId, Integer eventId,
                                                                       EventRequestStatusUpdateRequest eventRequestStatusUpdateRequest) {
        userRepository.findById(userId).orElseThrow(
                () -> new MyNotFoundException("Пользователь не найден с id " + userId));
        Event event = eventRepository.findById(eventId).orElseThrow(
                () -> new MyNotFoundException("Событие не найдено с id" + eventId));

        List<Request> requests = requestRepository.findAllByIdIn(eventRequestStatusUpdateRequest.getRequestIds());

        Integer countConfirmations = 0;
        List<Request> requestsForUpdate = new ArrayList<>();

        for (Request request : requests) {
            if (!eventId.equals(request.getEvent().getId())) {
                throw new MyIncorrectData("Не корректно указано событие");
            }
            if (!event.getRequestModeration() || event.getParticipantLimit() == 0
                    || event.getConfirmedRequests() >= event.getParticipantLimit()
                    || request.getStatus() != Status.PENDING) {
                throw new MyIncorrectRequestException("Невозможно подтвердить заявку");
            }
            if (eventRequestStatusUpdateRequest.getStatus() == Status.CONFIRMED
                    && (event.getConfirmedRequests() + countConfirmations) < event.getParticipantLimit()) {
                request.setStatus(Status.CONFIRMED);
                requestsForUpdate.add(request);
                countConfirmations++;
            } else {
                request.setStatus(Status.REJECTED);
                requestsForUpdate.add(request);
            }
        }

        requests = requestRepository.saveAll(requestsForUpdate);

        event.setConfirmedRequests(getRequestsByEventByStatus(event.getId(), Status.CONFIRMED));
        eventRepository.save(event);

        EventRequestStatusUpdateResult result = new EventRequestStatusUpdateResult();
        result.setConfirmedRequests(requests.stream()
                .filter(r -> r.getStatus() == Status.CONFIRMED)
                .collect(Collectors.toList()));
        result.setRejectedRequests(requests.stream()
                .filter(r -> r.getStatus() == Status.REJECTED)
                .collect(Collectors.toList()));

        return RequestMapper.toEventRequestStatusUpdateResultDto(result);
    }

    @Override
    public ParticipationRequestDto cancelRequest(Integer userId, Integer requestId) {
        userRepository.findById(userId).orElseThrow(
                () -> new MyNotFoundException("Пользователь не найден с id " + userId));
        Request request = requestRepository.findById(requestId).orElseThrow(
                () -> new MyNotFoundException("Заявка не найдена с id" + requestId));
        request.setStatus(Status.CANCELED);
        request = requestRepository.save(request);
        return RequestMapper.toParticipationRequestDto(request);
    }

    private Integer getRequestsByEventByStatus(Integer eventId, Status status) {
        return requestRepository.findCountRequestByEventIdAndStatus(eventId, status).orElse(0);
    }
}
