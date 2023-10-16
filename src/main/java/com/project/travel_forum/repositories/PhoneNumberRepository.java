package com.project.travel_forum.repositories;

import com.project.travel_forum.models.PhoneNumber;
import com.project.travel_forum.models.User;

public interface PhoneNumberRepository {
    PhoneNumber get(User user);

    void create(PhoneNumber phoneNumber);

    void update(PhoneNumber phoneNumber);

    void delete(PhoneNumber phoneNumber);
}
