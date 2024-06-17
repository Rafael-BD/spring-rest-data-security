package br.edu.fatecsjc.lgnspringapi.dto;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class AuthenticationRequestDTOTest {

    private AuthenticationRequestDTO authenticationRequestDTO;

    @BeforeEach
    public void setUp() {
        authenticationRequestDTO = new AuthenticationRequestDTO();
    }

    @Test
    void testAllArgsConstructorNoArgsConstructorAndBuilder() {
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
    void testGettersAndSetters() {
        String emailValue = "testeGetterSetter@mail.com";
        String passwordValue = "TestPasswordGetterSetter";

        authenticationRequestDTO.setEmail(emailValue);
        authenticationRequestDTO.setPassword(passwordValue);

        assertEquals(emailValue, authenticationRequestDTO.getEmail());
        assertEquals(passwordValue, authenticationRequestDTO.getPassword());
    }

    @Test
    void testEqualsAndHashCode() {
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

        AuthenticationRequestDTO authenticationRequestDTO1 = new AuthenticationRequestDTO(
                emailValue,
                passwordValue
        );

        AuthenticationRequestDTO authenticationRequestDTO2 = new AuthenticationRequestDTO(
                emailValue,
                passwordValue
        );

        assertEquals(authenticationRequestDTO1, authenticationRequestDTO2);
        assertEquals(authenticationRequestDTO2, authenticationRequestDTO1);
        assertEquals(authenticationRequestDTO1.hashCode(), authenticationRequestDTO2.hashCode());
        assertEquals(authenticationRequestDTO1.equals(authenticationRequestDTO1), true);
    }

    @Test
    void testEmail() {
        String emailValue = "test@mail.com";
        authenticationRequestDTO.setEmail(emailValue);
        assertEquals(emailValue, authenticationRequestDTO.getEmail());
    }

    @Test
    void testPassword() {
        String passwordValue = "password";
        authenticationRequestDTO.setPassword(passwordValue);
        assertEquals(passwordValue, authenticationRequestDTO.getPassword());
    }
}