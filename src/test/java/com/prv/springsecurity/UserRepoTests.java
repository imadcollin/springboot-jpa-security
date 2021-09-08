package com.prv.springsecurity;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.annotation.Rollback;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Rollback(false)
public class UserRepoTests {

    @Autowired
    private UserRepository repository;

    @Autowired
    private TestEntityManager entityManager;

    @Test
    public void testCreateUser() {
        User user = new User();
        user.setFirstName("Smith");
        user.setLastName("Lasan");
        user.setPassword("mypass");

        User savedUser = repository.save(user);
        User existUser = entityManager.find(User.class, savedUser.getId());

        assertThat(existUser.getFirstName()).isEqualTo(savedUser.getFirstName());
    }

    @Test
    public void testFindUserByEmail() {
        String email = "test@gmail.com";
        User user = repository.findByEmail(email);
        assertThat(user).isNotNull();
    }

    @Test
    public void testFindUserByEmailNotExist() {
        String email = "dsfsdfsdf@gmail.com";
        User user = repository.findByEmail(email);
        assertThat(user).isNull();
    }

    @Test
    public void testFindUserByEmailIsExist() {
        String email = "test@gmail.com";
        String firstName = "test1";

        User user = repository.findByEmail(email);
        assertThat(user.getEmail()).isNotNull();
    }
}
