package ru.practicum.mapper;

import lombok.experimental.UtilityClass;
import ru.practicum.dto.comment.CommentResponseDto;
import ru.practicum.dto.comment.NewAndUpdateCommentDto;
import ru.practicum.model.Comment;

@UtilityClass
public class CommentMapper {
    public Comment toComment(NewAndUpdateCommentDto newAndUpdateCommentDto) {
        return Comment.builder()
                .text(newAndUpdateCommentDto.getText())
                .build();
    }

    public CommentResponseDto toCommentResponseDto(Comment comment) {
        return CommentResponseDto.builder()
                .id(comment.getId())
                .text(comment.getText())
                .author(UserMapper.toUserShortDto(comment.getAuthor()))
                .event(EventMapper.toEventShortDto(comment.getEvent()))
                .created(comment.getCreated())
                .updated(comment.getUpdated())
                .build();
    }
}
