package com.markbdsouza.photoappUsers.PhotoAppUsers.shared;

import com.markbdsouza.photoappUsers.PhotoAppUsers.model.AlbumResponseModel;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class UserDTO implements Serializable {
    private static final long serialVersionUID = 926030759964540321L;
    private String userId;
    private String firstName;
    private String lastName;
    private String password;
    private String email;
    private String encryptedPassword;
    private List<AlbumResponseModel> albums;

}
