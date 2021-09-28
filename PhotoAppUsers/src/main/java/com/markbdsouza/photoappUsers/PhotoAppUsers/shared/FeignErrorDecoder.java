package com.markbdsouza.photoappUsers.PhotoAppUsers.shared;

import com.markbdsouza.photoappUsers.PhotoAppUsers.service.UserServiceImpl;
import feign.Response;
import feign.codec.ErrorDecoder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;


public class FeignErrorDecoder implements ErrorDecoder {

    final Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);
    @Override
    public Exception decode(String methodKey, Response response) {
        logger.info(methodKey);
        switch (response.status()) {
            case 404: {
                if(methodKey.contains("getAlbums")){
                    return new ResponseStatusException(HttpStatus.valueOf(response.status()), "User albums not found");
                }
                break;

            }
            default:
                return new Exception(response.reason());
        }
        return null;
    }
}
