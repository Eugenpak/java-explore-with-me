package ru.practicum.ewm.main.category.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class CategoryRequestDto {
    private Long id;
    @NotBlank(message = "Категория должна быть указана")
    @Size(max = 50, message = "Максимальная длина - 50 символов")
    private String name;
}
