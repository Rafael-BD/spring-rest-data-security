package br.edu.fatecsjc.lgnspringapi.dto;
import java.time.Instant;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class ApiErrorDTOTest {

    private ApiErrorDTO apiErrorDTO;

    @BeforeEach
    public void setUp() {
        apiErrorDTO = new ApiErrorDTO();
    }

    @Test
    public void testAllArgsConstructorNoArgsConstructorAndBuilder() {
        String messageValue = "Test Message";
        Instant timestampValue = Instant.now();

        ApiErrorDTO apiErrorDTOFields = new ApiErrorDTO(
                messageValue,
                timestampValue
        );
        ApiErrorDTO apiErrorDTOFromBuilder = ApiErrorDTO.builder()
                .message(messageValue)
                .timestamp(timestampValue)
                .build();

        assertEquals(apiErrorDTOFields, apiErrorDTOFromBuilder);

        ApiErrorDTO apiErrorDTONoArgs = new ApiErrorDTO();
        assertNotNull(apiErrorDTONoArgs);

        assertEquals(apiErrorDTOFields.equals(apiErrorDTOFromBuilder), true);
        assertEquals(apiErrorDTOFields.hashCode(), apiErrorDTOFromBuilder.hashCode());
        assertNotNull(apiErrorDTOFields.toString());
    }

    @Test
    public void testGettersAndSetters() {
        String messageValue = "Test Message getter setter";
        Instant timestampValue = Instant.now();

        apiErrorDTO.setMessage(messageValue);
        apiErrorDTO.setTimestamp(timestampValue);

        assertEquals(messageValue, apiErrorDTO.getMessage());
        assertEquals(timestampValue, apiErrorDTO.getTimestamp());
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