package com.markbdsouza.photoappUsers.PhotoAppUsers.model;

import lombok.Data;

@Data
public class LoginRequestModel {
    private String email;
    private String password;
}
