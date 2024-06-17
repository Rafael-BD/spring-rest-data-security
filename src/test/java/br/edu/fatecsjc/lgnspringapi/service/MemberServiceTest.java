package br.edu.fatecsjc.lgnspringapi.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.mockito.junit.jupiter.MockitoExtension;

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

@ExtendWith(MockitoExtension.class)
class MemberServiceTest {

    @InjectMocks
    private MemberService memberService;

    @Mock
    private MemberRepository memberRepository;

    @Mock
    private GroupRepository groupRepository;

    @Mock
    private MemberConverter memberConverter;

    @Mock
    private MarathonRepository marathonRepository;

    @Test
    void testGetAll() {
        List<Member> members = new ArrayList<>();
        when(memberRepository.findAll()).thenReturn(members);
        memberService.getAll();
        verify(memberConverter).convertToDto(members);
    }

    @Test
    void testFindById() {
        Member member = new Member();
        when(memberRepository.findById(anyLong())).thenReturn(Optional.of(member));
        memberService.findById(1L);
        verify(memberConverter).convertToDto(member);
    }

    @Test
    @Transactional
    void testSaveWithId() {
        MemberDTO dto = new MemberDTO();
        Member member = new Member();
        when(memberRepository.findById(anyLong())).thenReturn(Optional.of(member));
        when(memberRepository.save(any(Member.class))).thenReturn(member);
        memberService.save(1L, dto);
        verify(memberConverter).convertToDto(member);
    }

    @Test
    @Transactional
    void testSaveWithGroupIdAndMarathonIds() {
        MemberDTO dto = new MemberDTO();
        dto.setGroupId(1L);
        dto.setMarathonIds(Arrays.asList(1L, 2L));

        Member existingMember = new Member();
        existingMember.setMarathons(new ArrayList<>());

        Group group = new Group();
        Marathon marathon1 = new Marathon();
        Marathon marathon2 = new Marathon();

        when(memberRepository.findById(anyLong())).thenReturn(Optional.of(existingMember));
        when(groupRepository.findById(dto.getGroupId())).thenReturn(Optional.of(group));
        when(marathonRepository.findAllById(dto.getMarathonIds())).thenReturn(Arrays.asList(marathon1, marathon2));
        when(memberRepository.save(any(Member.class))).thenReturn(existingMember);

        memberService.save(1L, dto);

        assertEquals(group, existingMember.getGroup());
        assertTrue(existingMember.getMarathons().containsAll(Arrays.asList(marathon1, marathon2)));
        verify(memberConverter).convertToDto(existingMember);
    }

    @Test
    void testSave() {
        MemberDTO dto = new MemberDTO();
        Member member = new Member();
        member.setMarathons(new ArrayList<>());
        when(memberConverter.convertToEntity(dto)).thenReturn(member);
        when(memberRepository.save(any(Member.class))).thenReturn(member);
        memberService.save(dto);
        verify(memberConverter).convertToDto(member);
    }

    @Test
    @Transactional
    void testSaveWithMarathonIdsAndGroupId() {
        MemberDTO dto = new MemberDTO();
        dto.setMarathonIds(Arrays.asList(1L, 2L));
        dto.setGroupId(1L);

        Member member = new Member();
        member.setMarathons(new ArrayList<>());

        Marathon marathon1 = new Marathon();
        marathon1.setMembers(new ArrayList<>());
        Marathon marathon2 = new Marathon();
        marathon2.setMembers(new ArrayList<>());

        Group group = new Group();

        member.getMarathons().add(marathon1);
        member.getMarathons().add(marathon2);

        when(memberConverter.convertToEntity(dto)).thenReturn(member);
        when(groupRepository.findById(dto.getGroupId())).thenReturn(Optional.of(group));
        when(memberRepository.save(any(Member.class))).thenReturn(member);

        memberService.save(dto);

        verify(memberConverter).convertToDto(member);
        assertTrue(marathon1.getMembers().contains(member));
        assertTrue(marathon2.getMembers().contains(member));
        assertEquals(group, member.getGroup());
    }

    @Test
    void testDelete() {
        Member member = new Member();
        Group group = new Group();
        group.setMembers(new ArrayList<>());
        member.setGroup(group);
        member.setMarathons(new ArrayList<>());
        when(memberRepository.findById(anyLong())).thenReturn(Optional.of(member));
        memberService.delete(1L);
        verify(memberRepository).delete(member);
    }

    @Test
    void testDeleteWithMarathons() {
        Long memberId = 1L;
        Member member = new Member();
        Group group = new Group();
        group.setMembers(new ArrayList<>());
        member.setGroup(group);

        Marathon marathon1 = new Marathon();
        marathon1.setMembers(new ArrayList<>());
        marathon1.getMembers().add(member);

        Marathon marathon2 = new Marathon();
        marathon2.setMembers(new ArrayList<>());
        marathon2.getMembers().add(member);

        member.setMarathons(new ArrayList<>(Arrays.asList(marathon1, marathon2)));

        when(memberRepository.findById(memberId)).thenReturn(Optional.of(member));

        memberService.delete(memberId);

        assertTrue(marathon1.getMembers().isEmpty());
        assertTrue(marathon2.getMembers().isEmpty());
        assertTrue(group.getMembers().isEmpty());
        verify(marathonRepository, times(2)).save(any(Marathon.class));
        verify(groupRepository).save(group);
        verify(memberRepository).delete(member);
    }
}