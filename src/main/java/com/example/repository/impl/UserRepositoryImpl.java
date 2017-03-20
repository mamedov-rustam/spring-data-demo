package com.example.repository.impl;


import com.example.domain.User;
import com.example.repository.CustomUserRepository;
import com.example.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

import static java.util.stream.Collectors.toList;

public class UserRepositoryImpl implements CustomUserRepository {
    @PersistenceContext
    private EntityManager em;
    @Autowired
    private UserRepository userRepository;

    @Override
    public List<String> findAllMailboxProviders() {
        return userRepository.findAll().stream()
                .map(User::getEmail)
                .map(mail -> mail.split("@")[1])
                .distinct()
                .collect(toList());
    }

    @Override
    @SuppressWarnings("unchecked")
    public User findRandomUser() {
        List<Long> ids = (List<Long>) em.createQuery("select u.id from User as u").getResultList();
        int randomIndex = (int) (System.currentTimeMillis() % ids.size());
        long id = ids.get(randomIndex);

        return em.find(User.class, id);
    }
}
