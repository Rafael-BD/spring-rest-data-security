package br.edu.fatecsjc.lgnspringapi.repository;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import br.edu.fatecsjc.lgnspringapi.entity.Group;
import br.edu.fatecsjc.lgnspringapi.entity.Member;


@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class MemberRepositoryTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private MemberRepository memberRepository;

    @Test
    void testFindMemberById() {
        Group group = Group.builder().name("Group1").build();
        entityManager.persist(group);

        Member member = Member.builder().name("User1").age(25).group(group).build();
        entityManager.persist(member);
        entityManager.flush();

        Optional<Member> found = memberRepository.findById(member.getId());

        assertThat(found).isPresent();
        assertThat(found.get().getName()).isEqualTo(member.getName());
        assertThat(found.get().getAge()).isEqualTo(member.getAge());
    }

    @Test
    void testNotFindMemberByInvalidId() {
        Optional<Member> fromDb = memberRepository.findById(-11L);
        assertThat(fromDb).isNotPresent();
    }

    @Test
    void testFindAllMembers() {
        Group group = Group.builder().name("Group1").build();
        entityManager.persist(group);

        Member member1 = Member.builder().name("User1").age(25).group(group).build();
        Member member2 = Member.builder().name("User2").age(30).group(group).build();
        Member member3 = Member.builder().name("User3").age(35).group(group).build();

        entityManager.persist(member1);
        entityManager.persist(member2);
        entityManager.persist(member3);
        entityManager.flush();

        List<Member> allMembers = memberRepository.findAll();
        List<Member> testMembers = Arrays.asList(member1, member2, member3);
        assertThat(allMembers).containsAll(testMembers);
    }

    @Test
    void testSaveMember() {
        Group group = Group.builder().name("Group1").build();
        entityManager.persist(group);

        Member member = Member.builder().name("User1").age(25).group(group).build();

        Member savedMember = memberRepository.save(member);

        assertThat(savedMember).hasFieldOrPropertyWithValue("name", "User1");
        assertThat(savedMember).hasFieldOrPropertyWithValue("age", 25);
    }

    @Test
    void testDeleteMemberById() {
        Group group = Group.builder().name("Group1").build();
        entityManager.persist(group);

        Member member1 = Member.builder().name("User1").age(25).group(group).build();
        Member member2 = Member.builder().name("User2").age(30).group(group).build();

        entityManager.persist(member1);
        entityManager.persist(member2);
        entityManager.flush();

        memberRepository.deleteById(member1.getId());

        List<Member> allMembers = memberRepository.findAll();

        assertThat(allMembers).doesNotContain(member1).contains(member2);
    }
}