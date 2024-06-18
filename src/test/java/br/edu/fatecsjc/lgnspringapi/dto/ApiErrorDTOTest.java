package br.edu.fatecsjc.lgnspringapi.dto;
import java.time.Instant;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ApiErrorDTOTest {

    private ApiErrorDTO apiErrorDTO;

    @BeforeEach
    public void setUp() {
        apiErrorDTO = new ApiErrorDTO();
    }

    @Test
    void testAllArgsConstructorNoArgsConstructorAndBuilder() {
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

        assertEquals(true, apiErrorDTOFields.equals(apiErrorDTOFromBuilder));
        assertEquals(apiErrorDTOFields.hashCode(), apiErrorDTOFromBuilder.hashCode());
        assertNotNull(apiErrorDTOFields.toString());
    }

    @Test
    void testGettersAndSetters() {
        String messageValue = "Test Message getter setter";
        Instant timestampValue = Instant.now();

        apiErrorDTO.setMessage(messageValue);
        apiErrorDTO.setTimestamp(timestampValue);

        assertEquals(messageValue, apiErrorDTO.getMessage());
        assertEquals(timestampValue, apiErrorDTO.getTimestamp());
    }

    @Test
    void testEqualsAndHashCode() {
        String messageValue = "Test Message";
        Instant timestampValue = Instant.now();

        ApiErrorDTO apiErrorDTO1 = new ApiErrorDTO(
                messageValue,
                timestampValue
        );
        ApiErrorDTO apiErrorDTO2 = new ApiErrorDTO(
                messageValue,
                timestampValue
        );

        assertEquals(apiErrorDTO1, apiErrorDTO2);
        assertEquals(apiErrorDTO1.hashCode(), apiErrorDTO2.hashCode());
        assertNotEquals(apiErrorDTO1, new Object());
        ApiErrorDTO apiErrorDTO3 = new ApiErrorDTO(
                "Different Message",
                Instant.now()
        );
        assertNotEquals(apiErrorDTO1, apiErrorDTO3);
        assertNotEquals(apiErrorDTO1.hashCode(), apiErrorDTO3.hashCode());
    }

    @Test
    void testMessage() {
        String messageValue = "Test Message";
        apiErrorDTO.setMessage(messageValue);
        assertEquals(messageValue, apiErrorDTO.getMessage());
    }

    @Test
    void testTimestamp() {
        Instant timestampValue = Instant.now();
        apiErrorDTO.setTimestamp(timestampValue);
        assertEquals(timestampValue, apiErrorDTO.getTimestamp());
    }
}