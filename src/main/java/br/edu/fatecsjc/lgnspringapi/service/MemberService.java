package br.edu.fatecsjc.lgnspringapi.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

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

    private final MemberRepository memberRepository;

    private final GroupRepository groupRepository;

    private final MemberConverter memberConverter;

    private final MarathonRepository marathonRepository;

    public MemberService(MemberRepository memberRepository, GroupRepository groupRepository, MemberConverter memberConverter, MarathonRepository marathonRepository) {
        this.memberRepository = memberRepository;
        this.groupRepository = groupRepository;
        this.memberConverter = memberConverter;
        this.marathonRepository = marathonRepository;
    }

    public List<MemberDTO> getAll() {
        return memberConverter.convertToDto(memberRepository.findAll());
    }

    public MemberDTO findById(Long id) {
        Optional<Member> member = memberRepository.findById(id);
        if (member.isPresent()) {
            return memberConverter.convertToDto(member.get());
        }
        return null;
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

        if(dto.getMarathonIds() != null) {
            List<Marathon> marathons = marathonRepository.findAllById(dto.getMarathonIds());
            existingMember.getMarathons().clear();
            existingMember.getMarathons().addAll(marathons);
        }
        
        Member memberReturned = memberRepository.save(existingMember);

        return memberConverter.convertToDto(memberReturned);
    }
    
    public MemberDTO save(MemberDTO dto) {
        Member memberToSaved = memberConverter.convertToEntity(dto);

        memberToSaved.getMarathons().forEach(marathon -> marathon.getMembers().add(memberToSaved));

        if (dto.getGroupId() != null) {
            Group group = groupRepository.findById(dto.getGroupId()).orElse(null);
            memberToSaved.setGroup(group);
        }

        Member memberReturned = memberRepository.save(memberToSaved);
        return memberConverter.convertToDto(memberReturned);
    }

    public void delete(Long memberId) {
        Member member = memberRepository.findById(memberId)
            .orElseThrow(() -> new RuntimeException("Member not found"));
    
        for (Marathon marathon : member.getMarathons()) {
            marathon.getMembers().remove(member);
            marathonRepository.save(marathon); 
        }
    
        member.getMarathons().clear();

        Group group = member.getGroup();
        group.getMembers().remove(member);
        groupRepository.save(group);

        member.setGroup(null);
    
        memberRepository.delete(member);
    }
}