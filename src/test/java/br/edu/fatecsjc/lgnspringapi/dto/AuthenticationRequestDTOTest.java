package br.edu.fatecsjc.lgnspringapi.dto;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class AuthenticationRequestDTOTest {

    private AuthenticationRequestDTO authenticationRequestDTO;

    @BeforeEach
    public void setUp() {
        authenticationRequestDTO = new AuthenticationRequestDTO();
    }

    @Test
    public void testAllArgsConstructorNoArgsConstructorAndBuilder() {
        String emailValue = "test@mail.com";
        String passwordValue = "TestPassword";

        AuthenticationRequestDTO authenticationRequestDTOFields = new AuthenticationRequestDTO(
                emailValue,
                passwordValue
        );
        AuthenticationRequestDTO authenticationRequestDTOFromBuilder = AuthenticationRequestDTO.builder()
                .email(emailValue)
                .password(passwordValue)
                .build();

        assertEquals(authenticationRequestDTOFields, authenticationRequestDTOFromBuilder);

        AuthenticationRequestDTO authenticationRequestDTONoArgs = new AuthenticationRequestDTO();
        assertNotNull(authenticationRequestDTONoArgs);

        assertEquals(authenticationRequestDTOFields.equals(authenticationRequestDTOFromBuilder), true);
        assertEquals(authenticationRequestDTOFields.hashCode(), authenticationRequestDTOFromBuilder.hashCode());
        assertNotNull(authenticationRequestDTOFields.toString());
    }

    @Test
    public void testEmail() {
        String emailValue = "test@mail.com";
        authenticationRequestDTO.setEmail(emailValue);
        assertEquals(emailValue, authenticationRequestDTO.getEmail());
    }

    @Test
    public void testPassword() {
        String passwordValue = "password";
        authenticationRequestDTO.setPassword(passwordValue);
        assertEquals(passwordValue, authenticationRequestDTO.getPassword());
    }
}