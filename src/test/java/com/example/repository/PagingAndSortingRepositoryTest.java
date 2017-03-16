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
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
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
public class PagingAndSortingRepositoryTest {
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
    public void logFirstPage() {
        PageRequest pageRequest = new PageRequest(0, 3);
        Page<User> page = userRepository.findAll(pageRequest);
        log.info("\n" + WRITER.writeValueAsString(page));
    }

    @Test
    @SneakyThrows
    public void logFirstPageSortedByName() {
        Sort sort = new Sort("username");
        PageRequest pageRequest = new PageRequest(0, 3, sort);
        Page<User> page = userRepository.findAll(pageRequest);
        log.info("\n" + WRITER.writeValueAsString(page));
    }

    @Test
    @SneakyThrows
    public void logFirstPageWithActiveUsersViaQuery() {
        PageRequest pageRequest = new PageRequest(0, 3);
        Page<User> page = userRepository.findAllActive(pageRequest);
        log.info("\n" + WRITER.writeValueAsString(page));
    }

    @Test
    @SneakyThrows
    public void logFirstPageWithActiveUsersViaMethodName() {
        PageRequest pageRequest = new PageRequest(0, 3);
        Page<User> page = userRepository.findAllByIsActiveTrue(pageRequest);
        log.info("\n" + WRITER.writeValueAsString(page));
    }
}
