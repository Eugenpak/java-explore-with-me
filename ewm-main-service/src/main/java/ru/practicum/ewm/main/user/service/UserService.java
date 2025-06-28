package ru.practicum.ewm.main.user.service;

import ru.practicum.ewm.main.user.dto.UserRequestDto;
import ru.practicum.ewm.main.user.dto.UserResponseDto;

import java.util.List;

public interface UserService {
    List<UserResponseDto> getAllUsers(List<Long> ids, int from, int size);

    UserResponseDto createUser(UserRequestDto userRequestDto);

    void deleteUser(Long userId);
}
