package com.example.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class RestTemplateConfig {
    // restFul风格spring自带调用模板
    @Bean
    public RestTemplate restTemplate(){
        return new RestTemplate();
    }
}
