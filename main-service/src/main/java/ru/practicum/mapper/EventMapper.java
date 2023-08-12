package ru.practicum.mapper;

import ru.practicum.dto.event.*;
import ru.practicum.model.Category;
import ru.practicum.model.Event;
import ru.practicum.model.Location;
import ru.practicum.model.State;

public class EventMapper {

    public static Event toEvent(NewEventDto newEventDto) {
        return Event.builder()
                .annotation(newEventDto.getAnnotation())
                .category(Category.builder().id(newEventDto.getCategory()).build())
                .description(newEventDto.getDescription())
                .eventDate(newEventDto.getEventDate())
                .location(Location.builder().lat(newEventDto.getLocation().getLat()).lon(newEventDto.getLocation().getLon()).build())
                .paid(newEventDto.getPaid())
                .participantLimit(newEventDto.getParticipantLimit())
                .requestModeration(newEventDto.getRequestModeration())
                .title(newEventDto.getTitle())
                .build();
    }

    public static Event toEvent(UpdateEventUserRequest updateUserDto) {
        Event event = new Event();
        if (updateUserDto.getAnnotation() != null) event.setLocation(
                LocationMapper.toLocation(updateUserDto.getLocation()));
        if (updateUserDto.getCategory() != null) event.setCategory(
                Category.builder().id(updateUserDto.getCategory()).build());
        if (updateUserDto.getDescription() != null) event.setDescription(
                updateUserDto.getDescription());
        if (updateUserDto.getEventDate() != null) event.setEventDate(
                updateUserDto.getEventDate());
        if (updateUserDto.getLocation() != null) event.setLocation(LocationMapper.toLocation(
                updateUserDto.getLocation()));
        if (updateUserDto.getPaid() != null) event.setPaid(
                updateUserDto.getPaid());
        if (updateUserDto.getParticipantLimit() != null) event.setParticipantLimit(
                updateUserDto.getParticipantLimit());
        if (updateUserDto.getRequestModeration() != null) event.setRequestModeration(
                updateUserDto.getRequestModeration());
        if (updateUserDto.getStateAction() != null) event.setState(findState(
                updateUserDto.getStateAction()));
        if (updateUserDto.getTitle() != null) event.setTitle(
                updateUserDto.getTitle());
        return event;
    }

    public static Event toEvent(UpdateEventAdminRequest updateAdminDto) {
        Event event = new Event();
        if (updateAdminDto.getAnnotation() != null) event.setAnnotation(
                updateAdminDto.getAnnotation());
        if (updateAdminDto.getCategory() != null) event.setCategory(
                Category.builder().id(updateAdminDto.getCategory()).build());
        if (updateAdminDto.getDescription() != null) event.setDescription(
                updateAdminDto.getDescription());
        if (updateAdminDto.getEventDate() != null) event.setEventDate(
                updateAdminDto.getEventDate());
        if (updateAdminDto.getLocation() != null) event.setLocation(
                LocationMapper.toLocation(updateAdminDto.getLocation()));
        if (updateAdminDto.getPaid() != null) event.setPaid(
                updateAdminDto.getPaid());
        if (updateAdminDto.getParticipantLimit() != null) event.setParticipantLimit(
                updateAdminDto.getParticipantLimit());
        if (updateAdminDto.getRequestModeration() != null) event.setRequestModeration(
                updateAdminDto.getRequestModeration());
        if (updateAdminDto.getStateAction() != null) event.setState(findState(
                updateAdminDto.getStateAction()));
        if (updateAdminDto.getTitle() != null) event.setTitle(
                updateAdminDto.getTitle());
        return event;
    }

    public static EventFullDto toEventFullDto(Event event) {
        return EventFullDto.builder()
                .id(event.getId())
                .annotation(event.getAnnotation())
                .category(CategoryMapper.toNewCategoryDto(event.getCategory()))
                .confirmedRequests(event.getConfirmedRequests())
                .createdOn(event.getCreatedOn())
                .description(event.getDescription())
                .eventDate(event.getEventDate())
                .initiator(UserMapper.toUserShortDto(event.getInitiator()))
                .location(LocationMapper.toLocationResponseDto(event.getLocation()))
                .paid(event.getPaid())
                .participantLimit(event.getParticipantLimit())
                .publishedOn(event.getPublishedOn())
                .requestModeration(event.getRequestModeration())
                .state(event.getState().toString())
                .title(event.getTitle())
                .views(event.getViews().intValue())
                .build();
    }

    public static Event updateEvent(Event donor, Event recipient) {
        if (donor.getAnnotation() != null) recipient.setAnnotation(donor.getAnnotation());
        if (donor.getCategory() != null) recipient.setCategory(donor.getCategory());
        if (donor.getDescription() != null) recipient.setDescription(donor.getDescription());
        if (donor.getEventDate() != null) recipient.setEventDate(donor.getEventDate());
        if (donor.getLocation() != null) recipient.setLocation(donor.getLocation());
        if (donor.getPaid() != null) recipient.setPaid(donor.getPaid());
        if (donor.getParticipantLimit() != null) recipient.setParticipantLimit(donor.getParticipantLimit());
        if (donor.getRequestModeration() != null) recipient.setRequestModeration(donor.getRequestModeration());
        if (donor.getState() != null) recipient.setState(donor.getState());
        if (donor.getTitle() != null) recipient.setTitle(donor.getTitle());
        return recipient;
    }

    public static EventShortDto toEventShortDto(Event event) {
        return EventShortDto.builder()
                .id(event.getId())
                .annotation(event.getAnnotation())
                .category(CategoryMapper.toNewCategoryDto(event.getCategory()))
                .confirmedRequests(event.getConfirmedRequests())
                .eventDate(event.getEventDate())
                .initiator(UserMapper.toUserShortDto(event.getInitiator()))
                .paid(event.getPaid())
                .title(event.getTitle())
                .views(event.getViews())
                .build();
    }

    private static State findState(String str) {
        if (str == null) return null;
        if (str.equals("CANCEL_REVIEW")) return State.CANCELED;
        if (str.equals("PUBLISH_EVENT")) return State.PUBLISHED;
        if (str.equals("REJECT_EVENT")) return State.CANCELED;
        if (str.equals("SEND_TO_REVIEW")) return State.PENDING;
        return null;
    }
}
