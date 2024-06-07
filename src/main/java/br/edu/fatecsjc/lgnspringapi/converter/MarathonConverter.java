package br.edu.fatecsjc.lgnspringapi.converter;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.edu.fatecsjc.lgnspringapi.dto.MarathonDTO;
import br.edu.fatecsjc.lgnspringapi.entity.Marathon;
import br.edu.fatecsjc.lgnspringapi.entity.Member;
import br.edu.fatecsjc.lgnspringapi.repository.MemberRepository;

@Component
public class MarathonConverter implements Converter<Marathon, MarathonDTO> {

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private MemberRepository memberRepository;

    @Override
    public Marathon convertToEntity(MarathonDTO dto) {
        Marathon entity = modelMapper.map(dto, Marathon.class);

        if(dto.getMemberIds() == null) {
            return entity;
        }
        entity.setMembers(dto.getMemberIds().stream()
            .map(id -> memberRepository.findById(id).orElse(null))
            .collect(Collectors.toList()));

        return entity;
    }

    @Override
    public Marathon convertToEntity(MarathonDTO dto, Marathon existingEntity) {
        modelMapper.map(dto, existingEntity);

        if(dto.getMemberIds() == null) {
            return existingEntity;
        }
        existingEntity.setMembers(dto.getMemberIds().stream()
            .map(id -> memberRepository.findById(id).orElse(null))
            .collect(Collectors.toList()));

        return existingEntity;
    }

    @Override
    public MarathonDTO convertToDto(Marathon entity) {
        MarathonDTO dto = modelMapper.map(entity, MarathonDTO.class);

        if(entity.getMembers() == null) {
            return dto;
        }
        dto.setMemberIds(entity.getMembers().stream()
            .map(Member::getId)
            .collect(Collectors.toList()));

        return dto;
    }

    @Override
    public List<Marathon> convertToEntity(List<MarathonDTO> dtos) {
        return dtos.stream()
            .map(this::convertToEntity)
            .collect(Collectors.toList());
    }

    @Override
    public List<MarathonDTO> convertToDto(List<Marathon> entities) {
        return entities.stream()
            .map(this::convertToDto)
            .collect(Collectors.toList());
    }
}