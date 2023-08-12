package ru.practicum.service.request;

import ru.practicum.dto.event.EventRequestStatusUpdateRequest;
import ru.practicum.dto.request.EventRequestStatusUpdateResultDto;
import ru.practicum.dto.request.ParticipationRequestDto;

import java.util.List;

public interface RequestService {

    List<ParticipationRequestDto> getAll(Integer userId);

    ParticipationRequestDto createRequest(Integer userId, Integer eventId);

    List<ParticipationRequestDto> getEventRequests(Integer userId, Integer eventId);

    EventRequestStatusUpdateResultDto updateEventRequestsStatus(Integer userId, Integer eventId, EventRequestStatusUpdateRequest eventRequestStatusUpdateRequest);

    ParticipationRequestDto cancelRequest(Integer userId, Integer requestId);
}
