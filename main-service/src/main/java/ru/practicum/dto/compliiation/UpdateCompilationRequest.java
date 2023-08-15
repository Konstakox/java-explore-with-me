package ru.practicum.dto.compliiation;

import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.Size;
import java.util.Set;

@Data
@Builder
public class UpdateCompilationRequest {
    private Boolean pinned;
    @Size(max = 50)
    private String title;
    private Set<Integer> events;
}
