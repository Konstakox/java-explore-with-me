package ru.practicum.controller.comment;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.dto.comment.CommentResponseDto;
import ru.practicum.service.comment.CommentService;

import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;
import java.util.List;

@RestController
@RequestMapping("/comments")
@RequiredArgsConstructor
@Validated
@Slf4j
public class PublicCommentController {

    private final CommentService commentService;

    @GetMapping("{commentId}")
    public CommentResponseDto getComment(
            @PathVariable @Positive Integer commentId) {
        log.info("Запрос комментария с id {}", commentId);

        return commentService.getComment(commentId);
    }

    @GetMapping("{eventId}/events")
    public List<CommentResponseDto> getAllCommentByEventId(
            @PathVariable @Positive Integer eventId,
            @RequestParam(defaultValue = "0") @PositiveOrZero Integer from,
            @RequestParam(defaultValue = "10") @Positive Integer size) {
        log.info("Запрос комментариев для события с id {}, с параметрами from={}, size={}", eventId, from, size);
        return commentService.getAllCommentByEventId(eventId, from, size);
    }
}
