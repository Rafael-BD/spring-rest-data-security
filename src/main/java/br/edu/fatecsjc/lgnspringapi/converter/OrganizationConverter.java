package br.edu.fatecsjc.lgnspringapi.converter;

import java.util.ArrayList;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.modelmapper.Provider;
import org.modelmapper.TypeMap;
import org.modelmapper.TypeToken;
import org.springframework.stereotype.Component;

import br.edu.fatecsjc.lgnspringapi.dto.GroupDTO;
import br.edu.fatecsjc.lgnspringapi.dto.OrganizationDTO;
import br.edu.fatecsjc.lgnspringapi.entity.Group;
import br.edu.fatecsjc.lgnspringapi.entity.Organization;
import br.edu.fatecsjc.lgnspringapi.repository.GroupRepository;

@Component
public class OrganizationConverter implements Converter<Organization, OrganizationDTO> {

    private final ModelMapper modelMapper;

    private TypeMap<OrganizationDTO, Organization> propertyMapperDto;


    private final GroupRepository groupRepository;

    public OrganizationConverter(ModelMapper modelMapper, GroupRepository groupRepository) {
        this.modelMapper = modelMapper;
        this.groupRepository = groupRepository;
    }

    @Override
    public Organization convertToEntity(OrganizationDTO dto) {
        if(propertyMapperDto == null) {
            propertyMapperDto = modelMapper.createTypeMap(OrganizationDTO.class, Organization.class);
            propertyMapperDto.addMappings(mapper -> mapper.skip(Organization::setId));
        }
        List<Long> groupIds = dto.getGroups().stream().map(GroupDTO::getId).toList();
        List<Group> groups = groupRepository.findAllById(groupIds);

        Organization entity = modelMapper.map(dto, Organization.class);
        Provider<Organization> organizationProvider = p -> new Organization();
        propertyMapperDto.setProvider(organizationProvider);
        
        entity.setGroups(groups);

        entity.getGroups().forEach(g -> g.setOrganization(entity));
        return entity;
    }

    @Override
    public Organization convertToEntity(OrganizationDTO dto, Organization entity) {
        if(propertyMapperDto == null) {
            propertyMapperDto = modelMapper.createTypeMap(OrganizationDTO.class, Organization.class);
            propertyMapperDto.addMappings(mapper -> mapper.skip(Organization::setId));
        }

        Provider<Organization> organizationProvider = p -> entity;
        propertyMapperDto.setProvider(organizationProvider);

        Organization newEntity = modelMapper.map(dto, Organization.class);
        if (newEntity.getGroups() == null) {
            newEntity.setGroups(new ArrayList<>());
        }
    
        newEntity.getGroups().forEach(group -> group.setOrganization(newEntity));
        return newEntity;
    }

    @Override
    public OrganizationDTO convertToDto(Organization entity) {
        return modelMapper.map(entity, OrganizationDTO.class);
    }

    @Override
    public List<Organization> convertToEntity(List<OrganizationDTO> dtos) {
        List<Organization> organizations = modelMapper.map(dtos, new TypeToken<List<Organization>>() {}.getType());

        organizations.forEach(organization -> organization.getGroups().forEach(group -> group.setOrganization(organization)));
        return organizations;
    }

    @Override
    public List<OrganizationDTO> convertToDto(List<Organization> entities) {
        return modelMapper.map(entities, new TypeToken<List<OrganizationDTO>>() {}.getType());
    }
}