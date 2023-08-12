package ru.practicum.dto.event;

import lombok.Data;
import ru.practicum.model.Status;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.Set;

@Data
public class EventRequestStatusUpdateRequest {
    @NotEmpty
    private Set<Integer> requestIds;
    @NotNull
    private Status status;
}
