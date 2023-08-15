package ru.practicum.dto.category;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CategoryResponseDto {
    private Integer id;
    private String name;
}
