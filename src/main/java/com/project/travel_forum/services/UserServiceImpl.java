package com.project.travel_forum.services;

import com.project.travel_forum.exceptions.EntityDuplicateException;
import com.project.travel_forum.exceptions.EntityNotFoundException;
import com.project.travel_forum.exceptions.UnauthorizedOperationException;
import com.project.travel_forum.models.FilterOptions;
import com.project.travel_forum.models.User;
import com.project.travel_forum.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.project.travel_forum.helpers.CheckPermissions.*;

@Service
public class UserServiceImpl implements UserService {

    public static final String NOT_AUTHORIZED_USER = "This user is not authorized";

    private final UserRepository userRepository;

    @Autowired
    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public List<User> getUser(FilterOptions filterOptions) {

        return userRepository.getUser(filterOptions);
    }

    @Override
    public List<User> getAll() {
        //todo: add filter/sort here?
        return userRepository.getAll();
    }

    @Override
    public User getById(int id, User user) {
        if (id == user.getId() || user.isAdmin()) {
            return userRepository.getById(id);
        } else {
            throw new UnauthorizedOperationException(NOT_AUTHORIZED_USER);
        }
    }

    @Override
    public User getById(int id) {
        return userRepository.getById(id);
    }

    @Override
    public User getByUsername(String username) {
        return userRepository.getByUsername(username);
    }

    @Override
    public void createUser(User user) {
        boolean emailExists = true;
        try {
            userRepository.getByEmail(user.getEmail());
        } catch (EntityNotFoundException e) {
            emailExists = false;
        }
        if (emailExists) {
            throw new EntityDuplicateException("User", "email", user.getEmail());
        }
        userRepository.createUser(user);
    }

    @Override
    public void updateUser(User user, User userToUpdate) {
        checkIfSameUser(user, userToUpdate);

        boolean emailExists = true;
        try {
            userRepository.getByEmail(userToUpdate.getEmail());
        } catch (EntityNotFoundException e) {
            emailExists = false;
        }
        if (emailExists) {
            throw new EntityDuplicateException("User", "email", user.getEmail());
        }
        userRepository.updateUser(userToUpdate);
    }

    @Override
    public void deleteUser(int id) {
        userRepository.deleteUser(id);
    }

    @Override
    public void modifyPermissions(int id, User user, boolean adminFlag) {

        checkIfAdmin(user);
        checkIfBlocked(user);

        User userToModify = userRepository.getById(id);

        if (adminFlag) {
            userToModify.setAdmin(true);
            userRepository.modifyPermissions(userToModify);
        }
        if (!adminFlag) {
            userToModify.setAdmin(false);
            userRepository.modifyPermissions(userToModify);
        }
    }

    @Override
    public void modifyBlock(int id, User user, boolean blockFlag) {

        checkIfAdmin(user);
        checkIfBlocked(user);

        User userToModify = userRepository.getById(id);

        if (blockFlag) {
            userToModify.setBlocked(true);
            userRepository.modifyBlock(userToModify);
        }
        if (!blockFlag) {
            userToModify.setBlocked(false);
            userRepository.modifyBlock(userToModify);
        }
    }


}
