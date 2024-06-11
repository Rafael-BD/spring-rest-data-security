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

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class GroupRepositoryTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private GroupRepository groupRepository;

    @Test
    public void testFindGroupById() {
        Group group = new Group();
        group.setName("Test Group");
        entityManager.persist(group);
        entityManager.flush();

        Optional<Group> found = groupRepository.findById(group.getId());

        assertThat(found).isPresent();
        assertThat(found.get().getName()).isEqualTo(group.getName());
    }

    @Test
    public void testNotFindGroupByInvalidId() {
        Optional<Group> fromDb = groupRepository.findById(-11L);
        assertThat(fromDb).isNotPresent();
    }

    @Test
    public void testFindAllGroups() {
        Group group1 = new Group();
        Group group2 = new Group();
        Group group3 = new Group();

        entityManager.persist(group1);
        entityManager.persist(group2);
        entityManager.persist(group3);
        entityManager.flush();

        List<Group> allGroups = groupRepository.findAll();
        List<Group> testGroups = Arrays.asList(group1, group2, group3);

        assertThat(allGroups).containsAll(testGroups);
    }

    @Test
    public void testSaveGroup() {
        Group group = new Group();
        group.setName("Test Group");

        Group savedGroup = groupRepository.save(group);

        assertThat(savedGroup).hasFieldOrPropertyWithValue("name", "Test Group");
    }

    @Test
    public void testDeleteGroupById() {
        Group group1 = new Group();
        Group group2 = new Group();

        entityManager.persist(group1);
        entityManager.persist(group2);
        entityManager.flush();

        groupRepository.deleteById(group1.getId());

        List<Group> allGroups = groupRepository.findAll();

        assertThat(allGroups).doesNotContain(group1).contains(group2);
    }
}