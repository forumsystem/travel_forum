package com.project.travel_forum.helpers;

import com.project.travel_forum.exceptions.UnauthorizedOperationException;
import com.project.travel_forum.models.Post;
import com.project.travel_forum.models.User;

public class CheckPermissions {
    public static final String AUTH_ERR_MESSAGE = "You are not authorized to perform this operation";


    public static void checkIfSameUser(User user, User userToUpdate) {
        if (!(user.getId() == userToUpdate.getId())) {
            throw new UnauthorizedOperationException(AUTH_ERR_MESSAGE);
        }
    }

    public static void checkIfAdmin(User user) {
        if (!user.isAdmin()) {
            throw new UnauthorizedOperationException(AUTH_ERR_MESSAGE);
        }
    }

    public static void checkIfSameUserOrAdmin(User user, Post post) {
        if (!(user.equals(post.getCreatedBy()) || user.isAdmin())) {
            throw new UnauthorizedOperationException(AUTH_ERR_MESSAGE);
        }
    }

    public static void checkIfSameUserOrAdmin(int userId, User user) {
        if (!(userId == user.getId() || user.isAdmin())) {
            throw new UnauthorizedOperationException(AUTH_ERR_MESSAGE);
        }
    }

    public static void checkIfBlocked(User user) {
        if (user.isBlocked()) {
            throw new UnauthorizedOperationException(AUTH_ERR_MESSAGE);
        }
    }

    public static void checkUserAuthorization(int targetUserId, User executingUser) {
        if (!executingUser.isAdmin() && executingUser.getId() != targetUserId) {
            throw new UnauthorizedOperationException(AUTH_ERR_MESSAGE);
        }
    }

}
