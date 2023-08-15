package ru.practicum.dto.event;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import ru.practicum.dto.category.NewCategoryDto;
import ru.practicum.dto.user.UserShortDto;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NotNull
public class EventShortDto {

    public Integer id;
    @NotNull
    @Size(min = 20, max = 2000)
    private String annotation;
    @NotNull
    private NewCategoryDto category;
    private Integer confirmedRequests;
    @NotNull
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime eventDate;
    private UserShortDto initiator;
    private Boolean paid;
    @NotNull
    @Size(min = 3, max = 120)
    private String title;
    private Integer views;
}
