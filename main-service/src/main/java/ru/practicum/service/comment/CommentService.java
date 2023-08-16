package ru.practicum.service.comment;

import ru.practicum.dto.comment.CommentResponseDto;
import ru.practicum.dto.comment.NewAndUpdateCommentDto;

import java.util.List;

public interface CommentService {
    CommentResponseDto createComment(Integer userId, Integer eventId, NewAndUpdateCommentDto newAndUpdateCommentDto);

    CommentResponseDto updateComment(Integer userId, Integer commentId, NewAndUpdateCommentDto newAndUpdateCommentDto);

    void deleteComment(Integer userId, Integer commentId);

    void deleteCommentAdmin(Integer commentId);

    CommentResponseDto getComment(Integer commentId);

    List<CommentResponseDto> getAllCommentByEventId(Integer eventId, Integer from, Integer size);
}
