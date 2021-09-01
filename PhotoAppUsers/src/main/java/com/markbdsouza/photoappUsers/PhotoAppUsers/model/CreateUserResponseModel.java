package com.markbdsouza.photoappUsers.PhotoAppUsers.model;

import lombok.Data;

@Data
public class CreateUserResponseModel {
    private String userId;
    private String firstName;
    private String lastName;
    private String email;
}
