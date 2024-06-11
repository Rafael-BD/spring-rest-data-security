package br.edu.fatecsjc.lgnspringapi.dto;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class AuthenticationResponseDTOTest {

    private AuthenticationResponseDTO authenticationResponseDTO;

    @BeforeEach
    public void setUp() {
        authenticationResponseDTO = new AuthenticationResponseDTO();
    }

    @Test
    public void testAccessToken() {
        String accessTokenValue = "access_token";
        authenticationResponseDTO.setAccessToken(accessTokenValue);
        assertEquals(accessTokenValue, authenticationResponseDTO.getAccessToken());
    }

    @Test
    public void testRefreshToken() {
        String refreshTokenValue = "refresh_token";
        authenticationResponseDTO.setRefreshToken(refreshTokenValue);
        assertEquals(refreshTokenValue, authenticationResponseDTO.getRefreshToken());
    }
}