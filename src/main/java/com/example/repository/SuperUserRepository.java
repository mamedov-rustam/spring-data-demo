package com.example.repository;

import com.example.domain.User;
import org.springframework.data.repository.NoRepositoryBean;

@NoRepositoryBean
public interface SuperUserRepository {
    User findByMagicCriteria(String criteria);
}
