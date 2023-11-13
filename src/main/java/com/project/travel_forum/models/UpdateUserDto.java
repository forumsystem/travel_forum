package com.project.travel_forum.models;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import org.springframework.lang.Nullable;

public class UpdateUserDto {

    @NotNull
    private String password;
    @NotNull
    private String passwordConfirm;

    @Size(min = 4, max = 32, message = "Name should be between 4 and 32 symbols")
    private String firstName;
    @Size(min = 4, max = 32, message = "Name should be between 4 and 32 symbols")
    private String lastName;

    @Email
    private String email;

    //phone number

    public UpdateUserDto() {
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPasswordConfirm() {
        return passwordConfirm;
    }

    public void setPasswordConfirm(String passwordConfirm) {
        this.passwordConfirm = passwordConfirm;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

}
