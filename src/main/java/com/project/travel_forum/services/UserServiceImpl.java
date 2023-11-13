package com.project.travel_forum.services;

import com.project.travel_forum.exceptions.EntityDuplicateException;
import com.project.travel_forum.exceptions.EntityNotFoundException;
import com.project.travel_forum.exceptions.UnauthorizedOperationException;
import com.project.travel_forum.models.*;
import com.project.travel_forum.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.project.travel_forum.helpers.CheckPermissions.*;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    @Autowired
    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public long getUserCount() {
        return userRepository.getUserCount();
    }

    @Override
    public List<User> get(User user, FilterUserOptions filterUserOptions) {
        checkIfAdmin(user);
        return userRepository.get(filterUserOptions);
    }

    @Override
    public List<User> getAllBlockUser() {
        return userRepository.getAllBlockUser();
    }

    @Override
    public List<User> getAllAdmins() {
        return userRepository.getAllAdmins();
    }

    @Override
    public User get(int id) {
        return userRepository.getById(id);

    }

    @Override
    public User getById(int id, User user) {
        checkIfSameUserOrAdmin(id, user);
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
    public User updateUserV2(User user, User updatedUser, UpdateUserDto updateUserDto) {

        checkIfSameUser(user, updatedUser);

        if (updateUserDto.getFirstName() != null) {
            updatedUser.setFirstName(updateUserDto.getFirstName());
        }
        if (updateUserDto.getLastName() != null) {
            updatedUser.setLastName(updateUserDto.getLastName());
        }
        if (updateUserDto.getEmail() != null) {
            updatedUser.setEmail(updateUserDto.getEmail());
        }
        if (updateUserDto.getPassword() != null) {
            updatedUser.setPassword(updateUserDto.getPassword());
        }
//        if ((updateUserDto.getPhoneNumber() != null && user.getId() == updatedUser.getId())) {
//            addPhoneNumberToAdmin(updatedUser, updateUserDto.getPhoneNumber());
//        }
        userRepository.updateUser(updatedUser);
        return updatedUser;
    }

    @Override
    public void deleteUser(int id, User user) {
        checkUserAuthorization(id, user);

        boolean userExists = true;

        try {
            userRepository.getById(id);
        } catch (EntityNotFoundException e) {
            userExists = false;
        }
        if (userExists) {
            userRepository.deleteUser(id);
        }
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
