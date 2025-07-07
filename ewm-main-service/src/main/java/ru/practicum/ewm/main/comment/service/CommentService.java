package ru.practicum.ewm.main.comment.service;

import ru.practicum.ewm.main.comment.dto.CommentInDto;
import ru.practicum.ewm.main.comment.dto.CommentOutDto;

import java.util.List;

public interface CommentService {
    List<CommentOutDto> getAllComments(Long userId, Long eventId, int from, int size);

    CommentOutDto createComment(Long userId, Long eventId,CommentInDto commentInDto);

    CommentOutDto updateComment(Long userId, Long commentId,CommentInDto commentInDto);

    void deleteComment(Long userId, Long commentId);

    List<CommentOutDto> getAllCommentsByEvent(Long eventId, int from, int size);

    CommentOutDto getCommentById(Long commentId);

    void deleteCommentByAdmin(Long eventId);

    List<CommentOutDto> searchComments(Long userId, Long eventId, String comment, Integer from, Integer size);

}
