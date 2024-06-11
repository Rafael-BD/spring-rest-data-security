package br.edu.fatecsjc.lgnspringapi.dto;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class MemberDTOTest {

    private MemberDTO memberDTO;

    @BeforeEach
    public void setUp() {
        memberDTO = new MemberDTO();
    }

    @Test
    public void testId() {
        Long idValue = 1L;
        memberDTO.setId(idValue);
        assertEquals(idValue, memberDTO.getId());
    }

    @Test
    public void testName() {
        String nameValue = "Test Name";
        memberDTO.setName(nameValue);
        assertEquals(nameValue, memberDTO.getName());
    }

    @Test
    public void testAge() {
        Integer ageValue = 25;
        memberDTO.setAge(ageValue);
        assertEquals(ageValue, memberDTO.getAge());
    }

    @Test
    public void testMarathonIds() {
        List<Long> marathonIdsValue = Arrays.asList(1L, 2L, 3L);
        memberDTO.setMarathonIds(marathonIdsValue);
        assertEquals(marathonIdsValue, memberDTO.getMarathonIds());
    }

    @Test
    public void testGroupId() {
        Long groupIdValue = 1L;
        memberDTO.setGroupId(groupIdValue);
        assertEquals(groupIdValue, memberDTO.getGroupId());
    }
}