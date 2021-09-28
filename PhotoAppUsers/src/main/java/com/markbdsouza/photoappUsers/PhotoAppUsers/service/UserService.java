package com.markbdsouza.photoappUsers.PhotoAppUsers.service;

import com.markbdsouza.photoappUsers.PhotoAppUsers.shared.UserDTO;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface UserService extends UserDetailsService {

    UserDTO createUser(UserDTO userDetails);

    UserDTO getUserDetailsByEmail(String email);

    UserDTO getUserbyId(String userId);
}
