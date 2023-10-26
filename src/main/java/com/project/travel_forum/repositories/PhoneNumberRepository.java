package com.project.travel_forum.repositories;

import com.project.travel_forum.models.PhoneNumber;
import com.project.travel_forum.models.User;

public interface PhoneNumberRepository {
    PhoneNumber getByUser(User user);

    void create(PhoneNumber phoneNumber);

    void update(PhoneNumber phoneNumber);

    void delete(User user);
}
