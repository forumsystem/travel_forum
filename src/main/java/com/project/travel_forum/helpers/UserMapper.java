package com.project.travel_forum.helpers;

import com.project.travel_forum.models.User;
import com.project.travel_forum.models.UserDto;
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

    public User fromDto(int id, UserDto dto) { //todo look it up
        User user = fromDto(dto);
        user.setId(id);
//        beer.setCreatedBy(repositoryBeer.getCreatedBy());
        return user;
    }

    public User fromDto(UserDto dto) {
        User user = new User();
        user.setFirstName(dto.getFirstName());
        user.setLastName(dto.getLastName());
        user.setEmail(dto.getEmail());
        user.setUsername(dto.getUsername());
        user.setPassword(dto.getPassword());

        return user;
    }
}
