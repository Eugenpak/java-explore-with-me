package ru.practicum.ewm.main.user.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.ewm.main.exception.DuplicatedDataException;
import ru.practicum.ewm.main.exception.NotFoundException;
import ru.practicum.ewm.main.user.dao.UserRepository;
import ru.practicum.ewm.main.user.dto.UserRequestDto;
import ru.practicum.ewm.main.user.dto.UserResponseDto;
import ru.practicum.ewm.main.user.entity.User;
import ru.practicum.ewm.main.user.mapper.UserMapper;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;

    @Override
    public List<UserResponseDto> getAllUsers(List<Long> ids, int from, int size) {
        PageRequest pageRequest = PageRequest.of(from / size, size);
        List<User> users;
        if (Objects.isNull(ids) || ids.isEmpty()) {
            users = userRepository.findAll(pageRequest).getContent();
        } else {
            users = userRepository.findByIdIn(ids, pageRequest);
        }
        return UserMapper.mapToListDto(users);
    }

    @Override
    @Transactional
    public UserResponseDto createUser(UserRequestDto userRequestDto) {
        final String email = userRequestDto.getEmail();
        Optional<User> optionalUser = userRepository.findByEmailIgnoreCase(email);
        if (optionalUser.isPresent()) {
            throw new DuplicatedDataException("Пользователь с таким email = " + email +
                    " уже существует.");
        }

        User user = userRepository.save(UserMapper.mapToUser(userRequestDto));
        return UserMapper.mapToUserDto(user);
    }

    @Override
    @Transactional
    public void deleteUser(Long userId) {
        Optional<User> optionalUser = userRepository.findById(userId);
        if (optionalUser.isEmpty()) {
            throw new NotFoundException("Пользователь с таким id = " + userId +
                    " не найден или недоступен.");
        }
        userRepository.deleteById(userId);
    }
}
