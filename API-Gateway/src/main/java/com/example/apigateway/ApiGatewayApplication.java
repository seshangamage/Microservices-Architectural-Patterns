package com.example.apigateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.server.WebFilter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import reactor.core.publisher.Mono;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.ResponseStatusException;

@SpringBootApplication
public class ApiGatewayApplication {
    private static final Logger logger = LoggerFactory.getLogger(ApiGatewayApplication.class);
    public static void main(String[] args) {
        SpringApplication.run(ApiGatewayApplication.class, args);
    }

    @Bean
    public WebFilter errorLoggingFilter() {
        return (exchange, chain) -> chain.filter(exchange)
            .onErrorResume(e -> {
                logger.error("Gateway error: {}", e.getMessage(), e);
                if (e instanceof ResponseStatusException) {
                    return Mono.error(e);
                }
                return Mono.error(new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Internal error", e));
            });
    }
}
