package com.kretsev.calorietracker.service;

import com.kretsev.calorietracker.dto.UserDto;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;

public interface UserService {
    UserDto createUser(UserDto userDTO);

    UserDto getUserById(Long id);

    Page<UserDto> getAllUsers(int page, int size);

    UserDto updateUser(Long id, @Valid UserDto userDTO);

    void deleteUser(Long id);
}
