package ru.practicum.mapper;

import lombok.experimental.UtilityClass;
import ru.practicum.dto.location.LocationRequestDto;
import ru.practicum.dto.location.LocationResponseDto;
import ru.practicum.model.Location;

@UtilityClass
public class LocationMapper {

    public Location toLocation(LocationRequestDto dto) {
        if (dto == null) return null;
        return Location.builder()
                .lat(dto.getLat())
                .lon(dto.getLon())
                .build();
    }

    public LocationResponseDto toLocationResponseDto(Location location) {
        return LocationResponseDto.builder()
                .lat(location.getLat())
                .lon(location.getLon())
                .build();
    }
}
