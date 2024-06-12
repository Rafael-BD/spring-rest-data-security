package br.edu.fatecsjc.lgnspringapi.dto;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class MemberDTOTest {

    private MemberDTO memberDTO;

    @BeforeEach
    public void setUp() {
        memberDTO = new MemberDTO();
    }

    @Test
    public void testAllArgsConstructorNoArgsConstructorAndBuilder() {
        Long idValue = 1L;
        String nameValue = "Test Name";
        Integer ageValue = 30;
        List<Long> marathonIdsValue = Arrays.asList(1L, 2L, 3L);
        Long groupIdValue = 1L;

        MemberDTO memberDTOFields = new MemberDTO(
                idValue,
                nameValue,
                ageValue,
                marathonIdsValue,
                groupIdValue
        );
        MemberDTO memberDTOFromBuilder = MemberDTO.builder()
                .id(idValue)
                .name(nameValue)
                .age(ageValue)
                .marathonIds(marathonIdsValue)
                .groupId(groupIdValue)
                .build();

        assertEquals(memberDTOFields, memberDTOFromBuilder);

        MemberDTO memberDTONoArgs = new MemberDTO();
        assertNotNull(memberDTONoArgs);

        assertEquals(memberDTOFields.equals(memberDTOFromBuilder), true);
        assertEquals(memberDTOFields.hashCode(), memberDTOFromBuilder.hashCode());
        assertNotNull(memberDTOFields.toString());
    }

    @Test
    public void testGettersAndSetters() {
        Long idValue = 1L;
        String nameValue = "Test Name";
        Integer ageValue = 30;
        List<Long> marathonIdsValue = Arrays.asList(1L, 2L, 3L);
        Long groupIdValue = 1L;

        memberDTO.setId(idValue);
        memberDTO.setName(nameValue);
        memberDTO.setAge(ageValue);
        memberDTO.setMarathonIds(marathonIdsValue);
        memberDTO.setGroupId(groupIdValue);

        assertEquals(idValue, memberDTO.getId());
        assertEquals(nameValue, memberDTO.getName());
        assertEquals(ageValue, memberDTO.getAge());
        assertEquals(marathonIdsValue, memberDTO.getMarathonIds());
        assertEquals(groupIdValue, memberDTO.getGroupId());
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