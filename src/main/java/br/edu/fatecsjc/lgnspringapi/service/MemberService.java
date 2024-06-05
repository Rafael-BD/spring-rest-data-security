package br.edu.fatecsjc.lgnspringapi.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.edu.fatecsjc.lgnspringapi.converter.MemberConverter;
import br.edu.fatecsjc.lgnspringapi.dto.MemberDTO;
import br.edu.fatecsjc.lgnspringapi.entity.Member;
import br.edu.fatecsjc.lgnspringapi.repository.MemberRepository;
import jakarta.transaction.Transactional;

@Service
public class MemberService { // FIXME: Fix register of member's marathons in member_marathon table

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private MemberConverter memberConverter;

    public List<MemberDTO> getAll() {
        return memberConverter.convertToDto(memberRepository.findAll());
    }

    public MemberDTO findById(Long id) {
        return memberConverter.convertToDto(memberRepository.findById(id).get());
    }

    @Transactional
    public MemberDTO save(Long id, MemberDTO dto) {
        Member entity = memberRepository.findById(id).get();

        Member memberToSaved = memberConverter.convertToEntity(dto, entity);
        Member memberReturned = memberRepository.save(memberToSaved);
        return memberConverter.convertToDto(memberReturned);
    }

    public MemberDTO save(MemberDTO dto) {
        Member memberToSaved = memberConverter.convertToEntity(dto);
        Member memberReturned = memberRepository.save(memberToSaved);
        return memberConverter.convertToDto(memberReturned);
    }

    public void delete(Long id) {
        memberRepository.deleteById(id);
    }
}