package com.markbdsouza.photoapp.PhotoappApiGateway;

import io.jsonwebtoken.Jwts;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import javax.security.auth.Subject;

@Component
public class AuthHeaderFilter extends AbstractGatewayFilterFactory<AuthHeaderFilter.Config> {

    @Autowired
    private Environment env;

    public AuthHeaderFilter(){
        super(Config.class);
    }

    public static class Config {
        // can add config properties here
    }

    @Override
    public GatewayFilter apply(Config config) {
        return (exchange, chain) -> {
            ServerHttpRequest request = exchange.getRequest();
            if (!request.getHeaders().containsKey("Authorization")) {
                return onError(exchange, "No Authorization Header", HttpStatus.UNAUTHORIZED);
            }
            String authHeaderValue = request.getHeaders().get("Authorization").get(0);
            String jwtToken = authHeaderValue.replace("Bearer", "");
            if(!isJwtValid(jwtToken)){
                return onError(exchange, "JWT Token invalid", HttpStatus.UNAUTHORIZED);
            }
            return chain.filter(exchange);
        };
    }

    private Mono<Void> onError(ServerWebExchange exchange, String err, HttpStatus httpStatus) {
        ServerHttpResponse response = exchange.getResponse();
        response.setStatusCode(httpStatus);
        return response.setComplete();
    }

    //return true if token is valid
    private boolean isJwtValid(String jwt) {
        boolean returnValue = true;
        String subject = null;
        try {
            subject = Jwts.parser()
                    .setSigningKey(env.getProperty("token.secret"))
                    .parseClaimsJws(jwt)
                    .getBody()
                    .getSubject();
        } catch(Exception ex){
            returnValue=false;
        }
        //subject will actually be our userId which is UUID randomly generated string
        if (subject == null || subject.isEmpty()) {
            returnValue = false;
        }

        return returnValue;
    }

}
