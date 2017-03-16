package com.example.repository;

import com.example.domain.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;

public interface UserRepository extends PagingAndSortingRepository<User, Long> {

    User findByUsername(String username);

    List<User> findAll();

    @Query("from User u where u.isActive = true")
    Page<User> findAllActive(Pageable pageable);

    Page<User> findAllByIsActiveTrue(Pageable pageable);
}