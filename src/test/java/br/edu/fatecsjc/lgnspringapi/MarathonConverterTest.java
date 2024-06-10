package br.edu.fatecsjc.lgnspringapi;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
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

import br.edu.fatecsjc.lgnspringapi.converter.MarathonConverter;
import br.edu.fatecsjc.lgnspringapi.dto.MarathonDTO;
import br.edu.fatecsjc.lgnspringapi.entity.Marathon;
import br.edu.fatecsjc.lgnspringapi.entity.Member;
import br.edu.fatecsjc.lgnspringapi.repository.MemberRepository;

public class MarathonConverterTest {

    @Mock
    private ModelMapper modelMapper;

    @Mock
    private MemberRepository memberRepository;

    @InjectMocks
    private MarathonConverter marathonConverter;

    private MarathonDTO marathonDTO;
    private Marathon marathon;
    private Member member;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);

        marathonDTO = new MarathonDTO();
        marathonDTO.setId(1L);
        marathonDTO.setWeight(10);
        marathonDTO.setScore(100);
        marathonDTO.setMemberIds(Arrays.asList(1L));

        marathon = new Marathon();
        marathon.setId(1L);
        marathon.setWeight(10);
        marathon.setScore(100);

        member = new Member();
        member.setId(1L);
        member.setName("Member Name");
        member.setAge(22);

        marathon.setMembers(Arrays.asList(member));

        TypeMap<MarathonDTO, Marathon> typeMapMock = mock(TypeMap.class);
        when(modelMapper.createTypeMap(MarathonDTO.class, Marathon.class)).thenReturn(typeMapMock);
    }

    @Test
    public void testConvertToEntity() {
        when(modelMapper.map(marathonDTO, Marathon.class)).thenReturn(marathon);
        when(memberRepository.findById(1L)).thenReturn(Optional.of(member));

        Marathon result = marathonConverter.convertToEntity(marathonDTO);

        assertEquals(marathon, result);
        assertNotNull(result.getMembers());
        assertTrue(result.getMembers().contains(member));
    }

    @Test
    public void testConvertToEntityWithExistingEntity() {
        when(memberRepository.findById(1L)).thenReturn(Optional.of(member));

        Marathon result = marathonConverter.convertToEntity(marathonDTO, marathon);

        assertEquals(marathon.getId(), result.getId());
        assertEquals(marathon.getWeight(), result.getWeight());
        assertEquals(marathon.getScore(), result.getScore());
        assertNotNull(result.getMembers());
        assertTrue(result.getMembers().contains(member));
    }

    @Test
    public void testConvertToDto() {
        when(modelMapper.map(marathon, MarathonDTO.class)).thenReturn(marathonDTO);

        MarathonDTO result = marathonConverter.convertToDto(marathon);

        assertEquals(marathonDTO, result);
    }

    @Test
    public void testConvertToEntityList() {
        List<MarathonDTO> dtos = Arrays.asList(marathonDTO);
        List<Marathon> marathons = Arrays.asList(marathon);
        when(modelMapper.map(dtos, new TypeToken<List<Marathon>>(){}.getType())).thenReturn(marathons);
        when(memberRepository.findById(1L)).thenReturn(Optional.of(member));

        List<Marathon> result = marathonConverter.convertToEntity(dtos);

        assertEquals(marathons, result);
        assertTrue(result.stream().allMatch(m -> m.getMembers().contains(member)));
    }

    @Test
    public void testConvertToDtoList() {
        List<Marathon> marathons = Arrays.asList(marathon);
        List<MarathonDTO> dtos = Arrays.asList(marathonDTO);
        when(modelMapper.map(marathons, new TypeToken<List<MarathonDTO>>(){}.getType())).thenReturn(dtos);

        List<MarathonDTO> result = marathonConverter.convertToDto(marathons);

        assertEquals(dtos, result);
    }
}