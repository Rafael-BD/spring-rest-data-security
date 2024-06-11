package br.edu.fatecsjc.lgnspringapi.service;

import java.io.ByteArrayOutputStream;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import static org.mockito.ArgumentMatchers.any;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;

import br.edu.fatecsjc.lgnspringapi.dto.AuthenticationRequestDTO;
import br.edu.fatecsjc.lgnspringapi.dto.AuthenticationResponseDTO;
import br.edu.fatecsjc.lgnspringapi.dto.RegisterRequestDTO;
import br.edu.fatecsjc.lgnspringapi.entity.User;
import br.edu.fatecsjc.lgnspringapi.repository.TokenRepository;
import br.edu.fatecsjc.lgnspringapi.repository.UserRepository;
import io.jsonwebtoken.io.IOException;
import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.WriteListener;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@ExtendWith(MockitoExtension.class)
public class AuthenticationServiceTest {

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
    public void testRegister() {
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
    public void testAuthenticate() {
        AuthenticationRequestDTO request = new AuthenticationRequestDTO();
        User user = new User();
        when(userRepository.findByEmail(request.getEmail())).thenReturn(Optional.of(user));
        when(jwtService.generateToken(user)).thenReturn("jwtToken");
        when(jwtService.generateRefreshToken(user)).thenReturn("refreshToken");
        AuthenticationResponseDTO result = authenticationService.authenticate(request);
        assertThat(result.getAccessToken()).isEqualTo("jwtToken");
        assertThat(result.getRefreshToken()).isEqualTo("refreshToken");
    }

    @Test
    public void testRefreshToken() throws IOException, java.io.IOException {
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
}