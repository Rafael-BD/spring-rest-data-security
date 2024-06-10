package br.edu.fatecsjc.lgnspringapi.converter;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.edu.fatecsjc.lgnspringapi.dto.MemberDTO;
import br.edu.fatecsjc.lgnspringapi.entity.Group;
import br.edu.fatecsjc.lgnspringapi.entity.Marathon;
import br.edu.fatecsjc.lgnspringapi.entity.Member;
import br.edu.fatecsjc.lgnspringapi.repository.GroupRepository;
import br.edu.fatecsjc.lgnspringapi.repository.MarathonRepository;


@Component
public class MemberConverter implements Converter<Member, MemberDTO> {

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private MarathonRepository marathonRepository;

    @Autowired
    private GroupRepository groupRepository;

    @Autowired
    private GroupConverter groupConverter;

    @Override
    public Member convertToEntity(MemberDTO dto) {
        Member entity = modelMapper.map(dto, Member.class);

        entity.setMarathons(dto.getMarathonIds().stream()
            .map(id -> marathonRepository.findById(id).orElse(null))
            .collect(Collectors.toList()));

        if (dto.getGroupId() != null) {
            Group group = groupRepository.findById(dto.getGroupId()).orElse(null);
            entity.setGroup(group);
        }

        return entity;
    }

    @Override
    public Member convertToEntity(MemberDTO dto, Member existingEntity) {
        modelMapper.map(dto, existingEntity);
        
        existingEntity.setMarathons(dto.getMarathonIds().stream()
            .map(id -> marathonRepository.findById(id).orElse(null))
            .collect(Collectors.toList()));

        if (dto.getGroupId() != null) {
            Group group = groupRepository.findById(dto.getGroupId()).orElse(null);
            existingEntity.setGroup(group);
        }

        return existingEntity;
    }

    @Override
    public MemberDTO convertToDto(Member entity) {
        MemberDTO dto = modelMapper.map(entity, MemberDTO.class);

        dto.setMarathonIds(entity.getMarathons().stream()
            .map(Marathon::getId)
            .collect(Collectors.toList()));

        if (entity.getGroup() != null) {
            dto.setGroupId(entity.getGroup().getId());
        }

        return dto;
    }

    @Override
    public List<Member> convertToEntity(List<MemberDTO> dtos) {
        List<Member> members = modelMapper.map(dtos, new TypeToken<List<Member>>(){}.getType());
        members.forEach(member -> {
            member.getMarathons().forEach(marathon -> {
                if (marathon.getMembers() == null) {
                    marathon.setMembers(new ArrayList<>());
                }
                marathon.setMembers(members);
            });
        });
        return members;
    }

    @Override
    public List<MemberDTO> convertToDto(List<Member> entities) {
        List<MemberDTO> dtos = modelMapper.map(entities, new TypeToken<List<MemberDTO>>(){}.getType());
        dtos.forEach(dto -> {
            if(dto.getMarathonIds() == null) {
                dto.setMarathonIds(new ArrayList<>());
                return;
            }
            dto.setMarathonIds(dto.getMarathonIds());
        });
        return dtos;
    }
}