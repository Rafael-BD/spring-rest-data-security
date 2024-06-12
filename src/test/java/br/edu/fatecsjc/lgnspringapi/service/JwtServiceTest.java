package br.edu.fatecsjc.lgnspringapi.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import static org.mockito.Mockito.when;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.core.userdetails.UserDetails;

@ExtendWith(MockitoExtension.class)
@SpringBootTest(properties = {
    "application.security.jwt.secret-key=secretsecretsecretsecretsecretsecretsecretsecret",
    "application.security.jwt.expiration=3600000",
    "application.security.jwt.refresh-token.expiration=86400000"
})
public class JwtServiceTest {

    @Autowired
    private JwtService jwtService;

    @MockBean
    private UserDetails userDetails;

    @Test
    public void testGenerateToken() {
        when(userDetails.getUsername()).thenReturn("username");
        String token = jwtService.generateToken(userDetails);
        String username = jwtService.extractUsername(token);
        assertEquals("username", username);
    }

    @Test
    public void testIsTokenValid() {
        when(userDetails.getUsername()).thenReturn("username");
        String token = jwtService.generateToken(userDetails);
        boolean isValid = jwtService.isTokenValid(token, userDetails);
        assertTrue(isValid);
    }
}