package ru.practicum.ewm.main.user.dao;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import ru.practicum.ewm.main.user.entity.User;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    List<User> findByIdInOrderByIdAsc(List<Long> ids, PageRequest pageRequest);

    List<User> findByIdIn(List<Long> userIds, PageRequest pageRequest);

    Optional<User> findByEmailIgnoreCase(String emailSearch);
}
