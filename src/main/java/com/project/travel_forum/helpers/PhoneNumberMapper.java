package com.project.travel_forum.helpers;

import com.project.travel_forum.models.PhoneNumber;
import com.project.travel_forum.models.PhoneNumberDto;
import com.project.travel_forum.models.User;
import com.project.travel_forum.services.PhoneNumberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class PhoneNumberMapper {
    private final PhoneNumberService phoneNumberService;

    @Autowired
    public PhoneNumberMapper(PhoneNumberService phoneNumberService) {
        this.phoneNumberService = phoneNumberService;
    }

    public PhoneNumber fromDto(PhoneNumberDto dto, User user) {
        PhoneNumber existingPhoneNumber = phoneNumberService.getByUser(user);

        existingPhoneNumber.setPhoneNumber(dto.getPhoneNumber());

        return existingPhoneNumber;
    }


    public PhoneNumber fromDto(PhoneNumberDto dto) {
        PhoneNumber phoneNumber = new PhoneNumber();

        phoneNumber.setPhoneNumber(dto.getPhoneNumber());

        return phoneNumber;
    }

}


