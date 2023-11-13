package com.project.travel_forum.helpers;

import com.project.travel_forum.models.User;
import com.project.travel_forum.models.RegisterDto;
import com.project.travel_forum.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {
    private final UserService userService;

    @Autowired
    public UserMapper(UserService userService) {
        this.userService = userService;
    }

    public User fromDto(int id, RegisterDto dto, User user) {
        User existingUser = userService.getById(id, user);

        existingUser.setFirstName(dto.getFirstName());
        existingUser.setLastName(dto.getLastName());
        existingUser.setEmail(dto.getEmail());
        existingUser.setUsername(dto.getUsername());
        existingUser.setPassword(dto.getPassword());

        return existingUser;
    }


    public User fromDto(RegisterDto registerDto) {
        User user = new User();

        user.setFirstName(registerDto.getFirstName());
        user.setLastName(registerDto.getLastName());
        user.setEmail(registerDto.getEmail());
        user.setUsername(registerDto.getUsername());
        user.setPassword(registerDto.getPassword());

        return user;
    }

}
