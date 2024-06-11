package br.edu.fatecsjc.lgnspringapi.dto;
import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import br.edu.fatecsjc.lgnspringapi.enums.Role;

public class RegisterRequestDTOTest {

    private RegisterRequestDTO registerRequestDTO;

    @BeforeEach
    public void setUp() {
        registerRequestDTO = new RegisterRequestDTO();
    }

    @Test
    public void testFirstname() {
        String firstnameValue = "Test Firstname";
        registerRequestDTO.setFirstname(firstnameValue);
        assertEquals(firstnameValue, registerRequestDTO.getFirstname());
    }

    @Test
    public void testLastname() {
        String lastnameValue = "Test Lastname";
        registerRequestDTO.setLastname(lastnameValue);
        assertEquals(lastnameValue, registerRequestDTO.getLastname());
    }

    @Test
    public void testEmail() {
        String emailValue = "test@mail.com";
        registerRequestDTO.setEmail(emailValue);
        assertEquals(emailValue, registerRequestDTO.getEmail());
    }

    @Test
    public void testPassword() {
        String passwordValue = "test_password";
        registerRequestDTO.setPassword(passwordValue);
        assertEquals(passwordValue, registerRequestDTO.getPassword());
    }

    @Test
    public void testRole() {
        Role roleValue = Role.USER;
        registerRequestDTO.setRole(roleValue);
        assertEquals(roleValue, registerRequestDTO.getRole());
    }
}