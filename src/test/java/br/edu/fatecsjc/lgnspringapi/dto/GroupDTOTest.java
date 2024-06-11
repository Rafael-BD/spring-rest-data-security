package br.edu.fatecsjc.lgnspringapi.dto;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class GroupDTOTest {

    private GroupDTO groupDTO;

    @BeforeEach
    public void setUp() {
        groupDTO = new GroupDTO();
    }

    @Test
    public void testId() {
        Long idValue = 1L;
        groupDTO.setId(idValue);
        assertEquals(idValue, groupDTO.getId());
    }

    @Test
    public void testName() {
        String nameValue = "Test Name";
        groupDTO.setName(nameValue);
        assertEquals(nameValue, groupDTO.getName());
    }

    @Test
    public void testMembers() {
        MemberDTO memberDTO = new MemberDTO();
        List<MemberDTO> membersValue = Arrays.asList(memberDTO);
        groupDTO.setMembers(membersValue);
        assertEquals(membersValue, groupDTO.getMembers());
    }
}