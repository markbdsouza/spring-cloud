package com.markbdsouza.photoappUsers.PhotoAppUsers.controller;

import com.markbdsouza.photoappUsers.PhotoAppUsers.model.CreateUserRequestModel;
import com.markbdsouza.photoappUsers.PhotoAppUsers.model.CreateUserResponseModel;
import com.markbdsouza.photoappUsers.PhotoAppUsers.service.UserService;
import com.markbdsouza.photoappUsers.PhotoAppUsers.shared.UserDTO;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/users")
public class Users {
    @Autowired
    private Environment env;
    @Autowired
    UserService userService;

    @GetMapping("/status/check")
    public String getUser() {
        return "working on " + env.getProperty("local.server.port");
    }

    @PostMapping(consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE},
            produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    public ResponseEntity<CreateUserResponseModel> createUser(@RequestBody @Valid CreateUserRequestModel userDetails) {
        ModelMapper modelMapper = new ModelMapper();
        UserDTO userDTO = modelMapper.map(userDetails, UserDTO.class);
        UserDTO createdUser = userService.createUser(userDTO);
        CreateUserResponseModel createdUserDetails = modelMapper.map(createdUser, CreateUserResponseModel.class);
        return new ResponseEntity<>(createdUserDetails, HttpStatus.CREATED);
    }
}
