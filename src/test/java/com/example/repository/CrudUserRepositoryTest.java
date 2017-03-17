package com.example.repository;

import com.example.domain.User;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

import java.util.List;

import static com.example.repository.TestConstants.*;
import static java.util.Arrays.asList;

@Transactional
@RunWith(SpringRunner.class)
@SpringBootTest
public class CrudUserRepositoryTest {
    @PersistenceContext
    private EntityManager em;
    @Autowired
    private UserRepository userRepository;

    @Before
    public void setUp() {
        asList(
                new User("tratata@mail.ru", "123321", "John Pohn"),
                new User("show_main@mail.ru", "6986", SVINKA_PEPA),
                new User("funny_boobs@mail.ru", "sdfsdf", "John Snow"),
                new User("littlepig77@mail.ru", "qweqwe", "Gomer Simpson")
        ).forEach(em::persist);
    }

    @Test
    public void shouldDeleteAllUsers() {
        userRepository.deleteAll();
        Assert.assertEquals(0, userRepository.count());
    }

    @Test
    public void shouldReturnAmountOfAllSavedUsers() {
        Assert.assertEquals(4, userRepository.count());
    }

    @Test
    public void shouldFindByUsername() {
        User user = userRepository.findByUsername(SVINKA_PEPA);
        Assert.assertNotNull(user);
        Assert.assertEquals(SVINKA_PEPA, user.getUsername());
    }

    @Test
    public void shouldReturnAllUsers() {
        List<User> users = userRepository.findAll();
        Assert.assertEquals(4, users.size());
    }

    @Test
    public void shouldDeleteSavedUser() {
        User user = userRepository.findByUsername(SVINKA_PEPA);
        Assert.assertNotNull(user);
        userRepository.delete(user);
        user = userRepository.findByUsername(SVINKA_PEPA);

        Assert.assertNull(user);
        long count = userRepository.count();
        Assert.assertEquals(3, count);
    }

    @Test
    public void shouldFindUserById() {
        Long userId = userRepository.findByUsername(SVINKA_PEPA).getId();
        User user = userRepository.findOne(userId);
        Assert.assertNotNull(user);
        Assert.assertNotNull(user.getId());
    }

    @Test
    public void shouldUpdateSavedUser() {
        User user = userRepository.findByUsername(SVINKA_PEPA);
        user.setEmail(NEW_USER_EMAIL);
        userRepository.save(user);

        user = userRepository.findByUsername(SVINKA_PEPA);
        Assert.assertEquals(NEW_USER_EMAIL, user.getEmail());
    }

    @Test
    public void shouldSaveNewUser() {
        User user = new User(NEW_USER_EMAIL, NEW_USER_PASSWORD, NEW_USER_NAME);
        userRepository.save(user);

        long count = userRepository.count();
        Assert.assertEquals(5, count);
    }
}