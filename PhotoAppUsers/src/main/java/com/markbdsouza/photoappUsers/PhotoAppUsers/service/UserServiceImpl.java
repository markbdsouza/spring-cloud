package com.markbdsouza.photoappUsers.PhotoAppUsers.service;

import com.markbdsouza.photoappUsers.PhotoAppUsers.data.AlbumServiceClient;
import com.markbdsouza.photoappUsers.PhotoAppUsers.data.UserEntity;
import com.markbdsouza.photoappUsers.PhotoAppUsers.data.UserRepository;
import com.markbdsouza.photoappUsers.PhotoAppUsers.model.AlbumResponseModel;
import com.markbdsouza.photoappUsers.PhotoAppUsers.shared.UserDTO;
import feign.FeignException;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.circuitbreaker.CircuitBreaker;
import org.springframework.cloud.client.circuitbreaker.CircuitBreakerFactory;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class UserServiceImpl implements UserService {
    private String ALBUMS_URL = "http://ALBUMS-WS/users/%s/albums";

    final Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

    @Autowired
    UserRepository userRepository;

    @Autowired
    BCryptPasswordEncoder bCryptPasswordEncoder;

//    @Autowired
//    RestTemplate restTemplate;

    @Autowired
    AlbumServiceClient albumServiceClient;

    @Autowired
    private CircuitBreakerFactory circuitBreakerFactory;
    @Override
    public UserDTO createUser(UserDTO userDetails) {
        userDetails.setUserId(UUID.randomUUID().toString());
        ModelMapper modelMapper = new ModelMapper();
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
        UserEntity userEntity = modelMapper.map(userDetails, UserEntity.class);
        userEntity.setEncryptedPassword(bCryptPasswordEncoder.encode(userDetails.getPassword()));
        UserEntity savedUser = userRepository.save(userEntity);
        UserDTO savedUserDetails = modelMapper.map(savedUser, UserDTO.class);
        return savedUserDetails;
    }

    @Override
    public UserDTO getUserDetailsByEmail(String email) {
        UserEntity userEntity = userRepository.findByEmail(email);
        if (userEntity == null) throw new UsernameNotFoundException("User name not found" + email);
        return new ModelMapper().map(userEntity, UserDTO.class);
    }

    @Override
    public UserDTO getUserbyId(String userId) {

        CircuitBreaker circuitBreaker = circuitBreakerFactory.create("circuitbreaker");

        UserEntity userEntity = userRepository.findByUserId(userId);
        if (userEntity == null) throw new UsernameNotFoundException("User name not found");
        String albumsUrl = String.format(ALBUMS_URL, userId);
//        ResponseEntity<List<AlbumResponseModel>> albumBody = restTemplate.exchange(albumsUrl, HttpMethod.GET, null,
//                new ParameterizedTypeReference<>() {
//                });
//        List<AlbumResponseModel> albums = albumBody.getBody();
        List<AlbumResponseModel> albums = null;
        logger.info("Before calling albums MS");
        albums =  circuitBreaker.run(() -> albumServiceClient.getAlbums(userId),
                throwable -> getDefaultAlbumList());
        UserDTO userDTO = new ModelMapper().map(userEntity, UserDTO.class);
        userDTO.setAlbums(albums);
        return userDTO;
    }

    private List<AlbumResponseModel> getDefaultAlbumList(){
        return new ArrayList<>();
    }

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        UserEntity userEntity = userRepository.findByEmail(s);
        if (userEntity == null) throw new UsernameNotFoundException("User name not found" + s);
        return new User(userEntity.getEmail(), userEntity.getEncryptedPassword(),
                true, true, true, true,
                new ArrayList<>());
    }
}
