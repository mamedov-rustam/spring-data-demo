package com.example.dto;


import org.springframework.beans.factory.annotation.Value;

public interface UserCredentials {
    @Value("#{target.email.toUpperCase()}")
    String getEmail();
    String getPassword();
}
