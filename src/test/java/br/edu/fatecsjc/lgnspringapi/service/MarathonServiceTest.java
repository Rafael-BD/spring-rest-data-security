package br.edu.fatecsjc.lgnspringapi.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.mockito.junit.jupiter.MockitoExtension;

import br.edu.fatecsjc.lgnspringapi.converter.MarathonConverter;
import br.edu.fatecsjc.lgnspringapi.dto.MarathonDTO;
import br.edu.fatecsjc.lgnspringapi.entity.Marathon;
import br.edu.fatecsjc.lgnspringapi.entity.Member;
import br.edu.fatecsjc.lgnspringapi.repository.MarathonRepository;
import br.edu.fatecsjc.lgnspringapi.repository.MemberRepository;
import jakarta.transaction.Transactional;

@ExtendWith(MockitoExtension.class)
public class MarathonServiceTest {

    @InjectMocks
    private MarathonService marathonService;

    @Mock
    private MarathonRepository marathonRepository;

    @Mock
    private MarathonConverter marathonConverter;

    @Mock
    private MemberRepository memberRepository;

    @Test
    public void testGetAll() {
        List<Marathon> marathons = new ArrayList<>();
        when(marathonRepository.findAll()).thenReturn(marathons);
        marathonService.getAll();
        verify(marathonConverter).convertToDto(marathons);
    }

    @Test
    public void testFindById() {
        Marathon marathon = new Marathon();
        when(marathonRepository.findById(anyLong())).thenReturn(Optional.of(marathon));
        marathonService.findById(1L);
        verify(marathonConverter).convertToDto(marathon);
    }

    @Test
    @Transactional
    public void testSaveWithId() {
        MarathonDTO dto = new MarathonDTO();
        Marathon marathon = new Marathon();
        when(marathonRepository.findById(anyLong())).thenReturn(Optional.of(marathon));
        when(marathonRepository.save(any(Marathon.class))).thenReturn(marathon);
        marathonService.save(1L, dto);
        verify(marathonConverter).convertToDto(marathon);
    }

    @Test
    @Transactional
    public void testSaveWithIdAndMemberIds() {
        MarathonDTO dto = new MarathonDTO();
        dto.setMemberIds(Arrays.asList(1L, 2L));

        Marathon marathon = new Marathon();
        marathon.setMembers(new ArrayList<>());

        Member member1 = new Member();
        Member member2 = new Member();

        when(marathonRepository.findById(anyLong())).thenReturn(Optional.of(marathon));
        when(memberRepository.findAllById(dto.getMemberIds())).thenReturn(Arrays.asList(member1, member2));
        when(marathonRepository.save(any(Marathon.class))).thenReturn(marathon);

        marathonService.save(1L, dto);

        verify(marathonConverter).convertToDto(marathon);
        assertTrue(marathon.getMembers().contains(member1));
        assertTrue(marathon.getMembers().contains(member2));
    }

    @Test
    public void testSave() {
        MarathonDTO dto = new MarathonDTO();
        Marathon marathon = new Marathon();
        when(marathonConverter.convertToEntity(dto)).thenReturn(marathon);
        when(marathonRepository.save(any(Marathon.class))).thenReturn(marathon);
        marathonService.save(dto);
        verify(marathonConverter).convertToDto(marathon);
    }

    @Test
    public void testDelete() {
        marathonService.delete(1L);
        verify(marathonRepository).deleteById(1L);
    }
}