package br.edu.fatecsjc.lgnspringapi.repository;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import br.edu.fatecsjc.lgnspringapi.entity.Token;
import br.edu.fatecsjc.lgnspringapi.entity.User;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class TokenRepositoryTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private TokenRepository tokenRepository;

    @Test
    void testFindAllValidTokenByUser() {
        User user = new User();
        entityManager.persist(user);
        entityManager.flush();

        Token token1 = Token.builder().token("token1").user(user).revoked(false).expired(false).build();
        Token token2 = Token.builder().token("token2").user(user).revoked(false).expired(true).build();
        Token token3 = Token.builder().token("token3").user(user).revoked(true).expired(false).build();
        Token token4 = Token.builder().token("token4").user(user).revoked(true).expired(true).build();

        entityManager.persist(token1);
        entityManager.persist(token2);
        entityManager.persist(token3);
        entityManager.persist(token4);
        entityManager.flush();

        List<Token> validTokens = tokenRepository.findAllValidTokenByUser(user.getId());

        assertThat(validTokens).contains(token1, token2, token3).doesNotContain(token4);
    }

    @Test
    void testFindByToken() {
        Token token = Token.builder().token("token1").revoked(false).expired(false).build();
        entityManager.persist(token);
        entityManager.flush();

        Optional<Token> found = tokenRepository.findByToken("token1");

        assertThat(found).isPresent();
        assertThat(found.get().getToken()).isEqualTo("token1");
    }

    @Test
    void testSaveToken() {
        User user = new User();
        entityManager.persist(user);
        entityManager.flush();

        Token token = Token.builder().token("token1").user(user).revoked(false).expired(false).build();

        Token savedToken = tokenRepository.save(token);

        assertThat(savedToken).hasFieldOrPropertyWithValue("token", "token1");
    }
}