package com.project.travel_forum.models;

import java.util.Objects;

public class PhoneNumber {

    private int id;
    private String phoneNumber;
    private User user;

    public PhoneNumber() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public User getUser() {
        return user;
    }

    public void setAdmin(User user) {
        this.user= user;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        PhoneNumber phoneNumber = (PhoneNumber) obj;
        return id == phoneNumber.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
