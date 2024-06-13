package br.edu.fatecsjc.lgnspringapi.converter;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.reset;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.mockito.MockitoAnnotations;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeMap;
import org.modelmapper.TypeToken;

import br.edu.fatecsjc.lgnspringapi.dto.GroupDTO;
import br.edu.fatecsjc.lgnspringapi.dto.MemberDTO;
import br.edu.fatecsjc.lgnspringapi.entity.Group;
import br.edu.fatecsjc.lgnspringapi.entity.Member;

public class GroupConverterTest {

    @Mock
    private ModelMapper modelMapper;

    @InjectMocks
    private GroupConverter groupConverter;

    private GroupDTO groupDTO;
    private Group group;
    private Member member;
    private MemberDTO memberDTO;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);

        groupDTO = new GroupDTO();
        memberDTO = new MemberDTO();

        memberDTO.setId(1L);
        memberDTO.setName("Member Name New");
        memberDTO.setAge(22);
        memberDTO.setGroupId(2L);
        memberDTO.setMarathonIds(Arrays.asList(1L, 2L));

        groupDTO.setId(1L);
        groupDTO.setName("Group Name New");
        groupDTO.setMembers(Arrays.asList(memberDTO));

        group = new Group();
        member = new Member();

        group.setId(1L);
        group.setName("Group Name");
        group.setMembers(Arrays.asList(member));

        member.setId(1L);
        member.setName("Member Name");
        member.setAge(20);
        member.setGroup(group);
        member.setMarathons(Arrays.asList());

        TypeMap<GroupDTO, Group> typeMapMock = mock(TypeMap.class);
        when(modelMapper.createTypeMap(GroupDTO.class, Group.class)).thenReturn(typeMapMock);
    }

    @Test
    public void testConvertToEntity() {
        when(modelMapper.map(groupDTO, Group.class)).thenReturn(group);

        Group result = groupConverter.convertToEntity(groupDTO);

        assertEquals(group, result);
    }

    @Test
    public void testConvertToEntityWithExistingEntity() {
        when(modelMapper.map(groupDTO, Group.class)).thenReturn(group);

        Group result = groupConverter.convertToEntity(groupDTO, group);

        assertEquals(group, result);
        assertNotNull(result.getMembers());
        assertTrue(result.getMembers().stream().allMatch(m -> m.getGroup() == result));
    }

    @Test
    public void testConvertToEntityWithNullMembers() {
        Group existingEntity = new Group();
        existingEntity.setId(1L);
        existingEntity.setName("Existing Group");

        GroupDTO dto = new GroupDTO();
        dto.setId(1L);
        dto.setName("Group Name");
        dto.setMembers(null);

        when(modelMapper.map(dto, Group.class)).thenReturn(existingEntity);

        Group result = groupConverter.convertToEntity(dto, existingEntity);

        assertEquals(existingEntity, result);
        assertTrue(result.getMembers().isEmpty());
    }

    @Test
    public void testConvertToDto() {
        when(modelMapper.map(group, GroupDTO.class)).thenReturn(groupDTO);

        GroupDTO result = groupConverter.convertToDto(group);

        assertEquals(groupDTO, result);
    }

    @Test
    public void testConvertToEntityList() {
        List<GroupDTO> dtos = Arrays.asList(groupDTO);
        List<Group> groups = Arrays.asList(group);
        when(modelMapper.map(dtos, new TypeToken<List<Group>>(){}.getType())).thenReturn(groups);

        List<Group> result = groupConverter.convertToEntity(dtos);

        assertEquals(groups, result);
        assertTrue(result.stream().allMatch(g -> g.getMembers().stream().allMatch(m -> m.getGroup() == group)));
    }

    @Test
    public void testConvertToDtoList() {
        List<Group> groups = Arrays.asList(group);
        List<GroupDTO> dtos = Arrays.asList(groupDTO);
        when(modelMapper.map(groups, new TypeToken<List<GroupDTO>>(){}.getType())).thenReturn(dtos);

        List<GroupDTO> result = groupConverter.convertToDto(groups);

        assertEquals(dtos, result);
    }

    @Test
    public void testConvertToEntityWithExistingPropertyMapperDto() {
        TypeMap<GroupDTO, Group> typeMapMock = mock(TypeMap.class);
        when(modelMapper.createTypeMap(GroupDTO.class, Group.class)).thenReturn(typeMapMock);
        groupConverter.convertToEntity(groupDTO);

        reset(modelMapper);
        groupConverter.convertToEntity(groupDTO);
        verify(modelMapper, times(0)).createTypeMap(GroupDTO.class, Group.class);
    }

    @Test
    public void testConvertToEntityWithExistingEntityAndExistingPropertyMapperDto() {
        TypeMap<GroupDTO, Group> typeMapMock = mock(TypeMap.class);
        when(modelMapper.createTypeMap(GroupDTO.class, Group.class)).thenReturn(typeMapMock);
        when(modelMapper.map(groupDTO, Group.class)).thenReturn(new Group());
        groupConverter.convertToEntity(groupDTO, group);

        reset(modelMapper);
        when(modelMapper.map(groupDTO, Group.class)).thenReturn(new Group());
        groupConverter.convertToEntity(groupDTO, group);
        verify(modelMapper, times(0)).createTypeMap(GroupDTO.class, Group.class);
    }
}