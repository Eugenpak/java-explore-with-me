package ru.practicum.ewm.main.category.mapper;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import ru.practicum.ewm.main.category.dto.CategoryRequestDto;
import ru.practicum.ewm.main.category.dto.CategoryResponseDto;
import ru.practicum.ewm.main.category.entity.Category;

import java.util.List;
import java.util.stream.Collectors;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class CategoryMapper {
    public static Category mapToCategory(CategoryRequestDto categoryRequestDto) {
        return new Category(
                categoryRequestDto.getId(),
                categoryRequestDto.getName()
        );
    }

    public static CategoryResponseDto mapToCategoryResponseDto(Category category) {
        return new CategoryResponseDto(
                category.getId(),
                category.getName()
        );
    }

    public static List<CategoryResponseDto> mapToCategoryResponseDtoList(List<Category> categories) {
        return categories.stream()
                .map(CategoryMapper::mapToCategoryResponseDto)
                .collect(Collectors.toList());
    }
}
