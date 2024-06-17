package br.edu.fatecsjc.lgnspringapi.service;

import java.util.ArrayList;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.mockito.junit.jupiter.MockitoExtension;

import br.edu.fatecsjc.lgnspringapi.converter.GroupConverter;
import br.edu.fatecsjc.lgnspringapi.dto.GroupDTO;
import br.edu.fatecsjc.lgnspringapi.entity.Group;
import br.edu.fatecsjc.lgnspringapi.repository.GroupRepository;
import br.edu.fatecsjc.lgnspringapi.repository.MemberRepository;

@ExtendWith(MockitoExtension.class)
class GroupServiceTest {

    @Mock
    private GroupRepository groupRepository;

    @Mock
    private GroupConverter groupConverter;

    @InjectMocks
    private GroupService groupService;

    @Mock
    private MemberRepository memberRepository;

    @Test
    void testFindById() {
        Group group = new Group();
        GroupDTO dto = new GroupDTO();
        when(groupRepository.findById(1L)).thenReturn(Optional.of(group));
        when(groupConverter.convertToDto(group)).thenReturn(dto);
        GroupDTO result = groupService.findById(1L);
        assertThat(result).isEqualTo(dto);
    }

    @Test
    void testSaveWithId() {
        Group group = new Group();
        group.setMembers(new ArrayList<>());
        GroupDTO dto = new GroupDTO();
        when(groupRepository.findById(1L)).thenReturn(Optional.of(group));
        when(groupConverter.convertToEntity(dto, group)).thenReturn(group);
        when(groupRepository.save(group)).thenReturn(group);
        when(groupConverter.convertToDto(group)).thenReturn(dto);
        GroupDTO result = groupService.save(1L, dto);
        assertThat(result).isEqualTo(dto);
    }

    @Test
    void testSave() {
        Group group = new Group();
        GroupDTO dto = new GroupDTO();
        when(groupConverter.convertToEntity(dto)).thenReturn(group);
        when(groupRepository.save(group)).thenReturn(group);
        when(groupConverter.convertToDto(group)).thenReturn(dto);
        GroupDTO result = groupService.save(dto);
        assertThat(result).isEqualTo(dto);
    }

    @Test
    void testDelete() {
        doNothing().when(groupRepository).deleteById(1L);
        groupService.delete(1L);
        verify(groupRepository, times(1)).deleteById(1L);
    }
}
