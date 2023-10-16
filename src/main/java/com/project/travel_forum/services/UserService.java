package com.project.travel_forum.services;

import com.project.travel_forum.models.FilterOptions;
import com.project.travel_forum.models.User;

import java.util.List;

public interface UserService {
    List<User> getUser(FilterOptions filterOptions); //username, email, name
    void createUser(User user);
    void updateUser(User user);
    void deleteUser(int id);
    void makeAdmin (User user);
    void makeUser(User user);
    void blockUser (User user);
    void unblockUser(User user);
}
