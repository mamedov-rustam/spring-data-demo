package com.example.repository;


import com.example.domain.User;

import java.util.List;

public interface CustomUserRepository {
    List<String> findAllMailboxProviders();

    User findRandomUser();
}
