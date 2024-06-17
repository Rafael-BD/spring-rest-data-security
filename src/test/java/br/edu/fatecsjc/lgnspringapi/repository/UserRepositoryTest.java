package br.edu.fatecsjc.lgnspringapi.repository;

import java.util.Optional;

import org.apache.commons.lang3.RandomStringUtils;
import static org.assertj.core.api.Assertions.assertThat;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import br.edu.fatecsjc.lgnspringapi.entity.User;
import br.edu.fatecsjc.lgnspringapi.enums.Role;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class UserRepositoryTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private UserRepository userRepository;

    @Test
    void testFindByEmail() {
        String rendomName = RandomStringUtils.randomAlphabetic(5);
        User user = User.builder().email(rendomName + "@mail.com").password("password").role(Role.USER).build();
        entityManager.persist(user);
        entityManager.flush();

        Optional<User> found = userRepository.findByEmail(rendomName + "@mail.com");

        assertThat(found).isPresent();
        assertThat(found.get().getEmail()).isEqualTo(rendomName + "@mail.com");
    }

    @Test
    void testSaveUser() {
        User user = User.builder().email("test@mail.com").password("password").role(Role.USER).build();

        User savedUser = userRepository.save(user);

        assertThat(savedUser).hasFieldOrPropertyWithValue("email", "test@mail.com");
    }
}