package br.edu.fatecsjc.lgnspringapi.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.mockito.junit.jupiter.MockitoExtension;

import br.edu.fatecsjc.lgnspringapi.converter.GroupConverter;
import br.edu.fatecsjc.lgnspringapi.converter.OrganizationConverter;
import br.edu.fatecsjc.lgnspringapi.dto.OrganizationDTO;
import br.edu.fatecsjc.lgnspringapi.entity.Organization;
import br.edu.fatecsjc.lgnspringapi.repository.GroupRepository;
import br.edu.fatecsjc.lgnspringapi.repository.OrganizationRepository;
import jakarta.transaction.Transactional;

@ExtendWith(MockitoExtension.class)
class OrganizationServiceTest {

    @InjectMocks
    private OrganizationService organizationService;

    @Mock
    private OrganizationRepository organizationRepository;

    @Mock
    private GroupRepository groupRepository;

    @Mock
    private OrganizationConverter organizationConverter;

    @Test
    void testGetAll() {
        List<Organization> organizations = new ArrayList<>();
        when(organizationRepository.findAll()).thenReturn(organizations);
        organizationService.getAll();
        verify(organizationConverter).convertToDto(organizations);
    }

    @Test
    void testFindById() {
        Organization organization = new Organization();
        when(organizationRepository.findById(anyLong())).thenReturn(Optional.of(organization));
        organizationService.findById(1L);
        verify(organizationConverter).convertToDto(organization);
    }

    @Test
    void testFindByIdWithOrganizationRepositoryNull() {
        when(organizationRepository.findById(anyLong())).thenReturn(Optional.empty());
        OrganizationDTO result = organizationService.findById(1L);
        assertThat(result).isNull();
    }

    @Test
    @Transactional
    void testSaveWithId() {
        OrganizationDTO dto = new OrganizationDTO();
        Organization organization = new Organization();
        when(organizationRepository.findById(anyLong())).thenReturn(Optional.of(organization));
        when(organizationConverter.convertToEntity(dto, organization)).thenReturn(organization);
        when(organizationRepository.save(any(Organization.class))).thenReturn(organization);
        organizationService.save(1L, dto);
        verify(organizationConverter).convertToDto(organization);
    }

    @Test
    void testSave() {
        OrganizationDTO dto = new OrganizationDTO();
        Organization organization = new Organization();
        when(organizationConverter.convertToEntity(dto)).thenReturn(organization);
        when(organizationRepository.save(any(Organization.class))).thenReturn(organization);
        organizationService.save(dto);
        verify(organizationConverter).convertToDto(organization);
    }

    @Test
    @Transactional
    void testSaveWithIdAndGroups() {
        OrganizationDTO dto = new OrganizationDTO();
        dto.setGroups(new ArrayList<>());

        Organization organization = new Organization();
        organization.setGroups(new ArrayList<>());

        when(organizationRepository.findById(anyLong())).thenReturn(Optional.of(organization));
        when(organizationConverter.convertToEntity(dto, organization)).thenReturn(organization);
        when(organizationRepository.save(any(Organization.class))).thenReturn(organization);

        organizationService.save(1L, dto);

        verify(organizationConverter).convertToDto(organization);
        organization.getGroups().forEach(group -> assertEquals(organization.getId(), group.getOrganization().getId()));
    }

    @Test
    @Transactional
    void testSaveWithGroups() {
        OrganizationDTO dto = new OrganizationDTO();
        dto.setGroups(new ArrayList<>());

        Organization organization = new Organization();
        organization.setGroups(new ArrayList<>());

        when(organizationConverter.convertToEntity(dto)).thenReturn(organization);
        when(organizationRepository.save(any(Organization.class))).thenReturn(organization);

        organizationService.save(dto);

        verify(organizationConverter).convertToDto(organization);
        organization.getGroups().forEach(group -> assertEquals(organization.getId(), group.getOrganization().getId()));
    }

    @Test
    void testDelete() {
        organizationService.delete(1L);
        verify(organizationRepository).deleteById(1L);
    }
}