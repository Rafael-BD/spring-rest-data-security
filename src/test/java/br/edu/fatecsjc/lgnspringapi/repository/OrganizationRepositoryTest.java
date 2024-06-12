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

import br.edu.fatecsjc.lgnspringapi.entity.Organization;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class OrganizationRepositoryTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private OrganizationRepository organizationRepository;

    @Test
    public void testFindOrganizationById() {
        Organization organization = Organization.builder()
            .name("Organization1")
            .cep("12345")
            .number("1")
            .street("Street")
            .city("City")
            .state("State")
            .country("Country")
            .instituition_name("Institution1")
            .build();
        entityManager.persist(organization);
        entityManager.flush();

        Optional<Organization> found = organizationRepository.findById(organization.getId());

        assertThat(found).isPresent();
        assertThat(found.get().getName()).isEqualTo(organization.getName());
        assertThat(found.get().getCep()).isEqualTo(organization.getCep());
        assertThat(found.get().getNumber()).isEqualTo(organization.getNumber());
        assertThat(found.get().getStreet()).isEqualTo(organization.getStreet());
        assertThat(found.get().getCity()).isEqualTo(organization.getCity());
        assertThat(found.get().getState()).isEqualTo(organization.getState());
        assertThat(found.get().getCountry()).isEqualTo(organization.getCountry());
        assertThat(found.get().getInstituition_name()).isEqualTo(organization.getInstituition_name());
    }

    @Test
    public void testNotFindOrganizationByInvalidId() {
        Optional<Organization> fromDb = organizationRepository.findById(-11L);
        assertThat(fromDb).isNotPresent();
    }

    @Test
    public void testFindAllOrganizations() {
        Organization organization1 = Organization.builder().name("Organization1").build();
        Organization organization2 = Organization.builder().name("Organization2").build();
        Organization organization3 = Organization.builder().name("Organization3").build();

        entityManager.persist(organization1);
        entityManager.persist(organization2);
        entityManager.persist(organization3);
        entityManager.flush();

        List<Organization> allOrganizations = organizationRepository.findAll();
        List<Organization> testOrganizations = Arrays.asList(organization1, organization2, organization3);
        assertThat(allOrganizations).containsAll(testOrganizations);
    }

    @Test
    public void testSaveOrganization() {
        Organization organization = Organization.builder().name("Organization1").build();

        Organization savedOrganization = organizationRepository.save(organization);

        assertThat(savedOrganization).hasFieldOrPropertyWithValue("name", "Organization1");
    }

    @Test
    public void testDeleteOrganizationById() {
        Organization organization1 = Organization.builder().name("Organization1").build();
        Organization organization2 = Organization.builder().name("Organization2").build();

        entityManager.persist(organization1);
        entityManager.persist(organization2);
        entityManager.flush();

        organizationRepository.deleteById(organization1.getId());

        List<Organization> allOrganizations = organizationRepository.findAll();

        assertThat(allOrganizations).doesNotContain(organization1).contains(organization2);
    }
}