package br.edu.fatecsjc.lgnspringapi.service;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import org.mockito.Mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.core.Authentication;

import br.edu.fatecsjc.lgnspringapi.entity.Token;
import br.edu.fatecsjc.lgnspringapi.repository.TokenRepository;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@ExtendWith(MockitoExtension.class)
@SpringBootTest
class LogoutServiceTest {

    @Mock
    private HttpServletRequest request;

    @Mock
    private HttpServletResponse response;

    @Mock
    private Authentication authentication;

    @MockBean
    private TokenRepository tokenRepository;

    @Autowired
    private LogoutService logoutService;

    @Test
    void testLogoutWithValidToken() {
        String token = "Bearer validToken";
        Token storedToken = new Token();
        storedToken.setToken("validToken");
        storedToken.setExpired(false);
        storedToken.setRevoked(false);

        when(request.getHeader("Authorization")).thenReturn(token);
        when(tokenRepository.findByToken("validToken")).thenReturn(Optional.of(storedToken));

        logoutService.logout(request, response, authentication);

        assertTrue(storedToken.isExpired());
        assertTrue(storedToken.isRevoked());
    }

    @Test
    void testLogoutWithInvalidToken() {
        String token = "Bearer invalidToken";

        when(request.getHeader("Authorization")).thenReturn(token);
        when(tokenRepository.findByToken("invalidToken")).thenReturn(Optional.empty());

        logoutService.logout(request, response, authentication);

        verify(tokenRepository, never()).save(any(Token.class));
    }

    @Test
    void testLogoutWithNullAuthHeader() {
        when(request.getHeader("Authorization")).thenReturn(null);

        logoutService.logout(request, response, authentication);

        verify(tokenRepository, never()).findByToken(anyString());
    }

    @Test
    void testLogoutWithInvalidAuthHeader() {
        String token = "Invalid auth header";
        when(request.getHeader("Authorization")).thenReturn(token);

        logoutService.logout(request, response, authentication);

        verify(tokenRepository, never()).findByToken(anyString());
    }
}