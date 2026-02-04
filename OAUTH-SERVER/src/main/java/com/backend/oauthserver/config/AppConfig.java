package com.backend.oauthserver.config;

import org.springframework.cloud.client.loadbalancer.reactive.ReactorLoadBalancerExchangeFilterFunction;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class AppConfig {


    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    WebClient webClient(WebClient.Builder builder, ReactorLoadBalancerExchangeFilterFunction lbFunction) {
        return builder.baseUrl("http://USER-SERVICE").filter(lbFunction).build();
    }

}
