package com.project.travel_forum.models;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public class PhoneNumberDto {

    @NotNull(message = "Phone number cannot be empty.")
    @Size(min = 4, max = 32, message = "Phone Number should be between 4 and 32 symbols")
    String phoneNumber;

    public PhoneNumberDto() {
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
}
