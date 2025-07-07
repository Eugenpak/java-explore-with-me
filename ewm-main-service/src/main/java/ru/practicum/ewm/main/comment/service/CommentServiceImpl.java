package ru.practicum.ewm.main.comment.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.ewm.main.comment.dao.CommentRepository;
import ru.practicum.ewm.main.comment.dto.CommentInDto;
import ru.practicum.ewm.main.comment.dto.CommentOutDto;
import ru.practicum.ewm.main.comment.entity.Comment;
import ru.practicum.ewm.main.comment.mapper.CommentMapper;
import ru.practicum.ewm.main.event.dao.EventRepository;
import ru.practicum.ewm.main.event.entity.Event;
import ru.practicum.ewm.main.event.entity.enums.EventState;
import ru.practicum.ewm.main.exception.ConflictException;
import ru.practicum.ewm.main.exception.NotFoundException;
import ru.practicum.ewm.main.user.dao.UserRepository;
import ru.practicum.ewm.main.user.entity.User;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService {
    private final CommentRepository commentRepository;
    private final UserRepository userRepository;
    private final EventRepository eventRepository;

    @Override
    public List<CommentOutDto> getAllComments(Long userId, Long eventId, int from, int size) {
        PageRequest pageRequest = PageRequest.of(from / size, size);
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException("Пользователя с id=" + userId + " не найдено."));
        Event event = eventRepository.findById(eventId)
                .orElseThrow(() -> new NotFoundException("Событие с id=" + eventId + " не найдено."));
        List<Comment> comments = commentRepository.findByCreatorAndEvent(user, event, pageRequest);
        if (comments.isEmpty()) {
            return new ArrayList<>();
        }
        return CommentMapper.toCommentOutDtoList(comments);
    }

    @Override
    @Transactional
    public CommentOutDto createComment(Long userId, Long eventId, CommentInDto commentInDto) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException("Пользователя с id=" + userId + " не найдено."));
        Event event = eventRepository.findById(eventId)
                .orElseThrow(() -> new NotFoundException("Событие с id=" + eventId + " не найдено."));
        if (event.getState() != EventState.PUBLISHED) {
            throw new ConflictException("Событие с id=" + eventId + " нет статуса PUBLISHED. Операция отклонена!");
        }
        Comment newComment = CommentMapper.mapToComment(commentInDto, user, event);
        return CommentMapper.mapToCommentOutDto(commentRepository.save(newComment));
    }

    @Override
    @Transactional
    public CommentOutDto updateComment(Long userId, Long commentId,CommentInDto commentInDto) {
        if (!userRepository.existsById(userId)) {
            throw new NotFoundException("Пользователя с id=" + userId + " не найдено.");
        }
        Comment oldComment = commentRepository.findById(commentId)
                .orElseThrow(() -> new NotFoundException("Комментарий с id=" + commentId + " не  не найден."));
        if (!oldComment.getCreator().getId().equals(userId)) {
            throw new ConflictException("Редактирование комментария не доступно.");
        }
        oldComment.setComment(commentInDto.getComment());
        return CommentMapper.mapToCommentOutDto(oldComment);
    }

    @Override
    @Transactional
    public void deleteComment(final Long userId, final Long commentId) {
        final Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new NotFoundException("Комментарий с id=" + commentId + " не найден."));

        if (!comment.getCreator().getId().equals(userId) &&
                !comment.getCreator().getId().equals(comment.getEvent().getInitiator().getId())) {
            throw new ConflictException("Удалить комментарий может только его автор или инициатор события.");
        }

        commentRepository.deleteById(commentId);
    }

    @Override
    public List<CommentOutDto> getAllCommentsByEvent(Long eventId, int from, int size) {
        PageRequest pageRequest = PageRequest.of(from / size, size);
        Event event = eventRepository.findById(eventId)
                .orElseThrow(() -> new NotFoundException("Событие с id=" + eventId + " не найдено."));
        List<Comment> comments = commentRepository.findByEventOrderByIdAsc(event, pageRequest);
        if (comments.isEmpty()) {
            return new ArrayList<>();
        }
        return CommentMapper.toCommentOutDtoList(comments);
    }

    @Override
    public CommentOutDto getCommentById(Long commentId) {
        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new NotFoundException("Комментарий с id=" + commentId + " не найдено."));
        return CommentMapper.mapToCommentOutDto(comment);
    }

    @Override
    @Transactional
    public void deleteCommentByAdmin(Long eventId) {
        eventRepository.findById(eventId)
                .orElseThrow(() -> new NotFoundException("Событие с id=" + eventId + " не найден."));

        commentRepository.deleteByEventId(eventId);
    }

    @Override
    public List<CommentOutDto> searchComments(Long userId, Long eventId, String comment, Integer from, Integer size) {
        PageRequest pageRequest = PageRequest.of(from / size, size);
        if (!userRepository.existsById(userId)) {
            throw new NotFoundException("Пользователь с id=" + userId + " не найден");
        }
        if (!eventRepository.existsById(eventId)) {
            throw new NotFoundException("Событие с id=" + eventId + " не найдено");
        }
        if (comment.isBlank()) {
            return Collections.emptyList();
        }
        List<Comment> comments = commentRepository
                .findAllByCreatorIdAndEventIdAndCommentContainingIgnoreCase(userId, eventId, comment, pageRequest);
        return CommentMapper.toCommentOutDtoList(comments);
    }

}
