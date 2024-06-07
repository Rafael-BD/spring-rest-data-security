package br.edu.fatecsjc.lgnspringapi.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.edu.fatecsjc.lgnspringapi.converter.GroupConverter;
import br.edu.fatecsjc.lgnspringapi.converter.MemberConverter;
import br.edu.fatecsjc.lgnspringapi.dto.MemberDTO;
import br.edu.fatecsjc.lgnspringapi.entity.Group;
import br.edu.fatecsjc.lgnspringapi.entity.Marathon;
import br.edu.fatecsjc.lgnspringapi.entity.Member;
import br.edu.fatecsjc.lgnspringapi.repository.GroupRepository;
import br.edu.fatecsjc.lgnspringapi.repository.MarathonRepository;
import br.edu.fatecsjc.lgnspringapi.repository.MemberRepository;
import jakarta.transaction.Transactional;

@Service
public class MemberService { 

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private GroupRepository groupRepository;

    @Autowired
    private MemberConverter memberConverter;

    @Autowired
    private GroupConverter groupConverter;

    @Autowired
    private MarathonRepository marathonRepository;

    public List<MemberDTO> getAll() {
        return memberConverter.convertToDto(memberRepository.findAll());
    }

    public MemberDTO findById(Long id) {
        return memberConverter.convertToDto(memberRepository.findById(id).get());
    }

    @Transactional
    public MemberDTO save(Long id, MemberDTO dto) {
        Member existingMember = memberRepository.findById(id).get();

        existingMember.setName(dto.getName());
        existingMember.setAge(dto.getAge());

        if (dto.getGroupId() != null) {
            Group group = groupRepository.findById(dto.getGroupId()).orElse(null);
            existingMember.setGroup(group);
        }

        List<Marathon> marathons = marathonRepository.findAllById(dto.getMarathonIds());

        existingMember.getMarathons().clear();
        existingMember.getMarathons().addAll(marathons);
        
        Member memberReturned = memberRepository.save(existingMember);

        return memberConverter.convertToDto(memberReturned);
    }
    
    public MemberDTO save(MemberDTO dto) {
        Member memberToSaved = memberConverter.convertToEntity(dto);

        memberToSaved.getMarathons().forEach(marathon -> {
            marathon.getMembers().add(memberToSaved);
        });

        if (dto.getGroupId() != null) {
            Group group = groupRepository.findById(dto.getGroupId()).orElse(null);
            memberToSaved.setGroup(group);
        }

        Member memberReturned = memberRepository.save(memberToSaved);
        return memberConverter.convertToDto(memberReturned);
    }

    public void delete(Long id) {
        memberRepository.deleteById(id);
    }
}