package br.edu.fatecsjc.lgnspringapi.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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
import br.edu.fatecsjc.lgnspringapi.converter.MemberConverter;
import br.edu.fatecsjc.lgnspringapi.dto.MemberDTO;
import br.edu.fatecsjc.lgnspringapi.entity.Group;
import br.edu.fatecsjc.lgnspringapi.entity.Member;
import br.edu.fatecsjc.lgnspringapi.repository.GroupRepository;
import br.edu.fatecsjc.lgnspringapi.repository.MarathonRepository;
import br.edu.fatecsjc.lgnspringapi.repository.MemberRepository;
import jakarta.transaction.Transactional;

@ExtendWith(MockitoExtension.class)
public class MemberServiceTest {

    @InjectMocks
    private MemberService memberService;

    @Mock
    private MemberRepository memberRepository;

    @Mock
    private GroupRepository groupRepository;

    @Mock
    private MemberConverter memberConverter;

    @Mock
    private GroupConverter groupConverter;

    @Mock
    private MarathonRepository marathonRepository;

    @Test
    public void testGetAll() {
        List<Member> members = new ArrayList<>();
        when(memberRepository.findAll()).thenReturn(members);
        memberService.getAll();
        verify(memberConverter).convertToDto(members);
    }

    @Test
    public void testFindById() {
        Member member = new Member();
        when(memberRepository.findById(anyLong())).thenReturn(Optional.of(member));
        memberService.findById(1L);
        verify(memberConverter).convertToDto(member);
    }

    @Test
    @Transactional
    public void testSaveWithId() {
        MemberDTO dto = new MemberDTO();
        Member member = new Member();
        when(memberRepository.findById(anyLong())).thenReturn(Optional.of(member));
        when(memberRepository.save(any(Member.class))).thenReturn(member);
        memberService.save(1L, dto);
        verify(memberConverter).convertToDto(member);
    }

    @Test
    public void testSave() {
        MemberDTO dto = new MemberDTO();
        Member member = new Member();
        member.setMarathons(new ArrayList<>());
        when(memberConverter.convertToEntity(dto)).thenReturn(member);
        when(memberRepository.save(any(Member.class))).thenReturn(member);
        memberService.save(dto);
        verify(memberConverter).convertToDto(member);
    }

    @Test
    public void testDelete() {
        Member member = new Member();
        Group group = new Group();
        group.setMembers(new ArrayList<>());
        member.setGroup(group);
        member.setMarathons(new ArrayList<>());
        when(memberRepository.findById(anyLong())).thenReturn(Optional.of(member));
        memberService.delete(1L);
        verify(memberRepository).delete(member);
    }
}