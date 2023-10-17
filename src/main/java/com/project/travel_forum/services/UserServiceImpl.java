package com.project.travel_forum.services;

import com.project.travel_forum.exceptions.AuthorizationException;
import com.project.travel_forum.exceptions.EntityDuplicateException;
import com.project.travel_forum.exceptions.EntityNotFoundException;
import com.project.travel_forum.exceptions.UnauthorizedOperationException;
import com.project.travel_forum.models.FilterOptions;
import com.project.travel_forum.models.User;
import com.project.travel_forum.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {
    public static final String USER_IS_ADMIN = "This user is admin";
    public static final String USER_IS_NOT_ADMIN = "This user is not admin";
    public static final String NOT_AUTHORIZED_USER = "You are not authorized user.";
    public static final String NOT_USER_TO_UPDATE = "You are not user to update.";
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
        return userRepository.getAll();
    }

    @Override
    public User getById(int id, User headersUser) {
        if (id == headersUser.getId() || headersUser.isAdmin()) {
            return userRepository.getById(id);
        } else {
            throw new UnauthorizedOperationException(NOT_AUTHORIZED_USER);
        }
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
        checkModifyPermissions(user, userToUpdate);

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
        // -- TODO --
    }

    @Override
    public void makeAdmin(User user) {
        if (user.isAdmin()) {
            throw new AuthorizationException(USER_IS_ADMIN);
        }
        user.setAdmin(true);
    }

    @Override
    public void makeUser(User user) {
        if (!user.isAdmin()) {
            throw new AuthorizationException(USER_IS_NOT_ADMIN);
        }
        user.setAdmin(false);

    }

    @Override
    public void blockUser(User user) {
        // -- TODO --
    }

    @Override
    public void unblockUser(User user) {
        // -- TODO --
    }

    private void checkModifyPermissions(User user, User userToUpdate) {
        if (!(user.getId() == userToUpdate.getId())) {
            throw new AuthorizationException(NOT_USER_TO_UPDATE);
        }
    }
}
