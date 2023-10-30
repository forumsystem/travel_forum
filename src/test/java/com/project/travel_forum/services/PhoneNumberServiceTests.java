package com.project.travel_forum.services;

import com.project.travel_forum.exceptions.EntityDuplicateException;
import com.project.travel_forum.exceptions.EntityNotFoundException;
import com.project.travel_forum.models.PhoneNumber;
import com.project.travel_forum.models.User;
import com.project.travel_forum.repositories.PhoneNumberRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import static com.project.travel_forum.MockHelpers.createMockAdmin;
import static com.project.travel_forum.MockHelpers.createMockPhoneNumber;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class PhoneNumberServiceTests {

    @Mock
    PhoneNumberRepository mockPhoneNumberRepository;

    @InjectMocks
    PhoneNumberServiceImpl phoneNumberService;

    @Test
    public void getByUser_Should_ReturnPhoneNumber_When_MatchExists(){
        //Arrange
        PhoneNumber mockNumber=createMockPhoneNumber();
        User mockUser = createMockAdmin();
        when(mockPhoneNumberRepository.getByUser(mockUser))
                .thenReturn(mockNumber);

        //Act
        PhoneNumber result=phoneNumberService.getByUser(mockUser);

        //Assert
        Assertions.assertEquals(mockNumber, result);
        Mockito.verify(mockPhoneNumberRepository, Mockito.times(1))
                .getByUser(mockUser);
    }
    @Test
    public void getByUser_Should_CallRepository_When_NoPhoneNumber(){
        //Arrange
        User mockUser = createMockAdmin();
        Mockito.when(mockPhoneNumberRepository.getByUser(mockUser))
                .thenThrow(EntityNotFoundException.class);

        //Act
        PhoneNumber result = phoneNumberService.getByUser(mockUser);
        //Assert
        Assertions.assertEquals(null, result);
    }
    @Test
    public void create_Should_CreateNewPhoneNumber_When_UserIs_Admin(){
        //Arrange
        PhoneNumber mockNumber=createMockPhoneNumber();
        User mockUser = createMockAdmin();

        //Act
        Mockito.when(mockPhoneNumberRepository.getByUser(mockUser))
                .thenThrow(EntityNotFoundException.class);
        phoneNumberService.create(mockNumber,mockUser);
        //Assert
        Mockito.verify(mockPhoneNumberRepository).create(mockNumber);

    }
    @Test
    public void create_Should_ThrowException_When_PhoneNumber_Exists(){
        //Arrange
        PhoneNumber mockNumber=createMockPhoneNumber();
        User mockUser = createMockAdmin();

        //Act
        Mockito.when(mockPhoneNumberRepository.getByUser(mockUser))
                .thenReturn(mockNumber);
        //Assert
        assertThrows(EntityDuplicateException.class, () -> phoneNumberService.create(mockNumber, mockUser));

    }
    @Test
    public void UpdateTest() {
        //Arrange
        PhoneNumber mockNumber=createMockPhoneNumber();

        //Act
        phoneNumberService.update(mockNumber);

        //Assert
        Mockito.verify(mockPhoneNumberRepository).update(mockNumber);
    }

    @Test
    public void DeleteTest() {
        //Arrange
        User mockUser = createMockAdmin();

        //Act
        phoneNumberService.delete(mockUser);

        //Assert
        Mockito.verify(mockPhoneNumberRepository).delete(mockUser);
    }

}
