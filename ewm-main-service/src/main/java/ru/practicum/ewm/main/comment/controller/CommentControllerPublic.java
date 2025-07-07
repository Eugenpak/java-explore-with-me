package ru.practicum.ewm.main.comment.controller;

import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.ewm.main.comment.dto.CommentOutDto;
import ru.practicum.ewm.main.comment.service.CommentService;

import java.util.List;

@RestController
@RequestMapping
@RequiredArgsConstructor
@Slf4j
@Validated
public class CommentControllerPublic {
    private final CommentService commentService;

    @GetMapping("events/{eventId}/comments")
    @ResponseStatus(HttpStatus.OK)
    public List<CommentOutDto> getAllCommentsByEvent(@PathVariable Long eventId,
                                                     @RequestParam(defaultValue = "0") @PositiveOrZero int offset,
                                                     @RequestParam(defaultValue = "10") @Positive int limit) {
        log.info("EWM C-Com-Pub getAllCommentsByEvent(). eventId: {}, offset: {}, limit: {}",eventId, offset,limit);
        return commentService.getAllCommentsByEvent(eventId, offset, limit);
    }

    @GetMapping("/comments/{commentId}")
    @ResponseStatus(HttpStatus.OK)
    public CommentOutDto getCommentById(@PathVariable @Positive Long commentId) {
        log.info("EWM C-Com-Pub getCommentById(). commentId: {}",commentId);
        return commentService.getCommentById(commentId);
    }
}
