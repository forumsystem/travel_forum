package com.project.travel_forum.helpers;

import com.project.travel_forum.exceptions.AuthorizationException;
import com.project.travel_forum.models.Post;
import com.project.travel_forum.models.User;

public class CheckPermissions {

    public static final String USER_IS_NOT_ADMIN = "This user is not admin";
    public static final String NOT_USER_TO_UPDATE = "You are not user to update.";
    public static final String NOT_USER_OR_ADMIN = "This user is not creator or admin.";
    public static final String BLOCKED_USER = "This user is blocked.";
    public static final String ERROR_MESSAGE = "You are not authorized to browse user information.";


    public static void checkIfSameUser(User user, User userToUpdate) {
        if (!(user.getId() == userToUpdate.getId())) {
            throw new AuthorizationException(NOT_USER_TO_UPDATE);
        }
    }

    public static void checkIfAdmin(User user) {
        if (!user.isAdmin()) {
            throw new AuthorizationException(USER_IS_NOT_ADMIN);
        }
    }

    //TODO: check if such method exist --- @Simona
    public static void checkIfSameUserOrAdmin(User user, Post post) {
        if (!(user.equals(post.getCreatedBy()) || user.isAdmin())) {
            throw new AuthorizationException(NOT_USER_OR_ADMIN);
        }
    }

    public static void checkIfBlocked(User user) {
        if (user.isBlocked()) {
            throw new AuthorizationException(BLOCKED_USER);
        }
    }
    public static void checkUserAuthorization(int targetUserId, User executingUser) {
        if (!executingUser.isAdmin() && executingUser.getId() != targetUserId) {
            throw new AuthorizationException(ERROR_MESSAGE);
        }
    }

}
