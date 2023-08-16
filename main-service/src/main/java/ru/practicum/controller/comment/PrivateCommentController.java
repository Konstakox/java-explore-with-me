package ru.practicum.controller.comment;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.dto.comment.CommentResponseDto;
import ru.practicum.dto.comment.NewAndUpdateCommentDto;
import ru.practicum.service.comment.CommentService;

import javax.validation.Valid;
import javax.validation.constraints.Positive;

@RestController
@RequestMapping("/users/{userId}/comments")
@RequiredArgsConstructor
@Validated
@Slf4j
public class PrivateCommentController {

    private final CommentService commentService;

    @PostMapping("{eventId}/events")
    @ResponseStatus(HttpStatus.CREATED)
    public CommentResponseDto createComment(@PathVariable @Positive Integer userId,
                                            @PathVariable @Positive Integer eventId,
                                            @RequestBody @Valid NewAndUpdateCommentDto newAndUpdateCommentDto) {
        log.info("Запрос на создание комментария {} от пользователя с id {} для события с id {}",
                newAndUpdateCommentDto, userId, eventId);
        return commentService.createComment(userId, eventId, newAndUpdateCommentDto);
    }

    @PatchMapping("{commentId}")
    public CommentResponseDto update(@PathVariable @Positive Integer userId,
                                     @PathVariable @Positive Integer commentId,
                                     @RequestBody @Valid NewAndUpdateCommentDto newAndUpdateCommentDto) {
        log.info("Запрос на изменения {} от пользователя с id {} для комментария с id {}",
                newAndUpdateCommentDto, userId, commentId);
        return commentService.updateComment(userId, commentId, newAndUpdateCommentDto);
    }

    @DeleteMapping("{commentId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteCommentById(@PathVariable @Positive Integer userId,
                                  @PathVariable @Positive Integer commentId) {
        log.info("Запрос от пользователя с id {} на удаление комментария с id {} ", userId, commentId);
        commentService.deleteComment(userId, commentId);
    }
}
