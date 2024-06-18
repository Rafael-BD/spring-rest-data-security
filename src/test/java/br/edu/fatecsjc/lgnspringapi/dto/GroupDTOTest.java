package br.edu.fatecsjc.lgnspringapi.dto;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class GroupDTOTest {

    private GroupDTO groupDTO;

    @BeforeEach
    public void setUp() {
        groupDTO = new GroupDTO();
    }

    @Test
    void testAllArgsConstructorNoArgsConstructorAndBuilder() {
        Long idValue = 1L;
        String nameValue = "Test Name";
        MemberDTO memberDTO = new MemberDTO();
        List<MemberDTO> membersValue = Arrays.asList(memberDTO);

        GroupDTO groupDTOFields = new GroupDTO(
                idValue,
                nameValue,
                membersValue
        );
        GroupDTO groupDTOFromBuilder = GroupDTO.builder()
                .id(idValue)
                .name(nameValue)
                .members(membersValue)
                .build();

        assertEquals(groupDTOFields, groupDTOFromBuilder);

        GroupDTO groupDTONoArgs = new GroupDTO();
        assertNotNull(groupDTONoArgs);

        assertEquals(true, groupDTOFields.equals(groupDTOFromBuilder));
        assertEquals(groupDTOFields.hashCode(), groupDTOFromBuilder.hashCode());
        assertNotNull(groupDTOFields.toString());
    }

    @Test
    void testGettersAndSetters() {
        Long idValue = 1L;
        String nameValue = "Test Name";
        MemberDTO memberDTO = new MemberDTO();
        List<MemberDTO> membersValue = Arrays.asList(memberDTO);

        groupDTO.setId(idValue);
        groupDTO.setName(nameValue);
        groupDTO.setMembers(membersValue);

        assertEquals(idValue, groupDTO.getId());
        assertEquals(nameValue, groupDTO.getName());
        assertEquals(membersValue, groupDTO.getMembers());
    }

    @Test
    void testEqualsAndHashCode() {
        Long idValue = 1L;
        String nameValue = "Test Name";
        MemberDTO memberDTO = new MemberDTO();
        List<MemberDTO> membersValue = Arrays.asList(memberDTO);

        GroupDTO groupDTOFields = new GroupDTO(
                idValue,
                nameValue,
                membersValue
        );
        GroupDTO groupDTOFromBuilder = GroupDTO.builder()
                .id(idValue)
                .name(nameValue)
                .members(membersValue)
                .build();

        assertEquals(groupDTOFields, groupDTOFromBuilder);
        assertEquals(groupDTOFields.hashCode(), groupDTOFromBuilder.hashCode());
    }

    @Test
    void testId() {
        Long idValue = 1L;
        groupDTO.setId(idValue);
        assertEquals(idValue, groupDTO.getId());
    }

    @Test
    void testName() {
        String nameValue = "Test Name";
        groupDTO.setName(nameValue);
        assertEquals(nameValue, groupDTO.getName());
    }

    @Test
    void testMembers() {
        MemberDTO memberDTO = new MemberDTO();
        List<MemberDTO> membersValue = Arrays.asList(memberDTO);
        groupDTO.setMembers(membersValue);
        assertEquals(membersValue, groupDTO.getMembers());
    }
}