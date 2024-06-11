package br.edu.fatecsjc.lgnspringapi.dto;
import java.time.Instant;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class ApiErrorDTOTest {

    private ApiErrorDTO apiErrorDTO;

    @BeforeEach
    public void setUp() {
        apiErrorDTO = new ApiErrorDTO();
    }

    @Test
    public void testMessage() {
        String messageValue = "Test Message";
        apiErrorDTO.setMessage(messageValue);
        assertEquals(messageValue, apiErrorDTO.getMessage());
    }

    @Test
    public void testTimestamp() {
        Instant timestampValue = Instant.now();
        apiErrorDTO.setTimestamp(timestampValue);
        assertEquals(timestampValue, apiErrorDTO.getTimestamp());
    }
}