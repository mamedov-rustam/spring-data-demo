package com.example.repository;

import com.example.domain.User;
import com.example.dto.UserCredentials;
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

import java.util.List;

import static com.example.repository.TestConstants.SVINKA_PEPA;
import static java.util.Arrays.asList;

@Log
@Transactional
@RunWith(SpringRunner.class)
@SpringBootTest
public class ProjectionUserRepositoryTest {
    private static final ObjectWriter WRITER = new ObjectMapper().writerWithDefaultPrettyPrinter();

    @PersistenceContext
    private EntityManager em;
    @Autowired
    private UserRepository userRepository;

    @Before
    public void setUp() {
        asList(
                new User("tratata@mail.ru", "DxIyCzK", "John Pohn"),
                new User("big_pig@mail.ru", "marrypopins123", SVINKA_PEPA),
                new User("funny_boobs@mail.ru", "qwerty", "John Snow", false),
                new User("littlepig77@mail.ru", "password1!", "Homer Simpson")
        ).forEach(em::persist);
    }

    @Test
    @SneakyThrows
    public void logUserCredentials() {
        UserCredentials credentials = userRepository.findCredentialsByUsername(SVINKA_PEPA);
        log.info("\n" + WRITER.writeValueAsString(credentials));
    }

    @Test
    @SneakyThrows
    public void logUserCredentialsList() {
        List<UserCredentials> credentialsList = userRepository.findAllCredentials();
        log.info("\n" + WRITER.writeValueAsString(credentialsList));
    }
}
