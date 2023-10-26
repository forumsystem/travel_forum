package com.project.travel_forum.services;

import com.project.travel_forum.exceptions.EntityDuplicateException;
import com.project.travel_forum.exceptions.EntityNotFoundException;
import com.project.travel_forum.models.PhoneNumber;
import com.project.travel_forum.models.User;
import com.project.travel_forum.repositories.PhoneNumberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import static com.project.travel_forum.helpers.CheckPermissions.checkIfAdmin;

@Service
public class PhoneNumberServiceImpl implements PhoneNumberService {

    private final PhoneNumberRepository phoneNumberRepository;

    @Autowired
    public PhoneNumberServiceImpl(PhoneNumberRepository phoneNumberRepository) {
        this.phoneNumberRepository = phoneNumberRepository;
    }


    @Override
    public PhoneNumber getByUser(User user) {
        boolean phoneExists = true;
        PhoneNumber phoneNumber = null;
        try {
            phoneNumber = phoneNumberRepository.getByUser(user);
        } catch (EntityNotFoundException e) {
            phoneExists = false;
        }
        return phoneNumber;
    }

    @Override
    public void create(PhoneNumber phoneNumber, User user) {
        checkIfAdmin(user);

        boolean adminHasPhone = true;

        try {
            phoneNumberRepository.getByUser(user);
        } catch (EntityNotFoundException e) {
            adminHasPhone = false;
            phoneNumber.setUser(user);
            phoneNumberRepository.create(phoneNumber);
        }
        if (adminHasPhone) {
            throw new EntityDuplicateException("This admin already has phone number.");
        }
    }

        @Override
        public void update (PhoneNumber phoneNumber){
            phoneNumberRepository.update(phoneNumber);
        }

        @Override
        public void delete(User user){
            phoneNumberRepository.delete(user);
        }

    }
