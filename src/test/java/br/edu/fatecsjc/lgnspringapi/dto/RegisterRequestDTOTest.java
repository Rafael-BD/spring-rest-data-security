package br.edu.fatecsjc.lgnspringapi.dto;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
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
    public void testAllArgsConstructorNoArgsConstructorAndBuilder() {
        String firstnameValue = "Test Firstname";
        String lastnameValue = "Test Lastname";
        String emailValue = "test@mail.com";
        String passwordValue = "Test Password";
        Role roleValue = Role.USER;

        RegisterRequestDTO registerRequestDTOfields = new RegisterRequestDTO(
                firstnameValue,
                lastnameValue,
                emailValue,
                passwordValue,
                roleValue
        );
        RegisterRequestDTO registerRequestDTOFromBuilder = RegisterRequestDTO.builder()
                .firstname(firstnameValue)
                .lastname(lastnameValue)
                .email(emailValue)
                .password(passwordValue)
                .role(roleValue)
                .build();

        assertEquals(registerRequestDTOfields, registerRequestDTOFromBuilder);

        RegisterRequestDTO registerRequestDTONoArgs = new RegisterRequestDTO();
        assertNotNull(registerRequestDTONoArgs);

        assertEquals(registerRequestDTOfields.equals(registerRequestDTOFromBuilder), true);
        assertEquals(registerRequestDTOfields.hashCode(), registerRequestDTOFromBuilder.hashCode());
        assertNotNull(registerRequestDTOfields.toString());
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