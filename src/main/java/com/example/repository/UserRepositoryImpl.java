package com.example.repository;

import com.example.domain.User;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.List;

@Repository
public class UserRepositoryImpl implements UserRepository {
    @PersistenceContext
    private EntityManager em;

    @Override
    public List<User> findAll() {
        return em.createQuery("FROM User", User.class).getResultList();
    }

    @Override
    public User findOne(Long id) {
        return em.find(User.class, id);
    }

    @Override
    public User findByUsername(String username) {
        TypedQuery<User> query = em.createQuery("FROM User as u WHERE u.username = :username", User.class);
        query.setParameter("username", username);
        List<User> result = query.getResultList();

        return result.isEmpty() ? null : result.get(0);
    }

    @Override
    public User save(User user) {
        if(user.getId() == null) {
            this.em.persist(user);
            return user;
        }

        return this.em.merge(user);
    }

    @Override
    public void delete(User user) {
        em.remove(user);
    }

    @Override
    public long count() {
        return em.createQuery("SELECT count(u) FROM User as u", Long.class).getSingleResult();
    }

    @Override
    public void deleteAll() {
        em.createQuery("DELETE FROM User").executeUpdate();
    }
}
