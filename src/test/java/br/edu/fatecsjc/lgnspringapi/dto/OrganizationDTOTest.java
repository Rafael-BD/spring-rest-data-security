package br.edu.fatecsjc.lgnspringapi.dto;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class OrganizationDTOTest {

    private OrganizationDTO organizationDTO;

    @BeforeEach
    public void setUp() {
        organizationDTO = new OrganizationDTO();
    }

    @Test
    public void testAllArgsConstructorAndBuilder() {
        Long idValue = 1L;
        String nameValue = "Test Name";
        String cepValue = "12345-678";
        String numberValue = "123";
        String streetValue = "Test Street";
        String cityValue = "Test City";
        String stateValue = "Test State";
        String countryValue = "Test Country";
        String institutionNameValue = "Test Institution";
        GroupDTO groupDTO = new GroupDTO();
        List<GroupDTO> groupsValue = Arrays.asList(groupDTO);

        // Testa o construtor com todos os argumentos e o construtor de builder
        OrganizationDTO organizationDTOFields = new OrganizationDTO(
                idValue,
                nameValue,
                cepValue,
                numberValue,
                streetValue,
                cityValue,
                stateValue,
                countryValue,
                institutionNameValue,
                groupsValue
        );
        OrganizationDTO organizationDTOFromBuilder = OrganizationDTO.builder()
                .id(idValue)
                .name(nameValue)
                .cep(cepValue)
                .number(numberValue)
                .street(streetValue)
                .city(cityValue)
                .state(stateValue)
                .country(countryValue)
                .instituition_name(institutionNameValue)
                .groups(groupsValue)
                .build();

        // Verifica se os dois objetos são iguais
        assertEquals(organizationDTOFields, organizationDTOFromBuilder);

        // Testa os métodos equals, hashCode e toString
        assertEquals(organizationDTOFields.equals(organizationDTOFromBuilder), true);
        assertEquals(organizationDTOFields.hashCode(), organizationDTOFromBuilder.hashCode());
        assertNotNull(organizationDTOFields.toString());
    }

    @Test
    public void testId() {
        Long idValue = 1L;
        organizationDTO.setId(idValue);
        assertEquals(idValue, organizationDTO.getId());
    }

    @Test
    public void testName() {
        String nameValue = "Test Name";
        organizationDTO.setName(nameValue);
        assertEquals(nameValue, organizationDTO.getName());
    }

    @Test
    public void testCep() {
        String cepValue = "12345-678";
        organizationDTO.setCep(cepValue);
        assertEquals(cepValue, organizationDTO.getCep());
    }

    @Test
    public void testNumber() {
        String numberValue = "123";
        organizationDTO.setNumber(numberValue);
        assertEquals(numberValue, organizationDTO.getNumber());
    }

    @Test
    public void testStreet() {
        String streetValue = "Test Street";
        organizationDTO.setStreet(streetValue);
        assertEquals(streetValue, organizationDTO.getStreet());
    }

    @Test
    public void testCity() {
        String cityValue = "Test City";
        organizationDTO.setCity(cityValue);
        assertEquals(cityValue, organizationDTO.getCity());
    }

    @Test
    public void testState() {
        String stateValue = "Test State";
        organizationDTO.setState(stateValue);
        assertEquals(stateValue, organizationDTO.getState());
    }

    @Test
    public void testCountry() {
        String countryValue = "Test Country";
        organizationDTO.setCountry(countryValue);
        assertEquals(countryValue, organizationDTO.getCountry());
    }

    @Test
    public void testInstitutionName() {
        String institutionNameValue = "Test Institution";
        organizationDTO.setInstituition_name(institutionNameValue);
        assertEquals(institutionNameValue, organizationDTO.getInstituition_name());
    }

    @Test
    public void testGroups() {
        GroupDTO groupDTO = new GroupDTO();
        List<GroupDTO> groupsValue = Arrays.asList(groupDTO);
        organizationDTO.setGroups(groupsValue);
        assertEquals(groupsValue, organizationDTO.getGroups());
    }
}