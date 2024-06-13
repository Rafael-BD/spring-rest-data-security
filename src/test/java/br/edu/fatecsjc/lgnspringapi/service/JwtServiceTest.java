package br.edu.fatecsjc.lgnspringapi.service;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import static org.mockito.Mockito.when;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.core.userdetails.UserDetails;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import static io.jsonwebtoken.SignatureAlgorithm.HS256;

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

    @Test
    public void testGenerateRefreshToken() {
        when(userDetails.getUsername()).thenReturn("username");
        String token = jwtService.generateRefreshToken(userDetails);
        String username = jwtService.extractUsername(token);
        assertEquals("username", username);
    }

    @Test
    public void testIsTokenValidWithExpiredToken() {
        when(userDetails.getUsername()).thenReturn("username");

        String token = Jwts.builder()
            .setSubject("username")
            .setExpiration(new Date(System.currentTimeMillis() - 60 * 1000))
            .signWith(HS256, "secretsecretsecretsecretsecretsecretsecretsecret")
            .compact();

        ExpiredJwtException thrown = assertThrows(ExpiredJwtException.class, () -> {
            jwtService.isTokenValid(token, userDetails);
        });

        assertNotNull(thrown);
    }
}