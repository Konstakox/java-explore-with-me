package ru.practicum.service.user;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.support.PagedListHolder;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.dto.user.UserDto;
import ru.practicum.mapper.UserMapper;
import ru.practicum.model.User;
import ru.practicum.repository.UserRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;

    @Override
    public UserDto createUser(UserDto userDto) {
        User newUser = userRepository.save(UserMapper.toUser(userDto));
        log.info("Пользователь создан {} ", newUser);

        return UserMapper.toUserDto(newUser);
    }

    @Override
    public List<UserDto> getUsers(List<Integer> ids, Integer from, Integer size) {
        PageRequest page = PageRequest.of(from, size);
        List<User> users;

        if (ids == null) {
            users = userRepository.findAll();
        } else {
            users = userRepository.findByIdIn(ids, page);
        }
        PagedListHolder<UserDto> pageOut = new PagedListHolder<>(users
                .stream()
                .map(UserMapper::toUserDto)
                .collect(Collectors.toList()));
        pageOut.setPageSize(size);
        pageOut.setPage(from);

        return new ArrayList<>(pageOut.getPageList());
    }

    @Override
    public void deleteUser(Integer userId) {
        userRepository.deleteById(userId);
        log.info("Запрос на удаление пользователя с id {} выполнен", userId);
    }
}
