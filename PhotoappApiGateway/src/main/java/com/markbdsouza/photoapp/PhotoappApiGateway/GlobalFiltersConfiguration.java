package com.markbdsouza.photoapp.PhotoappApiGateway;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import reactor.core.publisher.Mono;

@Configuration
public class GlobalFiltersConfiguration {
    final Logger logger = LoggerFactory.getLogger(GlobalFiltersConfiguration.class);

    @Order(1)
    @Bean
    public GlobalFilter myPreFilterTwo(){
        return ((exchange, chain) -> {
            logger.info("pre filter 2");
            return chain.filter(exchange).then(
                    Mono.fromRunnable(()->{
                        logger.info("post filter 2");
                    })
            );
        });
    }
    @Bean
    public GlobalFilter myPreFilterThree(){
        return ((exchange, chain) -> {
            logger.info("pre filter 3");
            return chain.filter(exchange).then(
                    Mono.fromRunnable(()->{
                        logger.info("post filter 3");
                    })
            );
        });
    }
}
