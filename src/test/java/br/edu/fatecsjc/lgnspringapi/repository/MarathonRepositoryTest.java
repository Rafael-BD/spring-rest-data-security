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

import br.edu.fatecsjc.lgnspringapi.entity.Marathon;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class MarathonRepositoryTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private MarathonRepository marathonRepository;

    @Test
    public void testFindMarathonById() {
        Marathon marathon = Marathon.builder().weight(10).score(100).build();
        entityManager.persist(marathon);
        entityManager.flush();

        Optional<Marathon> found = marathonRepository.findById(marathon.getId());

        assertThat(found).isPresent();
        assertThat(found.get().getWeight()).isEqualTo(marathon.getWeight());
        assertThat(found.get().getScore()).isEqualTo(marathon.getScore());
    }

    @Test
    public void testNotFindMarathonByInvalidId() {
        Optional<Marathon> fromDb = marathonRepository.findById(-11L);
        assertThat(fromDb).isNotPresent();
    }

    @Test
    public void testFindAllMarathons() {
        Marathon marathon1 = Marathon.builder().weight(10).score(100).build();
        Marathon marathon2 = Marathon.builder().weight(20).score(200).build();
        Marathon marathon3 = Marathon.builder().weight(30).score(300).build();

        entityManager.persist(marathon1);
        entityManager.persist(marathon2);
        entityManager.persist(marathon3);
        entityManager.flush();

        List<Marathon> allMarathons = marathonRepository.findAll();
        List<Marathon> testMarathons = Arrays.asList(marathon1, marathon2, marathon3);
        assertThat(allMarathons).containsAll(testMarathons);
    }

    @Test
    public void testSaveMarathon() {
        Marathon marathon = Marathon.builder().weight(10).score(100).build();

        Marathon savedMarathon = marathonRepository.save(marathon);

        assertThat(savedMarathon).hasFieldOrPropertyWithValue("weight", 10);
        assertThat(savedMarathon).hasFieldOrPropertyWithValue("score", 100);
    }

    @Test
    public void testDeleteMarathonById() {
        Marathon marathon1 = Marathon.builder().weight(10).score(100).build();
        Marathon marathon2 = Marathon.builder().weight(20).score(200).build();

        entityManager.persist(marathon1);
        entityManager.persist(marathon2);
        entityManager.flush();

        marathonRepository.deleteById(marathon1.getId());

        List<Marathon> allMarathons = marathonRepository.findAll();

        assertThat(allMarathons).doesNotContain(marathon1).contains(marathon2);
    }
}