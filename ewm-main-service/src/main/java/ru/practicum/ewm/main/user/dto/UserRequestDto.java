package ru.practicum.ewm.main.user.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UserRequestDto {
    private Long id;

    @Size(min = 2, max = 250)
    @NotBlank(message = "Имя пользователя должно быть указано")
    private String name;

    @NotBlank
    @Size(min = 6, max = 254)
    @Email(message = "Имейл должен содержать символ «@». Формат имейла: example@mail.com")
    private String email;
}
