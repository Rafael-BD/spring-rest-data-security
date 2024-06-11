package br.edu.fatecsjc.lgnspringapi.dto;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class MarathonDTOTest {

    private MarathonDTO marathonDTO;

    @BeforeEach
    public void setUp() {
        marathonDTO = new MarathonDTO();
    }

    @Test
    public void testId() {
        Long idValue = 1L;
        marathonDTO.setId(idValue);
        assertEquals(idValue, marathonDTO.getId());
    }

    @Test
    public void testWeight() {
        int weightValue = 75;
        marathonDTO.setWeight(weightValue);
        assertEquals(weightValue, marathonDTO.getWeight());
    }

    @Test
    public void testScore() {
        int scoreValue = 100;
        marathonDTO.setScore(scoreValue);
        assertEquals(scoreValue, marathonDTO.getScore());
    }

    @Test
    public void testMemberIds() {
        List<Long> memberIdsValue = Arrays.asList(1L, 2L, 3L);
        marathonDTO.setMemberIds(memberIdsValue);
        assertEquals(memberIdsValue, marathonDTO.getMemberIds());
    }
}