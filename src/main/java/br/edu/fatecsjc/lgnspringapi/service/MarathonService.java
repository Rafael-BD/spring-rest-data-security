package br.edu.fatecsjc.lgnspringapi.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import br.edu.fatecsjc.lgnspringapi.converter.MarathonConverter;
import br.edu.fatecsjc.lgnspringapi.dto.MarathonDTO;
import br.edu.fatecsjc.lgnspringapi.entity.Marathon;
import br.edu.fatecsjc.lgnspringapi.entity.Member;
import br.edu.fatecsjc.lgnspringapi.repository.MarathonRepository;
import br.edu.fatecsjc.lgnspringapi.repository.MemberRepository;
import jakarta.transaction.Transactional;

@Service
public class MarathonService {

    private final MarathonRepository marathonRepository;

    private final MarathonConverter marathonConverter;

    private final MemberRepository memberRepository;

    public MarathonService(MarathonRepository marathonRepository, MarathonConverter marathonConverter, MemberRepository memberRepository) {
        this.marathonRepository = marathonRepository;
        this.marathonConverter = marathonConverter;
        this.memberRepository = memberRepository;
    }

    public List<MarathonDTO> getAll() {
        return marathonConverter.convertToDto(marathonRepository.findAll());
    }

    public MarathonDTO findById(Long id) {
        Optional<Marathon> marathon = marathonRepository.findById(id);
        if (marathon.isPresent()) {
            return marathonConverter.convertToDto(marathon.get());
        }
        return null;
    }

    @Transactional
    public MarathonDTO save(Long id, MarathonDTO dto) {
        Marathon entity = marathonRepository.findById(id).get();
        
        entity.setWeight(dto.getWeight());
        entity.setScore(dto.getScore());

        if (dto.getMemberIds() != null) {
            List<Member> members = memberRepository.findAllById(dto.getMemberIds());
            entity.getMembers().clear();
            entity.getMembers().addAll(members);
        }
        
        Marathon marathonReturned = marathonRepository.save(entity);

        return marathonConverter.convertToDto(marathonReturned);
    }

    public MarathonDTO save(MarathonDTO dto) {
        Marathon marathonToSaved = marathonConverter.convertToEntity(dto);
        Marathon marathonReturned = marathonRepository.save(marathonToSaved);
        return marathonConverter.convertToDto(marathonReturned);
    }

    public void delete(Long id) {
        marathonRepository.deleteById(id);
    }
}