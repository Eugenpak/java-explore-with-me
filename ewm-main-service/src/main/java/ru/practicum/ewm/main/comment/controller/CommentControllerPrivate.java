package ru.practicum.ewm.main.comment.controller;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.ewm.main.comment.dto.CommentInDto;
import ru.practicum.ewm.main.comment.dto.CommentOutDto;
import ru.practicum.ewm.main.comment.service.CommentService;

import java.util.List;

@RestController
@RequestMapping("/users/{userId}")
@RequiredArgsConstructor
@Slf4j
@Validated
public class CommentControllerPrivate {
    private final CommentService commentService;

    @PostMapping("/events/{eventId}/comments")
    @ResponseStatus(HttpStatus.CREATED)
    public CommentOutDto createComment(@PathVariable @Positive Long userId,
                                       @PathVariable @Positive Long eventId,
                                       @RequestBody @Validated CommentInDto commentInDto) {
        log.info("EWM C-Com-Pr createComment(). userId: {}, eventId: {}, commentInDto: {}",userId, eventId, commentInDto);
        return commentService.createComment(userId, eventId,commentInDto);
    }

    @PatchMapping("/comments/{commentId}")
    @ResponseStatus(HttpStatus.OK)
    public CommentOutDto updateComment(@PathVariable @Positive Long userId,
                                       @PathVariable @Positive Long commentId,
                                       @RequestBody @Validated CommentInDto commentInDto) {
        log.info("EWM C-Com-Pr updateComment(). userId: {}, commentId: {}, commentInDto: {}",userId, commentId, commentInDto);
        return commentService.updateComment(userId, commentId,commentInDto);
    }

    @GetMapping("/events/{eventId}/comments")
    @ResponseStatus(HttpStatus.OK)
    public List<CommentOutDto> getAllComments(@PathVariable @Positive Long userId,
                                              @PathVariable @Positive Long eventId,
                                              @RequestParam(defaultValue = "0") int offset,
                                              @RequestParam(defaultValue = "10") int limit) {
        log.info("EWM C-Com-Pr getAllComments(). userId: {}, eventId: {}",userId, eventId);
        return commentService.getAllComments(userId, eventId, offset, limit);
    }

    @DeleteMapping("/comments/{commentId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteComment(@PathVariable @Positive Long userId,
                              @PathVariable @Positive Long commentId) {
        log.info("EWM C-Com-Pr deleteComment(). userId: {}, commentId: {}",userId, commentId);
        commentService.deleteComment(userId, commentId);
    }

    @GetMapping("/events/{eventId}/comments/search")
    @ResponseStatus(HttpStatus.OK)
    public List<CommentOutDto> searchComments(@PathVariable Long userId,
                                                   @PathVariable Long eventId,
                                                   @RequestParam @NotBlank String comment,
                                                   @RequestParam(defaultValue = "0") @PositiveOrZero Integer offset,
                                                   @RequestParam(defaultValue = "10") @Positive Integer limit) {
        log.info("EWM C-Com-Pr searchComments(). userId: {}, eventId: {}, comment: {},offset: {},limit: {}",
                userId, eventId,comment,offset,limit);
        return commentService.searchComments(userId, eventId, comment, offset, limit);
    }
}
