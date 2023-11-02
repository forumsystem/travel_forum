package com.project.travel_forum.controllers.rest;

import com.project.travel_forum.controllers.AuthenticationHelper;
import com.project.travel_forum.exceptions.EntityDuplicateException;
import com.project.travel_forum.exceptions.EntityNotFoundException;
import com.project.travel_forum.exceptions.UnauthorizedOperationException;
import com.project.travel_forum.helpers.PhoneNumberMapper;
import com.project.travel_forum.helpers.UserMapper;
import com.project.travel_forum.models.*;
import com.project.travel_forum.services.PhoneNumberService;
import com.project.travel_forum.services.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;


import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserController {
    private final UserService userService;
    private final PhoneNumberService phoneNumberService;
    private final AuthenticationHelper authenticationHelper;
    private final UserMapper userMapper;
    private final PhoneNumberMapper phoneNumberMapper;

    @Autowired
    public UserController(UserService userService, PhoneNumberService phoneNumberService,
                          AuthenticationHelper authenticationHelper, UserMapper userMapper,
                          PhoneNumberMapper phoneNumberMapper) {
        this.userService = userService;
        this.phoneNumberService = phoneNumberService;
        this.authenticationHelper = authenticationHelper;
        this.userMapper = userMapper;
        this.phoneNumberMapper = phoneNumberMapper;
    }

    @GetMapping
    public List<User> get(
            @RequestHeader HttpHeaders headers,
            @RequestParam(required = false) String firstName,
            @RequestParam(required = false) String email,
            @RequestParam(required = false) String username,
            @RequestParam(required = false) String sortBy,
            @RequestParam(required = false) String sortOrder) {
        FilterUserOptions filterUserOptions = new FilterUserOptions(firstName, email, username, sortBy, sortOrder);
        try {
            User headersUser = authenticationHelper.tryGetUser(headers);
            return userService.get(headersUser, filterUserOptions);

        } catch (EntityNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        } catch (UnauthorizedOperationException e) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, e.getMessage());
        }
    }

    @GetMapping("/{id}")
    public User getById(@PathVariable int id, @RequestHeader HttpHeaders headers) {
        try {
            User headersUser = authenticationHelper.tryGetUser(headers);
            return userService.getById(id, headersUser);
        } catch (EntityNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        } catch (UnauthorizedOperationException e) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, e.getMessage());
        }
    }


    @PostMapping
    public User create(@Valid @RequestBody RegisterDto registerDto) {
        try {
            User user = userMapper.fromDto(registerDto);
            userService.createUser(user);
            return user;
        } catch (EntityNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        } catch (EntityDuplicateException e) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, e.getMessage());
        } catch (UnauthorizedOperationException e) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, e.getMessage());
        }
    }

    @PutMapping("/{id}")
    public User update(@RequestHeader HttpHeaders headers, @PathVariable int id, @Valid @RequestBody RegisterDto registerDto) {
        try {
            User user = authenticationHelper.tryGetUser(headers);
            User userToUpdate = userMapper.fromDto(id, registerDto, user);
            userService.updateUser(user, userToUpdate);
            return userToUpdate;
        } catch (EntityNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        } catch (EntityDuplicateException e) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, e.getMessage());
        } catch (UnauthorizedOperationException e) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public void delete(@RequestHeader HttpHeaders headers, @PathVariable int id) {
        try {
            User user = authenticationHelper.tryGetUser(headers);
            userService.deleteUser(id, user);
        } catch (EntityNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        } catch (UnauthorizedOperationException e) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, e.getMessage());
        }
    }

    ///users/admin/{id}?isAdmin=true / false
    @PatchMapping("/{id}/admin")
    public void modifyPermissions(@RequestHeader HttpHeaders headers, @PathVariable int id, @RequestParam boolean isAdmin) {
        try {
            boolean adminFlag = isAdmin;
            User user = authenticationHelper.tryGetUser(headers);
            userService.modifyPermissions(id, user, adminFlag);
        } catch (EntityNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        } catch (EntityDuplicateException e) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, e.getMessage());
        } catch (UnauthorizedOperationException e) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, e.getMessage());
        }
    }

    @PatchMapping("/{id}/block/")
    public void modifyBlock(@RequestHeader HttpHeaders headers, @PathVariable int id, @RequestParam boolean isBlocked) {
        try {
            boolean blockFlag = isBlocked;
            User user = authenticationHelper.tryGetUser(headers);
            userService.modifyBlock(id, user, blockFlag);
        } catch (EntityNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        } catch (EntityDuplicateException e) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, e.getMessage());
        } catch (UnauthorizedOperationException e) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, e.getMessage());
        }
    }

    @PostMapping("/phone")
    public PhoneNumber create(@RequestHeader HttpHeaders headers, @Valid @RequestBody PhoneNumberDto phoneNumberDto) {
        try {
            PhoneNumber phoneNumber = phoneNumberMapper.fromDto(phoneNumberDto);
            User user = authenticationHelper.tryGetUser(headers);

            phoneNumberService.create(phoneNumber, user);
            return phoneNumber;

        } catch (EntityNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        } catch (EntityDuplicateException e) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, e.getMessage());
        } catch (UnauthorizedOperationException e) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, e.getMessage());
        }
    }

    @PutMapping("/phone")
    public PhoneNumber update(@RequestHeader HttpHeaders headers, @Valid @RequestBody PhoneNumberDto phoneNumberDto) {
        try {
            User user = authenticationHelper.tryGetUser(headers);
            PhoneNumber phoneNumber = phoneNumberMapper.fromDto(phoneNumberDto, user);

            phoneNumberService.update(phoneNumber);
            return phoneNumber;

        } catch (EntityNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        } catch (EntityDuplicateException e) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, e.getMessage());
        } catch (UnauthorizedOperationException e) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, e.getMessage());
        }
    }

    @DeleteMapping("/phone")
    public void delete(@RequestHeader HttpHeaders headers) {
        try {
            User user = authenticationHelper.tryGetUser(headers);

            phoneNumberService.delete(user);

        } catch (EntityNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        } catch (EntityDuplicateException e) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, e.getMessage());
        } catch (UnauthorizedOperationException e) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, e.getMessage());
        }
    }

}















