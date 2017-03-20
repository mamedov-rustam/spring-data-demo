package com.example.repository;


import com.example.domain.User;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import lombok.SneakyThrows;
import lombok.extern.java.Log;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

import static com.example.repository.TestConstants.SVINKA_PEPA;
import static java.util.Arrays.asList;

@Log
@Transactional
@RunWith(SpringRunner.class)
@SpringBootTest
public class CustomUserRepositoryTest {
    private static final ObjectWriter WRITER = new ObjectMapper().writerWithDefaultPrettyPrinter();

    @PersistenceContext
    private EntityManager em;
    @Autowired
    private UserRepository userRepository;

    @Before
    public void setUp() {
        asList(
                new User("tratata@mail.ru", "123321", "John Pohn"),
                new User("show_main@gmail.com", "6986", SVINKA_PEPA),
                new User("funny_boobs@ya.ru", "sdfsdf", "John Snow"),
                new User("littlepig77@ukr.net", "qweqwe", "Homer Simpson"),
                new User("pinkdrink@mail.ru", "qwerty", "qqushka")
        ).forEach(em::persist);
    }

    @Test
    public void logAllMailboxProviders() {
        userRepository.findAllMailboxProviders().forEach(log::info);
    }

    @Test
    @SneakyThrows
    public void logRandomUser() {
        User user = userRepository.findRandomUser();
        log.info("\n" + WRITER.writeValueAsString(user));
    }
}
