package br.edu.fatecsjc.lgnspringapi.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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