package br.edu.fatecsjc.lgnspringapi.converter;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.stereotype.Component;

import br.edu.fatecsjc.lgnspringapi.dto.MarathonDTO;
import br.edu.fatecsjc.lgnspringapi.entity.Marathon;
import br.edu.fatecsjc.lgnspringapi.entity.Member;
import br.edu.fatecsjc.lgnspringapi.repository.MemberRepository;

@Component
public class MarathonConverter implements Converter<Marathon, MarathonDTO> {

    private final ModelMapper modelMapper;

    private final MemberRepository memberRepository;

    public MarathonConverter(ModelMapper modelMapper, MemberRepository memberRepository) {
        this.modelMapper = modelMapper;
        this.memberRepository = memberRepository;
    }

    @Override
    public Marathon convertToEntity(MarathonDTO dto) {
        Marathon entity = modelMapper.map(dto, Marathon.class);

        if(dto.getMemberIds() != null) {
            entity.setMembers(dto.getMemberIds().stream()
            .map(id -> memberRepository.findById(id).orElse(null))
            .collect(Collectors.toList()));
        }
        
        return entity;
    }

    @Override
    public Marathon convertToEntity(MarathonDTO dto, Marathon existingEntity) {
        modelMapper.map(dto, existingEntity);

        if(dto.getMemberIds() != null) {
            existingEntity.setMembers(dto.getMemberIds().stream()
            .map(id -> memberRepository.findById(id).orElse(null))
            .collect(Collectors.toList()));
        }

        return existingEntity;
    }

    @Override
    public MarathonDTO convertToDto(Marathon entity) {
        MarathonDTO dto = modelMapper.map(entity, MarathonDTO.class);

        if(entity.getMembers() != null) {
            dto.setMemberIds(entity.getMembers().stream()
            .map(Member::getId)
            .collect(Collectors.toList()));
        }

        return dto;
    }

    @Override
    public List<Marathon> convertToEntity(List<MarathonDTO> dtos) {
        List<Marathon> marathons = modelMapper.map(dtos, new TypeToken<List<Marathon>>(){}.getType());
        marathons.forEach(marathon -> {
            if(marathon.getMembers() != null) {
                marathon.getMembers().forEach(member -> {
                    member.setMarathons(marathons);
                });
            }    
        });
        return marathons;
    }

    @Override
    public List<MarathonDTO> convertToDto(List<Marathon> entities) {
        return modelMapper.map(entities, new TypeToken<List<MarathonDTO>>(){}.getType());
    }
}