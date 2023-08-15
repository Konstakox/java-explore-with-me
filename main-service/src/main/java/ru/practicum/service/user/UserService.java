package ru.practicum.service.user;

import ru.practicum.dto.user.UserDto;

import java.util.List;

public interface UserService {
    UserDto createUser(UserDto userDto);

    List<UserDto> getUsers(List<Integer> ids, Integer from, Integer size);

    void deleteUser(Integer userId);
}
