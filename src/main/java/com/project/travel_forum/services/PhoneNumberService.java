package com.project.travel_forum.services;

import com.project.travel_forum.models.PhoneNumber;
import com.project.travel_forum.models.User;

public interface PhoneNumberService {
    PhoneNumber getByUser(User user);

    void create(PhoneNumber phoneNumber, User user);

    void update(PhoneNumber phoneNumber);

    void delete(User user);
}
