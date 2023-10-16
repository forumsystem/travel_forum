package com.project.travel_forum.repositories;

import com.project.travel_forum.models.FilterOptions;
import com.project.travel_forum.models.User;

import java.util.List;

public class UserRepositoryImpl implements UserRepository{

    @Override
    public List<User> getUser(FilterOptions filterOptions) {
        //todo: implement
        return null;
    }

    @Override
    public void createUser(User user) {
    //todo: implement
    }

    @Override
    public void updateUser(User user) {
        //todo: implement
    }

    @Override
    public void deleteUser(int id) {
        //todo: implement
    }

    @Override
    public void makeAdmin(User user) {
        //todo: implement
    }

    @Override
    public void makeUser(User user) {
        //todo: implement
    }

    @Override
    public void blockUser(User user) {
        //todo: implement
    }

    @Override
    public void unblockUser(User user) {
        //todo: implement
    }
}
