package br.edu.fatecsjc.lgnspringapi.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.edu.fatecsjc.lgnspringapi.converter.OrganizationConverter;
import br.edu.fatecsjc.lgnspringapi.dto.OrganizationDTO;
import br.edu.fatecsjc.lgnspringapi.entity.Organization;
import br.edu.fatecsjc.lgnspringapi.repository.OrganizationRepository;
import jakarta.transaction.Transactional;

@Service
public class OrganizationService {

    private final OrganizationRepository organizationRepository;

    private final OrganizationConverter organizationConverter;

    public OrganizationService(OrganizationRepository organizationRepository, OrganizationConverter organizationConverter) {
        this.organizationRepository = organizationRepository;
        this.organizationConverter = organizationConverter;
    }

    public List<OrganizationDTO> getAll() {
        return organizationConverter.convertToDto(organizationRepository.findAll());
    }

    public OrganizationDTO findById(Long id) {
        Optional<Organization> organization = organizationRepository.findById(id);
        if (organization.isPresent()) {
            return organizationConverter.convertToDto(organization.get());
        }
        return null;
    }

    @Transactional
    public OrganizationDTO save(Long id, OrganizationDTO dto) {
        Organization entity = organizationRepository.findById(id).get();
        Organization organizationToSaved = organizationConverter.convertToEntity(dto, entity);

        if(dto.getGroups() != null) {
            organizationToSaved.getGroups().forEach(group -> {
                group.getOrganization().setId(organizationToSaved.getId());
            });
        }

        Organization organizationReturned = organizationRepository.save(organizationToSaved);
        return organizationConverter.convertToDto(organizationReturned);
    }

    @Transactional
    public OrganizationDTO save(OrganizationDTO dto) {
        Organization organizationToSaved = organizationConverter.convertToEntity(dto);
        
        if(dto.getGroups() != null) {
            organizationToSaved.getGroups().forEach(group -> {
                group.getOrganization().setId(organizationToSaved.getId());
            });
        }

        Organization organizationReturned = organizationRepository.save(organizationToSaved);
        return organizationConverter.convertToDto(organizationReturned);
    }

    public void delete(Long id) {
        organizationRepository.deleteById(id);
    }
}