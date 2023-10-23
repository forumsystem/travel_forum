package com.project.travel_forum.services;

import com.project.travel_forum.models.FilterOptions;
import com.project.travel_forum.models.FilterUserOptions;
import com.project.travel_forum.models.User;

import java.util.List;

public interface UserService {
    List<User> get(User user, FilterUserOptions filterUserOptions);

    User getById(int id, User headersUser);

    User getById(int id);

    User getByUsername(String username);

    void createUser(User user);

    void updateUser(User user, User userToUpdate);

    void deleteUser(int id, User user);

    void modifyPermissions(int id, User user, boolean adminFlag);

    void modifyBlock(int id, User user, boolean blockFlag);

}
