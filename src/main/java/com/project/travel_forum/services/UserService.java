package com.project.travel_forum.services;

import com.project.travel_forum.models.FilterOptions;
import com.project.travel_forum.models.User;

import java.util.List;

public interface UserService {
    List<User> getUser(FilterOptions filterOptions); //username, email, name
    List<User> getAll();
    User getById(int id, User headersUser);
    User getByUsername(String username);
    void createUser(User user);
    void updateUser(User user, User userToUpdate);
    void deleteUser(int id);
    void makeAdmin (User user);
    void makeUser(User user);
    void blockUser (User user);
    void unblockUser(User user);

}
