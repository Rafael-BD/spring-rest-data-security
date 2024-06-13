package br.edu.fatecsjc.lgnspringapi.config;

import java.util.ArrayList;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.when;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;

import br.edu.fatecsjc.lgnspringapi.entity.Token;
import br.edu.fatecsjc.lgnspringapi.entity.User;
import br.edu.fatecsjc.lgnspringapi.repository.TokenRepository;
import br.edu.fatecsjc.lgnspringapi.service.JwtService;


import br.edu.fatecsjc.lgnspringapi.enums.Role;
import br.edu.fatecsjc.lgnspringapi.enums.TokenType;

import jakarta.servlet.FilterChain;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@ExtendWith(MockitoExtension.class)
@SpringBootTest
public class JwtAuthenticationFilterTest {

    @Mock
    private JwtService jwtService;

    @Mock
    private UserDetailsService userDetailsService;

    @Mock
    private TokenRepository tokenRepository;

    @InjectMocks
    private JwtAuthenticationFilter jwtAuthenticationFilter;

    User user;
    Token token;

    @BeforeEach
    public void setup() {
        user = User.builder()
            .id(1L)
            .firstName("First")
            .lastName("Last")
            .email("username")
            .password("password")
            .role(Role.USER) 
            .tokens(new ArrayList<>())
            .build();

        token = Token.builder()
            .id(1L)
            .token("token")
            .tokenType(TokenType.BEARER) 
            .revoked(false)
            .expired(false)
            .user(user)
            .build();
    }

    @Test
    public void testDoFilterInternalWithNoAuthHeader() throws Exception {
        FilterChain filterChain = mock(FilterChain.class);
        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpServletResponse response = mock(HttpServletResponse.class);

        when(request.getServletPath()).thenReturn("/auth/authenticate");

        jwtAuthenticationFilter.doFilterInternal(request, response, filterChain);

        verifyNoInteractions(jwtService);
        verifyNoInteractions(userDetailsService);
        verifyNoInteractions(tokenRepository);
    }

    @Test
    public void testDoFilterInternalWithInvalidAuthHeader() throws Exception {
        FilterChain filterChain = mock(FilterChain.class);
        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpServletResponse response = mock(HttpServletResponse.class);

        when(request.getServletPath()).thenReturn("/auth/authenticate");

        jwtAuthenticationFilter.doFilterInternal(request, response, filterChain);

        verifyNoInteractions(jwtService);
        verifyNoInteractions(userDetailsService);
        verifyNoInteractions(tokenRepository);
    }

    // @Test
    // public void testDoFilterInternalWithValidToken() throws Exception {
    //     when(jwtService.extractUsername(anyString())).thenReturn("username");
    //     when(userDetailsService.loadUserByUsername(anyString())).thenReturn(user);
    //     when(tokenRepository.findByToken(anyString())).thenReturn(Optional.of(token));
    //     when(jwtService.isTokenValid(anyString(), any(UserDetails.class))).thenReturn(true);

    //     FilterChain filterChain = mock(FilterChain.class);
    //     HttpServletRequest request = mock(HttpServletRequest.class);
    //     HttpServletResponse response = mock(HttpServletResponse.class);

    //     when(request.getHeader("Authorization")).thenReturn("Bearer token");
    //     when(request.getServletPath()).thenReturn("/notAuth");

    //     jwtAuthenticationFilter.doFilterInternal(request, response, filterChain);

    //     verify(jwtService, times(1)).extractUsername(anyString());
    //     verify(userDetailsService, times(1)).loadUserByUsername(anyString());
    //     verify(tokenRepository, times(1)).findByToken(anyString());
    //     verify(jwtService, times(1)).isTokenValid(anyString(), any(UserDetails.class));
    // }

    // @Test
    // public void testDoFilterInternalWithInvalidToken() throws Exception {
    //     when(jwtService.extractUsername(anyString())).thenReturn("username");
    //     when(userDetailsService.loadUserByUsername(anyString())).thenReturn(user);
    //     when(tokenRepository.findByToken(anyString())).thenReturn(Optional.of(token));
    //     when(jwtService.isTokenValid(anyString(), any(UserDetails.class))).thenReturn(false);

    //     FilterChain filterChain = mock(FilterChain.class);
    //     HttpServletRequest request = mock(HttpServletRequest.class);
    //     HttpServletResponse response = mock(HttpServletResponse.class);

    //     when(request.getHeader("Authorization")).thenReturn("Bearer token");
    //     when(request.getServletPath()).thenReturn("/notAuth");

    //     jwtAuthenticationFilter.doFilterInternal(request, response, filterChain);

    //     verify(jwtService, times(1)).extractUsername(anyString());
    //     verify(userDetailsService, times(1)).loadUserByUsername(anyString());
    //     verify(tokenRepository, times(1)).findByToken(anyString());
    //     verify(jwtService, times(1)).isTokenValid(anyString(), any(UserDetails.class));
    // }
}