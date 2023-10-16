package com.project.travel_forum.services;

import com.project.travel_forum.models.PhoneNumber;
import com.project.travel_forum.models.User;

public interface PhoneNumberService {
    PhoneNumber get(User user);

    void create(PhoneNumber phoneNumber);

    void update(PhoneNumber phoneNumber);

    void delete(PhoneNumber phoneNumber);
}
