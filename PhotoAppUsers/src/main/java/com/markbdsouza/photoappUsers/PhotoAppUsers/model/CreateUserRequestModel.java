package com.markbdsouza.photoappUsers.PhotoAppUsers.model;

import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
public class CreateUserRequestModel {
    @NotNull(message="First Name should not be null")
    @Size(min = 2, message="Needs to be greater than 2 characters")
    private String firstName;
    @NotNull(message="Last Name should not be null")
    @Size(min = 2, message="Needs to be greater than 2 characters")
    private String lastName;
    @NotNull(message="Password should not be null")
    @Size(min=6, max=16, message = "should be between 6 and 16 characters")
    private String password;
    @NotNull(message="email should be not null")
    @Email
    private String email;
}
