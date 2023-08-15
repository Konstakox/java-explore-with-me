package ru.practicum.mapper;

import lombok.experimental.UtilityClass;
import ru.practicum.dto.request.ParticipationRequestDto;
import ru.practicum.model.Request;

@UtilityClass
public class RequestMapper {

    public ParticipationRequestDto toParticipationRequestDto(Request request) {

        return ParticipationRequestDto.builder()
                .id(request.getId())
                .event(request.getEvent().getId())
                .requester(request.getRequester().getId())
                .status(request.getStatus())
                .created(request.getCreated())
                .build();
    }
}
