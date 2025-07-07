package ru.practicum.ewm.main.comment.mapper;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import ru.practicum.ewm.main.comment.dto.CommentInDto;
import ru.practicum.ewm.main.comment.dto.CommentOutDto;
import ru.practicum.ewm.main.comment.entity.Comment;
import ru.practicum.ewm.main.event.entity.Event;
import ru.practicum.ewm.main.event.mapper.EventMapper;
import ru.practicum.ewm.main.user.entity.User;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class CommentMapper {
    public static Comment mapToComment(CommentInDto commentInDto, User user, Event event) {
        return new Comment(
                null,
                commentInDto.getComment(),
                user,
                event,
                LocalDateTime.now()
        );
    }

    public static CommentOutDto mapToCommentOutDto(Comment comment) {
        return new CommentOutDto(
                comment.getId(),
                comment.getComment(),
                comment.getCreator().getName(),
                EventMapper.mapToEventShortDto(comment.getEvent()),
                comment.getCreated()
        );
    }

    public static List<CommentOutDto> toCommentOutDtoList(List<Comment> comments) {
        return comments.stream()
                .map(CommentMapper::mapToCommentOutDto)
                .collect(Collectors.toList());
    }
}
