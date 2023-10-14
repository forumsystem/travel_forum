package com.project.travel_forum.repositories;

import com.project.travel_forum.models.User;

import java.util.List;

public class UserRepositoryImpl implements UserRepository{

    @Override
    public List<User> get(String firstName, String lastName, String email, String username) {
        return null;
    }

    @Override
    public User getById(int id) {
        return null;
    }

    @Override
    public User getByEmail(String email) {
        return null;
    }

    @Override
    public User getByUsername(String username) {
        return null;
    }

    @Override
    public User getByName(String firstName) {
        return null;
    }

    @Override
    public void createUser(User user) {

    }

    @Override
    public void updateUser(User user) {

    }

    @Override
    public void deleteUser(int id) {

    }
}
