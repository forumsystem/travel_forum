package com.project.travel_forum.services;

import com.project.travel_forum.models.User;

import java.util.List;

public interface UserService {
    List<User> get();
    User getById(int id);
    User getByEmail(String email);
    User getByUsername(String username);
    User getByName(String firstName);
    void addComment(int userId, int postId);
    void addLike(int userId, int postId);
}
