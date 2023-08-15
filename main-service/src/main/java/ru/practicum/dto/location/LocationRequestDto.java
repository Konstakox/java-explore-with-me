package ru.practicum.dto.location;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class LocationRequestDto {
    private float lat;
    private float lon;
}
