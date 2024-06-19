package br.edu.fatecsjc.lgnspringapi.service;

import java.security.Key;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import static org.mockito.Mockito.when;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.core.userdetails.UserDetails;

import io.jsonwebtoken.Jwts;
import static io.jsonwebtoken.SignatureAlgorithm.HS256;
import io.jsonwebtoken.security.Keys;


@ExtendWith(MockitoExtension.class)
@SpringBootTest(properties = {
    "application.security.jwt.secret-key=secretsecretsecretsecretsecretsecretsecretsecret",
    "application.security.jwt.expiration=3600000",
    "application.security.jwt.refresh-token.expiration=86400000"
})
class JwtServiceTest {

    @Autowired
    private JwtService jwtService;

    @MockBean
    private UserDetails userDetails;
    private static final Key KEY = Keys.secretKeyFor(HS256);

    @Test
    void testGenerateToken() {
        when(userDetails.getUsername()).thenReturn("username");
        String token = jwtService.generateToken(userDetails);
        String username = jwtService.extractUsername(token);
        assertEquals("username", username);
    }

    @Test
    void testIsTokenValid() {
        when(userDetails.getUsername()).thenReturn("username");
        String token = jwtService.generateToken(userDetails);
        boolean isValid = jwtService.isTokenValid(token, userDetails);
        assertTrue(isValid);
    }

    @Test
    void testGenerateRefreshToken() {
        when(userDetails.getUsername()).thenReturn("username");
        String token = jwtService.generateRefreshToken(userDetails);
        String username = jwtService.extractUsername(token);
        assertEquals("username", username);
    }

    @Test
    void testIsTokenValidWithExpiredToken() {
        when(userDetails.getUsername()).thenReturn("username");

        String token = Jwts.builder()
            .setSubject("username")
            .setExpiration(new Date(System.currentTimeMillis() - 60 * 1000))
            .signWith(KEY)
            .compact();

        boolean isValid = jwtService.isTokenValid(token, userDetails);
        assertFalse(isValid);
    }

    @Test
    void testIsTokenValidWithWrongUsername() {
        when(userDetails.getUsername()).thenReturn("correctUsername");

        String wrongUsernameToken = Jwts.builder()
            .setSubject("wrongUsername")
            .setIssuedAt(new Date(System.currentTimeMillis()))
            .setExpiration(new Date(System.currentTimeMillis() + 3600000))
            .signWith(KEY)
            .compact();

        boolean isValid = jwtService.isTokenValid(wrongUsernameToken, userDetails);
        assertFalse(isValid);
    }

    @Test
    void testIsTokenValidWithWrongUsernameAndExpiredToken() {
        when(userDetails.getUsername()).thenReturn("correctUsername");

        String wrongUsernameAndExpiredToken = Jwts.builder()
            .setSubject("wrongUsername")
            .setExpiration(new Date(System.currentTimeMillis() - 60 * 1000))
            .signWith(KEY)
            .compact();

        boolean isValid = jwtService.isTokenValid(wrongUsernameAndExpiredToken, userDetails);
        assertFalse(isValid);
    }

}