package com.project.travel_forum.repositories;

import com.project.travel_forum.models.User;

import java.util.List;

public interface UserRepository {
    List<User> get(String firstName, String lastName, String email, String username);
    User getById(int id);
    User getByEmail(String email);
    User getByUsername(String username);
    User getByName(String firstName);
    void createUser(User user);
    void updateUser(User user);
    void deleteUser(int id);

}
