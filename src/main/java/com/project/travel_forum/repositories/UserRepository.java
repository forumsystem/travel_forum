package com.project.travel_forum.repositories;

import com.project.travel_forum.models.FilterOptions;
import com.project.travel_forum.models.User;

import java.util.List;

public interface UserRepository {

    //todo: filter & sort --- @Dora
    List<User> getUser(FilterOptions filterOptions); //username, email, name

    List<User> getAll();

    User getById(int id);

    User getByEmail(String email);

    User getByUsername(String username);

    void createUser(User user);

    void updateUser(User user);

    void deleteUser(int id);

    void modifyPermissions(User user);

    void modifyBlock(User user);

}
