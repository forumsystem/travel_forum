package com.project.travel_forum.controllers;

import com.project.travel_forum.exceptions.AuthorizationException;
import com.project.travel_forum.exceptions.EntityNotFoundException;
import com.project.travel_forum.exceptions.UnauthorizedOperationException;
import com.project.travel_forum.models.User;
import com.project.travel_forum.services.UserService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;


@Component
public class AuthenticationHelper {
    public static final String AUTHORIZATION_HEADER_NAME = "Authorization";
    public static final String AUTHENTICATION_ERROR = "The requested resource requires authentication.";
    public static final String INVALID_AUTHENTICATION_ERROR = "Invalid authentication";

    private final UserService userService;

    @Autowired
    public AuthenticationHelper(UserService userService) {
        this.userService = userService;
    }

    public User tryGetUser(HttpHeaders headers) {
        if (!headers.containsKey(AUTHORIZATION_HEADER_NAME)) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED,
                    AUTHENTICATION_ERROR);
        }

        try {
            String userInfo = headers.getFirst(AUTHORIZATION_HEADER_NAME);
            String username = getUsername(userInfo);
            String password = getPassword(userInfo);

            User user = userService.getByUsername(username);

            if (!user.getPassword().equals(password)) {
                throw new UnauthorizedOperationException(INVALID_AUTHENTICATION_ERROR);
            }

            return user;
        } catch (EntityNotFoundException e) {
            throw new UnauthorizedOperationException(AUTHENTICATION_ERROR);
        }
    }

    public User verifyAuthentication(String username, String password) {

        try {
            User user = userService.getByUsername(username);

            if (!user.getPassword().equals(password)) {
                throw new AuthorizationException(INVALID_AUTHENTICATION_ERROR);
            }
            return user;

        } catch (EntityNotFoundException e) {
            throw new AuthorizationException(INVALID_AUTHENTICATION_ERROR);
        }

    }


    private String getUsername(String userInfo) {
        int firstSpace = userInfo.indexOf(" ");

        if (firstSpace == -1) {
            throw new AuthorizationException(AUTHENTICATION_ERROR);
        }
        return userInfo.substring(0, firstSpace);

    }

    private String getPassword(String userInfo) {
        int firstSpace = userInfo.indexOf(" ");

        if (firstSpace == -1) {
            throw new AuthorizationException(AUTHENTICATION_ERROR);
        }
        return userInfo.substring(firstSpace + 1);

    }

    public User tryGetCurrentUser(HttpSession session) {
        String currentUsername = (String) session.getAttribute("currentUser");

        if (currentUsername == null) {
            throw new AuthorizationException(INVALID_AUTHENTICATION_ERROR);
        }
        return userService.getByUsername(currentUsername);
    }
}
