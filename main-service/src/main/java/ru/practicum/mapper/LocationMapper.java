package ru.practicum.mapper;

import ru.practicum.dto.location.LocationRequestDto;
import ru.practicum.dto.location.LocationResponseDto;
import ru.practicum.model.Location;

public class LocationMapper {

    public static Location toLocation(LocationRequestDto dto) {
        if (dto == null) return null;
        return Location.builder()
                .lat(dto.getLat())
                .lon(dto.getLon())
                .build();
    }

    public static LocationResponseDto toLocationResponseDto(Location location) {
        return LocationResponseDto.builder()
                .lat(location.getLat())
                .lon(location.getLon())
                .build();
    }
}
