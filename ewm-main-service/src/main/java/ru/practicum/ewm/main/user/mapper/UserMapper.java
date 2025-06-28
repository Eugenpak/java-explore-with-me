package ru.practicum.ewm.main.user.mapper;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import ru.practicum.ewm.main.user.dto.UserRequestDto;
import ru.practicum.ewm.main.user.dto.UserResponseDto;
import ru.practicum.ewm.main.user.entity.User;

import java.util.List;
import java.util.stream.Collectors;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class UserMapper {
    public static User mapToUser(UserRequestDto userRequestDto) {
        return new User(
                null,
                userRequestDto.getName(),
                userRequestDto.getEmail()
        );
    }

    public static UserResponseDto mapToUserDto(User user) {
        return new UserResponseDto(
                user.getId(),
                user.getName(),
                user.getEmail()
        );
    }

    public static List<UserResponseDto> mapToListDto(List<User> users) {
        return users.stream()
                .map(UserMapper::mapToUserDto)
                .collect(Collectors.toList());
    }
}
