package ru.practicum.service.comment;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.support.PagedListHolder;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import ru.practicum.dto.comment.CommentResponseDto;
import ru.practicum.dto.comment.NewAndUpdateCommentDto;
import ru.practicum.exeption.MyIncorrectCommentException;
import ru.practicum.exeption.MyNotFoundException;
import ru.practicum.mapper.CommentMapper;
import ru.practicum.model.Comment;
import ru.practicum.model.Event;
import ru.practicum.model.User;
import ru.practicum.repository.CommentRepository;
import ru.practicum.repository.EventRepository;
import ru.practicum.repository.UserRepository;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService {
    private final CommentRepository commentRepository;
    private final UserRepository userRepository;
    private final EventRepository eventRepository;


    @Override
    public CommentResponseDto createComment(Integer userId, Integer eventId, NewAndUpdateCommentDto newAndUpdateCommentDto) {
        Comment comment = CommentMapper.toComment(newAndUpdateCommentDto);
        User user = userRepository.findById(userId).orElseThrow(
                () -> new MyNotFoundException("Пользователь не найден с id " + userId));
        Event event = eventRepository.findById(eventId).orElseThrow(
                () -> new MyNotFoundException("Событие не найдено с id" + eventId));

        comment.setAuthor(user);
        comment.setEvent(event);
        comment.setCreated(LocalDateTime.now());
        comment.setUpdated(LocalDateTime.now());
        return CommentMapper.toCommentResponseDto(commentRepository.save(comment));
    }

    @Override
    public CommentResponseDto updateComment(Integer userId, Integer commentId, NewAndUpdateCommentDto newAndUpdateCommentDto) {
        Comment comment = commentRepository.findById(commentId).orElseThrow(
                () -> new MyNotFoundException("Комментарий не найден с id" + commentId));
        if (!Objects.equals(userId, comment.getAuthor().getId())) {
            throw new MyIncorrectCommentException("Нельзя редактировать чужой комментарий");
        }
        userRepository.findById(userId).orElseThrow(
                () -> new MyNotFoundException("Пользователь не найден с id " + userId));

        comment.setText(newAndUpdateCommentDto.getText());
        comment.setUpdated(LocalDateTime.now());

        return CommentMapper.toCommentResponseDto(commentRepository.save(comment));
    }

    @Override
    public void deleteComment(Integer userId, Integer commentId) {
        Comment comment = commentRepository.findById(commentId).orElseThrow(
                () -> new MyNotFoundException("Комментарий не найден с id" + commentId));
        if (!Objects.equals(userId, comment.getAuthor().getId())) {
            throw new MyIncorrectCommentException("Нельзя удалить чужой комментарий");
        }
        userRepository.findById(userId).orElseThrow(
                () -> new MyNotFoundException("Пользователь не найден с id " + userId));

        commentRepository.deleteById(commentId);
    }

    @Override
    public void deleteCommentAdmin(Integer commentId) {
        commentRepository.findById(commentId).orElseThrow(
                () -> new MyNotFoundException("Комментарий не найден с id" + commentId));

        commentRepository.deleteById(commentId);
    }

    @Override
    public CommentResponseDto getComment(Integer commentId) {
        Comment comment = commentRepository.findById(commentId).orElseThrow(
                () -> new MyNotFoundException("Комментарий не найден с id" + commentId));

        return CommentMapper.toCommentResponseDto(comment);
    }

    @Override
    public List<CommentResponseDto> getAllCommentByEventId(Integer eventId, Integer from, Integer size) {
        eventRepository.findById(eventId).orElseThrow(
                () -> new MyNotFoundException("Событие не найдено с id" + eventId));
        PageRequest page = PageRequest.of(from, size);

        List<Comment> result = commentRepository.findAllByEventIdOrderByCreated(eventId, page);
        PagedListHolder<CommentResponseDto> pageOut = new PagedListHolder<>(result
                .stream()
                .map(CommentMapper::toCommentResponseDto)
                .collect(Collectors.toList()));
        pageOut.setPageSize(size);
        pageOut.setPage(from);

        return new ArrayList<>(pageOut.getPageList());
    }
}
