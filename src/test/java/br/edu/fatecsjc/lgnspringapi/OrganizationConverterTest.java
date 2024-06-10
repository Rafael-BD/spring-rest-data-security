package br.edu.fatecsjc.lgnspringapi;
import java.util.Arrays;
import static java.util.Arrays.asList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.mockito.ArgumentMatchers.anyList;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import org.mockito.MockitoAnnotations;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeMap;
import org.modelmapper.TypeToken;

import br.edu.fatecsjc.lgnspringapi.converter.OrganizationConverter;
import br.edu.fatecsjc.lgnspringapi.dto.GroupDTO;
import br.edu.fatecsjc.lgnspringapi.dto.MemberDTO;
import br.edu.fatecsjc.lgnspringapi.dto.OrganizationDTO;
import br.edu.fatecsjc.lgnspringapi.entity.Group;
import br.edu.fatecsjc.lgnspringapi.entity.Organization;
import br.edu.fatecsjc.lgnspringapi.repository.GroupRepository;


public class OrganizationConverterTest {

    @Mock
    private ModelMapper modelMapper;

    @Mock
    private GroupRepository groupRepository;

    @InjectMocks
    private OrganizationConverter organizationConverter;

    private OrganizationDTO organizationDTO;
    private Organization organization;
    private Group group;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);

        organizationDTO = new OrganizationDTO();
        organizationDTO.setId(1L);
        organizationDTO.setName("Organization Name");
        organizationDTO.setCep("12345-678");
        organizationDTO.setNumber("123");
        organizationDTO.setStreet("Street Name");
        organizationDTO.setCity("City Name");
        organizationDTO.setState("State Name");
        organizationDTO.setCountry("Country Name");
        organizationDTO.setInstituition_name("Institution Name");
        organizationDTO.setGroups(Arrays.asList(new GroupDTO(1L, "Group Name", Arrays.asList(new MemberDTO(1L, "Member Name", 22, asList(1L), 1L)))));

        organization = new Organization();
        organization.setId(1L);
        organization.setName("Organization Name");
        organization.setCep("12345-678");
        organization.setNumber("123");
        organization.setStreet("Street Name");
        organization.setCity("City Name");
        organization.setState("State Name");
        organization.setCountry("Country Name");
        organization.setInstituition_name("Institution Name");

        group = new Group();
        group.setId(1L);
        group.setName("Group Name");
        group.setOrganization(organization);

        organization.setGroups(Arrays.asList(group));

        TypeMap<OrganizationDTO, Organization> typeMapMock = mock(TypeMap.class);
        when(modelMapper.createTypeMap(OrganizationDTO.class, Organization.class)).thenReturn(typeMapMock);
    }

    @Test
    public void testConvertToEntity() {
        when(modelMapper.map(organizationDTO, Organization.class)).thenReturn(organization);
        when(groupRepository.findAllById(anyList())).thenReturn(Arrays.asList(group));

        Organization result = organizationConverter.convertToEntity(organizationDTO);

        assertEquals(organization, result);
        assertNotNull(result.getGroups());
        assertTrue(result.getGroups().contains(group));
    }

    @Test
    public void testConvertToDto() {
        when(modelMapper.map(organization, OrganizationDTO.class)).thenReturn(organizationDTO);

        OrganizationDTO result = organizationConverter.convertToDto(organization);

        assertEquals(organizationDTO, result);
    }

    @Test
    public void testConvertToEntityList() {
        List<OrganizationDTO> dtos = Arrays.asList(organizationDTO);
        List<Organization> organizations = Arrays.asList(organization);
        when(modelMapper.map(dtos, new TypeToken<List<Organization>>(){}.getType())).thenReturn(organizations);
        when(groupRepository.findAllById(anyList())).thenReturn(Arrays.asList(group));

        List<Organization> result = organizationConverter.convertToEntity(dtos);

        assertEquals(organizations, result);
        assertTrue(result.stream().allMatch(o -> o.getGroups().contains(group)));
    }

    @Test
    public void testConvertToDtoList() {
        List<Organization> organizations = Arrays.asList(organization);
        List<OrganizationDTO> dtos = Arrays.asList(organizationDTO);
        when(modelMapper.map(organizations, new TypeToken<List<OrganizationDTO>>(){}.getType())).thenReturn(dtos);

        List<OrganizationDTO> result = organizationConverter.convertToDto(organizations);

        assertEquals(dtos, result);
    }
}