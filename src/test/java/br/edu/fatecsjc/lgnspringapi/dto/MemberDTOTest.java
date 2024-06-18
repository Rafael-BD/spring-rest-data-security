package br.edu.fatecsjc.lgnspringapi.dto;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class MemberDTOTest {

    private MemberDTO memberDTO;

    @BeforeEach
    public void setUp() {
        memberDTO = new MemberDTO();
    }

    @Test
    void testAllArgsConstructorNoArgsConstructorAndBuilder() {
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

        assertEquals(true, memberDTOFields.equals(memberDTOFromBuilder));
        assertEquals(memberDTOFields.hashCode(), memberDTOFromBuilder.hashCode());
        assertNotNull(memberDTOFields.toString());
    }

    @Test
    void testGettersAndSetters() {
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
    void testEqualsAndHashCode() {
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

        MemberDTO memberDTO1 = new MemberDTO(
                idValue,
                nameValue,
                ageValue,
                marathonIdsValue,
                groupIdValue
        );
        MemberDTO memberDTO2 = new MemberDTO(
                idValue,
                nameValue,
                ageValue,
                marathonIdsValue,
                groupIdValue
        );

        assertEquals(true, memberDTO1.equals(memberDTO2));
        assertEquals(true, memberDTO2.equals(memberDTO1));
        assertEquals(memberDTO1.hashCode(), memberDTO2.hashCode());
        assertEquals(true, memberDTO1.equals(memberDTO1));
    }

    @Test
    void testId() {
        Long idValue = 1L;
        memberDTO.setId(idValue);
        assertEquals(idValue, memberDTO.getId());
    }

    @Test
    void testName() {
        String nameValue = "Test Name";
        memberDTO.setName(nameValue);
        assertEquals(nameValue, memberDTO.getName());
    }

    @Test
    void testAge() {
        Integer ageValue = 25;
        memberDTO.setAge(ageValue);
        assertEquals(ageValue, memberDTO.getAge());
    }

    @Test
    void testMarathonIds() {
        List<Long> marathonIdsValue = Arrays.asList(1L, 2L, 3L);
        memberDTO.setMarathonIds(marathonIdsValue);
        assertEquals(marathonIdsValue, memberDTO.getMarathonIds());
    }

    @Test
    void testGroupId() {
        Long groupIdValue = 1L;
        memberDTO.setGroupId(groupIdValue);
        assertEquals(groupIdValue, memberDTO.getGroupId());
    }
}