package ru.practicum.ewm.main.comment.dao;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import ru.practicum.ewm.main.comment.entity.Comment;
import ru.practicum.ewm.main.event.entity.Event;
import ru.practicum.ewm.main.user.entity.User;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long> {
    List<Comment> findByEventOrderByIdAsc(Event event, PageRequest pageRequest);

    List<Comment> findByCreatorAndEvent(User creator, Event event, PageRequest pageRequest);

    List<Comment> findAllByCreatorIdAndEventIdAndCommentContainingIgnoreCase(Long creatorId,
                                                                             Long eventId,
                                                                             String comment,
                                                                             PageRequest request);

    void deleteByEventId(Long eventId);
}
