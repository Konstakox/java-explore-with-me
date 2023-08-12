package ru.practicum.dto.user;

import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Data
@Builder
public class UserDto {
    private Integer id;
    @NotBlank(message = "Нет имени")
    @Size(min = 2, max = 250)
    private String name;
    @NotBlank(message = "Некорректный емайл")
    @Email(message = "Некорректный емайл")
    @Size(min = 6, max = 254)
    private String email;
}
