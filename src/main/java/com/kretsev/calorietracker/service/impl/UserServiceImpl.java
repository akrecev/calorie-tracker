package com.kretsev.calorietracker.service.impl;

import com.kretsev.calorietracker.dto.UserDto;
import com.kretsev.calorietracker.mapper.UserMapper;
import com.kretsev.calorietracker.model.User;
import com.kretsev.calorietracker.repository.UserRepository;
import com.kretsev.calorietracker.service.EntityService;
import com.kretsev.calorietracker.service.LoggingService;
import com.kretsev.calorietracker.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final EntityService entityService;
    private final LoggingService loggingService;

    @Override
    @Transactional
    public UserDto createUser(UserDto userDTO) {
        User user = userMapper.toEntity(userDTO);
        loggingService.logInfo("Пользователь успешно создан: id={}, email={}", user.getId(), user.getEmail());

        return userMapper.toUserDto(userRepository.save(user));
    }

    @Override
    public UserDto getUserById(Long id) {
        return userMapper.toUserDto(getUserInner(id));
    }

    @Override
    public Page<UserDto> getAllUsers(int page, int size) {
        return userRepository.findAll(PageRequest.of(page, size)).map(userMapper::toUserDto);
    }

    @Override
    @Transactional
    public UserDto updateUser(Long id, UserDto userDto) {
        User user = getUserInner(id);
        user.setName(userDto.name());
        user.setEmail(userDto.email());
        user.setAge(userDto.age());
        user.setWeight(userDto.weight());
        user.setHeight(userDto.height());
        user.setGoal(userDto.goal());
        loggingService.logInfo("Пользователь обновлен: id={}, email={}", user.getId(), user.getEmail());

        return userMapper.toUserDto(userRepository.save(user));
    }

    @Override
    @Transactional
    public void deleteUser(Long id) {
        User user = getUserInner(id);
        userRepository.deleteById(id);
        loggingService.logInfo("Пользователь удален: id={}, email={}", user.getId(), user.getEmail());
    }

    private User getUserInner(Long id) {
        return entityService.findEntityOrElseThrow(userRepository, id, "Пользователь не найден");
    }
}
