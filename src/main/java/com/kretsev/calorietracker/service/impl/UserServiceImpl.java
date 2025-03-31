package com.kretsev.calorietracker.service.impl;

import com.kretsev.calorietracker.dto.UserDto;
import com.kretsev.calorietracker.mapper.UserMapper;
import com.kretsev.calorietracker.model.User;
import com.kretsev.calorietracker.repository.UserRepository;
import com.kretsev.calorietracker.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;

    @Override
    public UserDto createUser(UserDto userDTO) {
        User user = userMapper.toEntity(userDTO);
        return userMapper.toUserDto(userRepository.save(user));
    }
}
