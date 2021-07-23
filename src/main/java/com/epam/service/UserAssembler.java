package com.epam.service;

import com.epam.dto.UserDto;
import com.epam.entity.User;
/**
    Converts UserDTO to User entity and vice versa.
 */
public interface UserAssembler {
    User assemble(UserDto userDto);
    UserDto assemble(User user);
}
