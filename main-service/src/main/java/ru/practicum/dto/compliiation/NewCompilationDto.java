package ru.practicum.dto.compliiation;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.HashSet;
import java.util.Set;

@Data
public class NewCompilationDto {
    private Boolean pinned = false;
    @NotBlank
    @Size(min = 1, max = 50)
    private String title;
    private Set<Integer> events = new HashSet<>();
}
