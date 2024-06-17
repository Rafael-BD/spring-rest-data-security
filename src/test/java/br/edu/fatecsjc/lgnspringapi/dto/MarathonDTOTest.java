package br.edu.fatecsjc.lgnspringapi.dto;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class MarathonDTOTest {

    private MarathonDTO marathonDTO;

    @BeforeEach
    public void setUp() {
        marathonDTO = new MarathonDTO();
    }

    @Test
    void testAllArgsConstructorNoArgsConstructorAndBuilder() {
        Long idValue = 1L;
        int weightValue = 70;
        int scoreValue = 90;
        List<Long> memberIdsValue = Arrays.asList(1L, 2L, 3L);

        MarathonDTO marathonDTOFields = new MarathonDTO(
                idValue,
                weightValue,
                scoreValue,
                memberIdsValue
        );
        MarathonDTO marathonDTOFromBuilder = MarathonDTO.builder()
                .id(idValue)
                .weight(weightValue)
                .score(scoreValue)
                .memberIds(memberIdsValue)
                .build();

        assertEquals(marathonDTOFields, marathonDTOFromBuilder);

        MarathonDTO marathonDTONoArgs = new MarathonDTO();
        assertNotNull(marathonDTONoArgs);

        assertEquals(marathonDTOFields.equals(marathonDTOFromBuilder), true);
        assertEquals(marathonDTOFields.hashCode(), marathonDTOFromBuilder.hashCode());
        assertNotNull(marathonDTOFields.toString());
    }

    @Test
    void testGettersAndSetters() {
        Long idValue = 1L;
        int weightValue = 70;
        int scoreValue = 90;
        List<Long> memberIdsValue = Arrays.asList(1L, 2L, 3L);

        marathonDTO.setId(idValue);
        marathonDTO.setWeight(weightValue);
        marathonDTO.setScore(scoreValue);
        marathonDTO.setMemberIds(memberIdsValue);

        assertEquals(idValue, marathonDTO.getId());
        assertEquals(weightValue, marathonDTO.getWeight());
        assertEquals(scoreValue, marathonDTO.getScore());
        assertEquals(memberIdsValue, marathonDTO.getMemberIds());
    }

    @Test
    void testEqualsAndHashCode() {
        Long idValue = 1L;
        int weightValue = 70;
        int scoreValue = 90;
        List<Long> memberIdsValue = Arrays.asList(1L, 2L, 3L);

        MarathonDTO marathonDTOFields = new MarathonDTO(
                idValue,
                weightValue,
                scoreValue,
                memberIdsValue
        );
        MarathonDTO marathonDTOFromBuilder = MarathonDTO.builder()
                .id(idValue)
                .weight(weightValue)
                .score(scoreValue)
                .memberIds(memberIdsValue)
                .build();

        assertEquals(marathonDTOFields, marathonDTOFromBuilder);
    }

    @Test
    void testId() {
        Long idValue = 1L;
        marathonDTO.setId(idValue);
        assertEquals(idValue, marathonDTO.getId());
    }

    @Test
    void testWeight() {
        int weightValue = 75;
        marathonDTO.setWeight(weightValue);
        assertEquals(weightValue, marathonDTO.getWeight());
    }

    @Test
    void testScore() {
        int scoreValue = 100;
        marathonDTO.setScore(scoreValue);
        assertEquals(scoreValue, marathonDTO.getScore());
    }

    @Test
    void testMemberIds() {
        List<Long> memberIdsValue = Arrays.asList(1L, 2L, 3L);
        marathonDTO.setMemberIds(memberIdsValue);
        assertEquals(memberIdsValue, marathonDTO.getMemberIds());
    }
}