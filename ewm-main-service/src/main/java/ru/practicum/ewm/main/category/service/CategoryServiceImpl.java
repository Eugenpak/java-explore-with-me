package ru.practicum.ewm.main.category.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.ewm.main.category.dao.CategoryRepository;
import ru.practicum.ewm.main.category.dto.CategoryRequestDto;
import ru.practicum.ewm.main.category.dto.CategoryResponseDto;
import ru.practicum.ewm.main.category.entity.Category;
import ru.practicum.ewm.main.category.mapper.CategoryMapper;
import ru.practicum.ewm.main.event.dao.EventRepository;
import ru.practicum.ewm.main.exception.ConflictException;
import ru.practicum.ewm.main.exception.DuplicatedDataException;
import ru.practicum.ewm.main.exception.NotFoundException;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {
    private final CategoryRepository categoryRepository;
    private final EventRepository eventRepository;

    @Override
    @Transactional
    public CategoryResponseDto createCategory(CategoryRequestDto categoryRequestDto) {
        final String nameDto = categoryRequestDto.getName();
        Optional<Category> optionalUser = categoryRepository.findByNameIgnoreCase(nameDto);
        if (optionalUser.isPresent()) {
            throw new DuplicatedDataException("Категория с таким name = " + nameDto +
                    " уже существует.");
        }

        Category category = categoryRepository.save(CategoryMapper.mapToCategory(categoryRequestDto));
        return CategoryMapper.mapToCategoryResponseDto(category);
    }

    @Override
    @Transactional
    public CategoryResponseDto updateCategory(long catId, CategoryRequestDto categoryRequestDto) {
        Category category = categoryRepository.findById(catId)
                .orElseThrow(() -> new NotFoundException("Категории с id = {} не существует." + catId));

        if (Objects.nonNull(categoryRequestDto.getName())) {
            category.setName(categoryRequestDto.getName());
        }

        return CategoryMapper.mapToCategoryResponseDto(category);
    }

    @Override
    @Transactional
    public void deleteCategory(long catId) {
        if (eventRepository.existsByCategoryId(catId)) {
            throw new ConflictException("Нельзя удалить категорию, с которой связаны события.");
        }
        categoryRepository.deleteById(catId);
    }

    @Override
    public CategoryResponseDto getCategoryById(long catId) {
        Category category = categoryRepository.findById(catId)
                .orElseThrow(() -> new NotFoundException("Категории с id = {} не существует." + catId));
        return CategoryMapper.mapToCategoryResponseDto(category);
    }

    @Override
    public List<CategoryResponseDto> getAllCategories(int from, int size) {
        Pageable pageable = PageRequest.of(from / size, size);
        Page<Category> categoryPage = categoryRepository.findAll(pageable);
        return CategoryMapper.mapToCategoryResponseDtoList(categoryPage.getContent());
    }
}
