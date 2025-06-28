package ru.practicum.ewm.main.category.dao;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import ru.practicum.ewm.main.category.entity.Category;

import java.util.List;
import java.util.Optional;

public interface CategoryRepository extends JpaRepository<Category, Long> {
    List<Category> findByIdInOrderByIdAsc(List<Long> ids, PageRequest pageRequest);

    Optional<Category> findByNameIgnoreCase(String nameDto);
}
