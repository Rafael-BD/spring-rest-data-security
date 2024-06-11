package br.edu.fatecsjc.lgnspringapi.dto;
import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class AuthenticationRequestDTOTest {

    private AuthenticationRequestDTO authenticationRequestDTO;

    @BeforeEach
    public void setUp() {
        authenticationRequestDTO = new AuthenticationRequestDTO();
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