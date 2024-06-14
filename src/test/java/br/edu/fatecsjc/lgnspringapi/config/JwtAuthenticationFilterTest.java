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
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;

import br.edu.fatecsjc.lgnspringapi.entity.Token;
import br.edu.fatecsjc.lgnspringapi.entity.User;
import br.edu.fatecsjc.lgnspringapi.enums.Role;
import br.edu.fatecsjc.lgnspringapi.enums.TokenType;
import br.edu.fatecsjc.lgnspringapi.repository.TokenRepository;
import br.edu.fatecsjc.lgnspringapi.service.JwtService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@ExtendWith(MockitoExtension.class)
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
    public void testDoFilterInternalWithNoAuthHeader() throws Exception { // L43 authHeader == null
        FilterChain filterChain = mock(FilterChain.class);
        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpServletResponse response = mock(HttpServletResponse.class);

        when(request.getServletPath()).thenReturn("/na");

        jwtAuthenticationFilter.doFilterInternal(request, response, filterChain);

        verifyNoInteractions(jwtService);
        verifyNoInteractions(userDetailsService);
        verifyNoInteractions(tokenRepository);
    }

    @Test
    public void testDoFilterInternalWithAuthPath() throws Exception {
        FilterChain filterChain = mock(FilterChain.class);
        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpServletResponse response = mock(HttpServletResponse.class);

        when(request.getServletPath()).thenReturn("/auth");

        jwtAuthenticationFilter.doFilterInternal(request, response, filterChain);

        verifyNoInteractions(jwtService);
        verifyNoInteractions(userDetailsService);
        verifyNoInteractions(tokenRepository);
    }

    @Test
    public void testDoFilterInternalWithValidToken() throws Exception { // L52 true - true
        when(jwtService.extractUsername(anyString())).thenReturn("username");
        when(userDetailsService.loadUserByUsername(anyString())).thenReturn(user);
        when(tokenRepository.findByToken(anyString())).thenReturn(Optional.of(token));
        when(jwtService.isTokenValid(anyString(), any(UserDetails.class))).thenReturn(true);

        FilterChain filterChain = mock(FilterChain.class);
        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpServletResponse response = mock(HttpServletResponse.class);

        when(request.getHeader("Authorization")).thenReturn("Bearer token");
        when(request.getServletPath()).thenReturn("/na");

        jwtAuthenticationFilter.doFilterInternal(request, response, filterChain);

        verify(filterChain, times(1)).doFilter(request, response);
        verify(jwtService, times(1)).extractUsername(anyString());
        verify(userDetailsService, times(1)).loadUserByUsername(anyString());
        verify(tokenRepository, times(1)).findByToken(anyString());
        verify(jwtService, times(1)).isTokenValid(anyString(), any(UserDetails.class));
    }

    @Test
    public void testDoFilterInternalWithInvalidToken() throws Exception { // L52 false - false
        token.setRevoked(true);
        token.setExpired(true);
            
        when(jwtService.extractUsername(anyString())).thenReturn("username");
        when(userDetailsService.loadUserByUsername(anyString())).thenReturn(user);
        when(tokenRepository.findByToken(anyString())).thenReturn(Optional.of(token));
        when(jwtService.isTokenValid(anyString(), any(UserDetails.class))).thenReturn(false);

        FilterChain filterChain = mock(FilterChain.class);
        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpServletResponse response = mock(HttpServletResponse.class);

        when(request.getHeader("Authorization")).thenReturn("Bearer token");
        when(request.getServletPath()).thenReturn("/na");

        jwtAuthenticationFilter.doFilterInternal(request, response, filterChain);

        verify(filterChain, times(1)).doFilter(request, response);
        verify(jwtService, times(1)).extractUsername(anyString());
        verify(userDetailsService, times(1)).loadUserByUsername(anyString());
        verify(tokenRepository, times(1)).findByToken(anyString());
        verify(jwtService, times(1)).isTokenValid(anyString(), any(UserDetails.class));
    }

    @Test
    public void testDoFilterInternalWithExpiredToken() throws Exception { // L52 false - true
        token.setExpired(true);

        FilterChain filterChain = mock(FilterChain.class);
        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpServletResponse response = mock(HttpServletResponse.class);

        when(request.getHeader("Authorization")).thenReturn("Bearer token");
        when(request.getServletPath()).thenReturn("/na");

        jwtAuthenticationFilter.doFilterInternal(request, response, filterChain);

        verify(filterChain, times(1)).doFilter(request, response);
    }

    @Test
    public void testDoFilterInternalWithRevokedToken() throws Exception { // L52 true - false
        token.setRevoked(true);

        when(jwtService.extractUsername(anyString())).thenReturn("username");
        when(userDetailsService.loadUserByUsername(anyString())).thenReturn(user);
        when(tokenRepository.findByToken(anyString())).thenReturn(Optional.of(token));
        when(jwtService.isTokenValid(anyString(), any(UserDetails.class))).thenReturn(true);

        FilterChain filterChain = mock(FilterChain.class);
        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpServletResponse response = mock(HttpServletResponse.class);

        when(request.getHeader("Authorization")).thenReturn("Bearer token");
        when(request.getServletPath()).thenReturn("/na");

        jwtAuthenticationFilter.doFilterInternal(request, response, filterChain);

        verify(filterChain, times(1)).doFilter(request, response);
    }

    @Test
    public void testDoFilterInternalWithAuthHeaderNotStartingWithBearer() throws Exception { // L43 !authHeader.startsWith("Bearer ") == true
        FilterChain filterChain = mock(FilterChain.class);
        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpServletResponse response = mock(HttpServletResponse.class);

        when(request.getHeader("Authorization")).thenReturn("Invalid token");
        when(request.getServletPath()).thenReturn("/na");

        jwtAuthenticationFilter.doFilterInternal(request, response, filterChain);

        verifyNoInteractions(jwtService);
        verifyNoInteractions(userDetailsService);
        verifyNoInteractions(tokenRepository);
    }

    @Test
    public void testDoFilterInternalWithNonNullUserEmailAndAuthentication() throws Exception { //L49 false - false
        //SecurityContextHolder.getContext().setAuthentication(mock(Authentication.class));

        FilterChain filterChain = mock(FilterChain.class);
        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpServletResponse response = mock(HttpServletResponse.class);

        when(request.getHeader("Authorization")).thenReturn("Bearer token");
        when(request.getServletPath()).thenReturn("/na");

        jwtAuthenticationFilter.doFilterInternal(request, response, filterChain);

        verify(filterChain, times(1)).doFilter(request, response);
    }

    @Test
    public void testDoFilterInternalWithNonNullUserEmail() throws Exception { // L49 false - true
        SecurityContextHolder.getContext().setAuthentication(mock(Authentication.class));

        FilterChain filterChain = mock(FilterChain.class);
        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpServletResponse response = mock(HttpServletResponse.class);

        when(request.getHeader("Authorization")).thenReturn("Bearer token");
        when(request.getServletPath()).thenReturn("/na");

        jwtAuthenticationFilter.doFilterInternal(request, response, filterChain);

        verify(filterChain, times(1)).doFilter(request, response);
    }

    @Test
    public void testDoFilterInternalWithNonNullAuthentication() throws Exception { // L49 true - false
        //SecurityContextHolder.getContext().setAuthentication(mock(Authentication.class));

        FilterChain filterChain = mock(FilterChain.class);
        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpServletResponse response = mock(HttpServletResponse.class);

        when(jwtService.extractUsername(anyString())).thenReturn("username");
        when(request.getHeader("Authorization")).thenReturn("Bearer token");
        when(request.getServletPath()).thenReturn("/na");

        jwtAuthenticationFilter.doFilterInternal(request, response, filterChain);

        verify(filterChain, times(1)).doFilter(request, response);
    }
}