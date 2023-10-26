package com.project.travel_forum.services;

import com.project.travel_forum.exceptions.EntityDuplicateException;
import com.project.travel_forum.exceptions.EntityNotFoundException;
import com.project.travel_forum.exceptions.UnauthorizedOperationException;
import com.project.travel_forum.models.FilterUserOptions;
import com.project.travel_forum.models.User;
import com.project.travel_forum.repositories.UserRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import static com.project.travel_forum.UserHelpers.*;

@ExtendWith(MockitoExtension.class)
public class UserServiceTests {

    @Mock
    UserRepository mockRepository;

    @InjectMocks
    UserServiceImpl service;

    @Test
    void get_Should_CallRepository_When_UserIsAdmin() {
        // Arrange
        FilterUserOptions mockFilterUserOptions = createMockFilterUserOptions();
        User mockAdmin = createMockAdmin();

        Mockito.when(mockRepository.get(mockFilterUserOptions))
                .thenReturn(null);

        // Act
        service.get(mockAdmin, mockFilterUserOptions);

        // Assert if we call the repo
        Mockito.verify(mockRepository, Mockito.times(1))
                .get(mockFilterUserOptions);
    }

    @Test
    void getById_Should_ReturnUser_When_UserIsAdminOrSameUser() {
        // Arrange
        User mockUser = createMockUser();
        User mockAdmin = createMockAdmin();

        Mockito.when(mockRepository.getById(Mockito.anyInt()))
                .thenReturn(mockUser);

        // Act
        User result = service.getById(1, mockAdmin);

        // Assert
        Assertions.assertEquals(mockUser, result);
        Mockito.verify(mockRepository, Mockito.times(1))
                .getById(1);
    }

    @Test
    void getById_Should_ThrowException_When_UserIsNotAdminOrSameUser() {
        // Arrange
        User mockUser = createMockUser();

        // Act, Assert
        Assertions.assertThrows(
                UnauthorizedOperationException.class,
                () -> service.getById(2, mockUser));
    }

    @Test
    void createUser_Should_CallRepository_When_EmailNotExists() {
        // Arrange
        User mockUser = createMockUser();

        Mockito.when(mockRepository.getByEmail(mockUser.getEmail()))
                .thenThrow(EntityNotFoundException.class);

        // Act
        service.createUser(mockUser);

        // Assert
        Mockito.verify(mockRepository, Mockito.times(1))
                .createUser(mockUser);
    }

    @Test
    void createUser_Should_ThrowException_When_EmailExists() {
        // Arrange
        User mockUser = createMockUser();

        Mockito.when(mockRepository.getByEmail(mockUser.getEmail()))
                .thenReturn(mockUser);

        // Act, Assert
        Assertions.assertThrows(
                EntityDuplicateException.class,
                () -> service.createUser(mockUser));
    }

        @Test
    void updateUser_Should_CallRepository_When_EmailNotExists() {
        // Arrange
        User mockUser = createMockUser();
        User mockUserToUpdate = createMockUser();

        Mockito.when(mockRepository.getByEmail(mockUserToUpdate.getEmail()))
                .thenThrow(EntityNotFoundException.class);

        // Act
        service.updateUser(mockUser, mockUserToUpdate);

        // Assert
        Mockito.verify(mockRepository, Mockito.times(1))
                .updateUser(mockUserToUpdate);
    }

    @Test
    void updateUser_Should_ThrowException_When_EmailExists() {
        // Arrange
        User mockUser = createMockUser();
        User mockUserToUpdate = createMockUser();

        Mockito.when(mockRepository.getByEmail(mockUserToUpdate.getEmail()))
                .thenReturn(mockUser);

        // Act, Assert
        Assertions.assertThrows(
                EntityDuplicateException.class,
                () -> service.updateUser(mockUser, mockUserToUpdate));
    }

        @Test
    void deleteUser_Should_CallRepository_When_UserIsAuthorized() {
        // Arrange
        User mockUser = createMockUser();
        User mockAdmin = createMockAdmin();

        Mockito.when(mockRepository.getById(1))
                .thenReturn(mockUser);

        // Act
        service.deleteUser(1, mockAdmin);

        // Assert
        Mockito.verify(mockRepository, Mockito.times(1))
                .deleteUser(1);
    }

    @Test
    void deleteUser_Should_ThrowException_When_UserIsNotAuthorized() {
        // Arrange
        User mockUser = createMockUser();

        // Act, Assert
        Assertions.assertThrows(
                UnauthorizedOperationException.class,
                () -> service.deleteUser(2, mockUser));
    }

    @Test
    void modifyPermissions_Should_CallRepository_When_UserIsAdmin() {
        // Arrange
        User mockAdmin = createMockAdmin();
        User mockUserToModify = createMockUser();

        Mockito.when(mockRepository.getById(1))
                .thenReturn(mockUserToModify);

        // Act
        service.modifyPermissions(1, mockAdmin, true);

        // Assert
        Mockito.verify(mockRepository, Mockito.times(1))
                .modifyPermissions(mockUserToModify);
    }

        @Test
    void modifyPermissions_Should_ThrowException_When_UserIsNotAdmin() {
        // Arrange
        User mockUser = createMockUser();
        User mockUserToModify = createMockUser();
        mockUserToModify.setId(2);

        // Act, Assert
        Assertions.assertThrows(
                UnauthorizedOperationException.class,
                () -> service.modifyPermissions(1, mockUser, true));
    }

        @Test
    void modifyBlock_Should_CallRepository_When_UserIsAdmin() {
        // Arrange
        User mockAdmin = createMockAdmin();
        User mockUserToModify = createMockUser();

        Mockito.when(mockRepository.getById(1))
                .thenReturn(mockUserToModify);

        // Act
        service.modifyBlock(1, mockAdmin, true);

        // Assert
        Mockito.verify(mockRepository, Mockito.times(1))
                .modifyBlock(mockUserToModify);
    }

    @Test
    void modifyBlock_Should_ThrowException_When_UserIsNotAdmin() {
        // Arrange
        User mockUser = createMockUser();
        User mockUserToModify = createMockUser();
        mockUserToModify.setId(2);

        // Act, Assert
        Assertions.assertThrows(
                UnauthorizedOperationException.class,
                () -> service.modifyBlock(1, mockUser, true));
    }

}

