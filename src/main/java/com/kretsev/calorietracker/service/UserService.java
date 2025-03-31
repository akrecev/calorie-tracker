package com.kretsev.calorietracker.service;

import com.kretsev.calorietracker.dto.UserDto;

public interface UserService {
    UserDto createUser(UserDto userDTO);
}
