package com.project.travel_forum.models;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

public class RegisterDto extends LoginDto {
    @NotEmpty(message = "Password confirmation can't be empty")
    private String passwordConfirm;

    @NotEmpty(message = "Name cannot be empty")
    @Size(min = 4, max = 32, message = "Name should be between 4 and 32 symbols")
    private String firstName;

    @NotEmpty(message = "Last name cannot be empty")
    @Size(min = 4, max = 32, message = "Name should be between 4 and 32 symbols")
    private String lastName;

    @NotEmpty(message = "Email can't be empty")
    @Email
    private String email;


    public RegisterDto() {
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
