package br.edu.fatecsjc.lgnspringapi.converter;
import java.util.Arrays;
import static java.util.Arrays.asList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import org.mockito.MockitoAnnotations;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeMap;
import org.modelmapper.TypeToken;

import br.edu.fatecsjc.lgnspringapi.dto.MemberDTO;
import br.edu.fatecsjc.lgnspringapi.entity.Group;
import br.edu.fatecsjc.lgnspringapi.entity.Marathon;
import br.edu.fatecsjc.lgnspringapi.entity.Member;
import br.edu.fatecsjc.lgnspringapi.repository.GroupRepository;
import br.edu.fatecsjc.lgnspringapi.repository.MarathonRepository;


public class MemberConverterTest {

    @Mock
    private ModelMapper modelMapper;

    @Mock
    private MarathonRepository marathonRepository;

    @Mock
    private GroupRepository groupRepository;

    @InjectMocks
    private MemberConverter memberConverter;

    private MemberDTO memberDTO;
    private Member member;
    private Marathon marathon;
    private Group group;
    MemberDTO memberDtoWithoutGroupId;
    Member memberWithoutGroup;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);

        memberDTO = new MemberDTO();
        memberDTO.setId(1L);
        memberDTO.setName("Member Name");
        memberDTO.setAge(22);
        memberDTO.setGroupId(1L);

        member = new Member();
        member.setId(1L);
        member.setName("Member Name");
        member.setAge(22);

        marathon = new Marathon();
        marathon.setId(1L);
        marathon.setMembers(Arrays.asList(member));

        memberDTO.setMarathonIds(asList(marathon.getId()));

        group = new Group();
        group.setId(1L);

        memberDtoWithoutGroupId = new MemberDTO();
        memberDtoWithoutGroupId.setId(1L);
        memberDtoWithoutGroupId.setName("Member Name");
        memberDtoWithoutGroupId.setAge(22);
        memberDtoWithoutGroupId.setMarathonIds(Arrays.asList(1L));

        memberWithoutGroup = new Member();
        memberWithoutGroup.setId(1L);
        memberWithoutGroup.setName("Member Name");
        memberWithoutGroup.setAge(22);
        

        member.setMarathons(Arrays.asList(marathon));
        member.setGroup(group);

        TypeMap<MemberDTO, Member> typeMapMock = mock(TypeMap.class);
        when(modelMapper.createTypeMap(MemberDTO.class, Member.class)).thenReturn(typeMapMock);
    }

    @Test
    public void testConvertToEntity() {
        when(modelMapper.map(memberDTO, Member.class)).thenReturn(member);
        when(marathonRepository.findById(1L)).thenReturn(Optional.of(marathon));
        when(groupRepository.findById(1L)).thenReturn(Optional.of(group));

        Member result = memberConverter.convertToEntity(memberDTO);

        assertEquals(member, result);
        assertNotNull(result.getMarathons());
        assertTrue(result.getMarathons().contains(marathon));
        assertEquals(group, result.getGroup());
    }

    @Test
    public void testConvertToEntityWithExistingEntity() {
        when(modelMapper.map(memberDTO, Member.class)).thenReturn(member);
        when(marathonRepository.findById(1L)).thenReturn(Optional.of(marathon));
        when(groupRepository.findById(1L)).thenReturn(Optional.of(group));

        Member result = memberConverter.convertToEntity(memberDTO, member);

        assertEquals(member, result);
        assertNotNull(result.getMarathons());
        assertTrue(result.getMarathons().contains(marathon));
        assertEquals(group, result.getGroup());
    }

    @Test
    public void testConvertToDto() {
        when(modelMapper.map(member, MemberDTO.class)).thenReturn(memberDTO);

        MemberDTO result = memberConverter.convertToDto(member);

        assertEquals(memberDTO, result);
    }

    @Test
    public void testConvertToEntityWithNullGroupId() {
        when(modelMapper.map(memberDtoWithoutGroupId, Member.class)).thenReturn(memberWithoutGroup);
        when(marathonRepository.findById(1L)).thenReturn(Optional.of(marathon));

        Member result = memberConverter.convertToEntity(memberDtoWithoutGroupId);

        assertEquals(memberWithoutGroup, result);
        assertNull(result.getGroup());
    }

    @Test
    public void testConvertToEntityWithExistingEntityAndNullGroupId() {
        when(modelMapper.map(memberDtoWithoutGroupId, Member.class)).thenReturn(memberWithoutGroup);
        when(marathonRepository.findById(1L)).thenReturn(Optional.of(marathon));

        Member result = memberConverter.convertToEntity(memberDtoWithoutGroupId, memberWithoutGroup);

        assertEquals(memberWithoutGroup, result);
        assertNull(result.getGroup());
    }

    @Test
    public void testConvertToDtoWithNullGroup() {
        memberWithoutGroup.setMarathons(Arrays.asList(marathon));

        when(modelMapper.map(memberWithoutGroup, MemberDTO.class)).thenReturn(memberDtoWithoutGroupId);

        MemberDTO result = memberConverter.convertToDto(memberWithoutGroup);

        assertEquals(memberDtoWithoutGroupId, result);
        assertNull(result.getGroupId());
    }

    @Test
    public void testConvertToEntityList() {
        List<MemberDTO> dtos = Arrays.asList(memberDTO);
        List<Member> members = Arrays.asList(member);
        when(modelMapper.map(dtos, new TypeToken<List<Member>>(){}.getType())).thenReturn(members);
        when(marathonRepository.findById(1L)).thenReturn(Optional.of(marathon));
        when(groupRepository.findById(1L)).thenReturn(Optional.of(group));

        List<Member> result = memberConverter.convertToEntity(dtos);

        assertEquals(members, result);
        assertTrue(result.stream().allMatch(m -> m.getMarathons().contains(marathon)));
        assertTrue(result.stream().allMatch(m -> m.getGroup().equals(group)));
    }

    @Test
    public void testConvertToDtoList() {
        List<Member> members = Arrays.asList(member);
        List<MemberDTO> dtos = Arrays.asList(memberDTO);
        when(modelMapper.map(members, new TypeToken<List<MemberDTO>>(){}.getType())).thenReturn(dtos);

        List<MemberDTO> result = memberConverter.convertToDto(members);

        assertEquals(dtos, result);
    }

    @Test
    public void testConvertToEntityListWithNullMembers() {
        Marathon marathonWithNullMembers = new Marathon();
        marathonWithNullMembers.setId(1L);
        marathonWithNullMembers.setMembers(null);

        MemberDTO memberDtoWithMarathon = new MemberDTO();
        memberDtoWithMarathon.setId(1L);
        memberDtoWithMarathon.setName("Member Name");
        memberDtoWithMarathon.setAge(22);
        memberDtoWithMarathon.setMarathonIds(Arrays.asList(1L));
        memberDtoWithMarathon.setGroupId(1L);

        List<MemberDTO> dtos = Arrays.asList(memberDtoWithMarathon);

        when(modelMapper.map(dtos, new TypeToken<List<Member>>(){}.getType())).thenReturn(Arrays.asList(member));
        when(marathonRepository.findById(1L)).thenReturn(Optional.of(marathonWithNullMembers));
        when(groupRepository.findById(1L)).thenReturn(Optional.of(group));

        List<Member> result = memberConverter.convertToEntity(dtos);

        assertEquals(Arrays.asList(member), result);
        assertTrue(result.stream().allMatch(m -> m.getMarathons().stream().anyMatch(mt-> mt.getId().equals(marathonWithNullMembers.getId()))));
        assertTrue(result.stream().allMatch(m -> m.getGroup().equals(group)));
    }

    @Test
    public void testConvertToDtoListWithNullMarathonIds() {
        Member memberWithNullMarathons = new Member();
        memberWithNullMarathons.setId(1L);
        memberWithNullMarathons.setName("Member Name");
        memberWithNullMarathons.setAge(22);
        memberWithNullMarathons.setMarathons(null);

        List<Member> members = Arrays.asList(memberWithNullMarathons);

        MemberDTO expectedDto = new MemberDTO();
        expectedDto.setId(1L);
        expectedDto.setName("Member Name");
        expectedDto.setAge(22);
        expectedDto.setMarathonIds(null);

        List<MemberDTO> expectedDtos = Arrays.asList(expectedDto);

        when(modelMapper.map(members, new TypeToken<List<MemberDTO>>(){}.getType())).thenReturn(expectedDtos);

        List<MemberDTO> result = memberConverter.convertToDto(members);

        assertEquals(expectedDtos, result);
        assertTrue(result.stream().allMatch(dto -> dto.getMarathonIds() == null));
    }
}