package com.example.repository;


import com.example.domain.User;
import org.springframework.data.repository.RepositoryDefinition;

import java.util.List;

@RepositoryDefinition(domainClass = User.class, idClass = Long.class)
public interface UserRepository {
    // Read
    User findByUsername(String username);

    List<User> findAll();

    User findOne(Long id);

    // Create + Update
    User save(User user);

    // Delete
    void delete(User user);

    // Additional
    long count();

    void deleteAll();
}