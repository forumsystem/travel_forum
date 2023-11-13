package com.project.travel_forum.repositories;

import com.project.travel_forum.models.FilterUserOptions;
import com.project.travel_forum.models.User;

import java.util.List;

public interface UserRepository {

    long getUserCount();

    List<User> get(FilterUserOptions filterUserOptions);

    List<User> getAllBlockUser();

    List<User> getAllAdmins();

    User getById(int id);

    User getByEmail(String email);

    User getByUsername(String username);

    void createUser(User user);

    void updateUser(User user);

    void deleteUser(int id);

    void modifyPermissions(User user);

    void modifyBlock(User user);


}
