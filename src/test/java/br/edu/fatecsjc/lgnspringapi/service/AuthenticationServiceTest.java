package br.edu.fatecsjc.lgnspringapi.service;

import java.io.ByteArrayOutputStream;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import static org.mockito.ArgumentMatchers.any;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;

import br.edu.fatecsjc.lgnspringapi.dto.AuthenticationRequestDTO;
import br.edu.fatecsjc.lgnspringapi.dto.AuthenticationResponseDTO;
import br.edu.fatecsjc.lgnspringapi.dto.RegisterRequestDTO;
import br.edu.fatecsjc.lgnspringapi.entity.Token;
import br.edu.fatecsjc.lgnspringapi.entity.User;
import br.edu.fatecsjc.lgnspringapi.repository.TokenRepository;
import br.edu.fatecsjc.lgnspringapi.repository.UserRepository;
import io.jsonwebtoken.io.IOException;
import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.WriteListener;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@ExtendWith(MockitoExtension.class)
class AuthenticationServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private TokenRepository tokenRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private JwtService jwtService;

    @Mock
    private AuthenticationManager authenticationManager;

    @InjectMocks
    private AuthenticationService authenticationService;

    @Test
    void testRegister() {
        RegisterRequestDTO request = new RegisterRequestDTO();
        User user = new User();
        when(passwordEncoder.encode(request.getPassword())).thenReturn("encodedPassword");
        when(userRepository.save(any(User.class))).thenReturn(user);
        when(jwtService.generateToken(any(User.class))).thenReturn("jwtToken");
        when(jwtService.generateRefreshToken(any(User.class))).thenReturn("refreshToken");
        AuthenticationResponseDTO result = authenticationService.register(request);
        assertThat(result.getAccessToken()).isEqualTo("jwtToken");
        assertThat(result.getRefreshToken()).isEqualTo("refreshToken");
    }

    @Test
    void testAuthenticate() {
        AuthenticationRequestDTO request = new AuthenticationRequestDTO();
        User user = new User();
        user.setId(1L);

        Token validToken1 = new Token();
        validToken1.setExpired(false);
        validToken1.setRevoked(false);

        Token validToken2 = new Token();
        validToken2.setExpired(false);
        validToken2.setRevoked(false);

        List<Token> validUserTokens = Arrays.asList(validToken1, validToken2);

        when(userRepository.findByEmail(request.getEmail())).thenReturn(Optional.of(user));
        when(tokenRepository.findAllValidTokenByUser(user.getId())).thenReturn(validUserTokens);
        when(jwtService.generateToken(user)).thenReturn("jwtToken");
        when(jwtService.generateRefreshToken(user)).thenReturn("refreshToken");

        AuthenticationResponseDTO result = authenticationService.authenticate(request);

        assertThat(result.getAccessToken()).isEqualTo("jwtToken");
        assertThat(result.getRefreshToken()).isEqualTo("refreshToken");

        assertTrue(validToken1.isExpired());
        assertTrue(validToken1.isRevoked());
        assertTrue(validToken2.isExpired());
        assertTrue(validToken2.isRevoked());

        verify(tokenRepository).saveAll(validUserTokens);
    }

    @Test
    void testAuthenticateWithNoValidTokens() {
        AuthenticationRequestDTO request = new AuthenticationRequestDTO();
        User user = new User();
        user.setId(1L);

        when(userRepository.findByEmail(request.getEmail())).thenReturn(Optional.of(user));
        when(tokenRepository.findAllValidTokenByUser(user.getId())).thenReturn(Collections.emptyList());
        when(jwtService.generateToken(user)).thenReturn("jwtToken");
        when(jwtService.generateRefreshToken(user)).thenReturn("refreshToken");

        AuthenticationResponseDTO result = authenticationService.authenticate(request);

        assertThat(result.getAccessToken()).isEqualTo("jwtToken");
        assertThat(result.getRefreshToken()).isEqualTo("refreshToken");

        verify(tokenRepository, never()).saveAll(any());
    }

    @Test
    void testRefreshToken() throws IOException, java.io.IOException {
        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpServletResponse response = mock(HttpServletResponse.class);
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        when(request.getHeader(HttpHeaders.AUTHORIZATION)).thenReturn("Bearer refreshToken");
        when(response.getOutputStream()).thenReturn(new ServletOutputStream() {
            @Override
            public boolean isReady() {
                return false;
            }

            @Override
            public void setWriteListener(WriteListener writeListener) {

            }

            @Override
            public void write(int b) throws IOException {
                outputStream.write(b);
            }
        });
        when(jwtService.extractUsername("refreshToken")).thenReturn("userEmail");
        User user = new User();
        when(userRepository.findByEmail("userEmail")).thenReturn(Optional.of(user));
        when(jwtService.isTokenValid("refreshToken", user)).thenReturn(true);
        when(jwtService.generateToken(user)).thenReturn("accessToken");

        authenticationService.refreshToken(request, response);

        String output = outputStream.toString();
        assertTrue(output.contains("accessToken"));
    }

    @Test
    void testRefreshTokenWithInvalidAuthHeader() throws IOException, java.io.IOException {
        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpServletResponse response = mock(HttpServletResponse.class);
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        
        when(request.getHeader(HttpHeaders.AUTHORIZATION)).thenReturn(null);
        authenticationService.refreshToken(request, response);
        assertEquals("", outputStream.toString());
        when(request.getHeader(HttpHeaders.AUTHORIZATION)).thenReturn("Invalid refreshToken");

        authenticationService.refreshToken(request, response);

        assertEquals("", outputStream.toString());
    }

    @Test
    void testRefreshTokenWithNullEmail() throws IOException, java.io.IOException {
        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpServletResponse response = mock(HttpServletResponse.class);
        when(request.getHeader(HttpHeaders.AUTHORIZATION)).thenReturn("Bearer refreshToken");
        when(jwtService.extractUsername("refreshToken")).thenReturn(null);

        authenticationService.refreshToken(request, response);

        verify(response, never()).getOutputStream();
    }

    @Test
    void testRefreshTokenWithInvalidToken() throws IOException, java.io.IOException {
        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpServletResponse response = mock(HttpServletResponse.class);
        when(request.getHeader(HttpHeaders.AUTHORIZATION)).thenReturn("Bearer refreshToken");
        when(jwtService.extractUsername("refreshToken")).thenReturn("userEmail");
        User user = new User();
        when(userRepository.findByEmail("userEmail")).thenReturn(Optional.of(user));
        when(jwtService.isTokenValid("refreshToken", user)).thenReturn(false);

        authenticationService.refreshToken(request, response);

        verify(response, never()).getOutputStream();
    }
}