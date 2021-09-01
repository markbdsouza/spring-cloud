package com.markbdsouza.photoappUsers.PhotoAppUsers.service;

import com.markbdsouza.photoappUsers.PhotoAppUsers.shared.UserDTO;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface UserService extends UserDetailsService {

    public UserDTO createUser(UserDTO userDetails);
    public UserDTO getUserDetailsByEmail(String email);
}
