package com.markbdsouza.photoapp.PhotoappApiGateway;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.http.HttpHeaders;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.Set;

@Component
public class MyPreFilter implements GlobalFilter {
    final Logger log = LoggerFactory.getLogger(MyPreFilter.class);

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        log.info("My first pre filter");
        ServerHttpRequest req = exchange.getRequest();
        String path = req.getPath().toString();
        log.info("Path to access : " + path);
        HttpHeaders headers = req.getHeaders();
        Set<String> headerNames = headers.keySet();
        headerNames.forEach(name -> {
            log.info(name + " : " + headers.getFirst(name));
        });
        return chain.filter(exchange);
    }
}
