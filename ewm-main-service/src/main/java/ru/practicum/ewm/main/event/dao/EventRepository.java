package ru.practicum.ewm.main.event.dao;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import ru.practicum.ewm.main.category.entity.Category;
import ru.practicum.ewm.main.event.entity.Event;
import ru.practicum.ewm.main.event.entity.enums.EventState;
import ru.practicum.ewm.main.user.entity.User;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface EventRepository extends JpaRepository<Event, Long> {
    boolean existsByCategoryId(Long catId);

    List<Event> findByInitiatorId(Long initiatorId, Pageable pageable);

    List<Event> findByInitiatorInAndStateInAndCategoryInAndEventDateAfterAndEventDateBeforeOrderByIdAsc(
            List<User> initiators, List<EventState> states, List<Category> categories,
            LocalDateTime start, LocalDateTime end, Pageable pageable);

    Optional<Event> findByIdAndState(Long eventId, EventState state);

    List<Event> findByIdIn(List<Long> eventIds);
}
