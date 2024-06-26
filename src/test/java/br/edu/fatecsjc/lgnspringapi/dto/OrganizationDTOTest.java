package br.edu.fatecsjc.lgnspringapi.dto;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class OrganizationDTOTest {

    private OrganizationDTO organizationDTO;

    @BeforeEach
    public void setUp() {
        organizationDTO = new OrganizationDTO();
    }

    @Test
    void testAllArgsConstructorAndBuilder() {
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

        assertEquals(organizationDTOFields, organizationDTOFromBuilder);

        assertEquals(true, organizationDTOFields.equals(organizationDTOFromBuilder));
        assertEquals(organizationDTOFields.hashCode(), organizationDTOFromBuilder.hashCode());
        assertNotNull(organizationDTOFields.toString());
    }

    @Test
    void testGettersAndSetters() {
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

        organizationDTO.setId(idValue);
        organizationDTO.setName(nameValue);
        organizationDTO.setCep(cepValue);
        organizationDTO.setNumber(numberValue);
        organizationDTO.setStreet(streetValue);
        organizationDTO.setCity(cityValue);
        organizationDTO.setState(stateValue);
        organizationDTO.setCountry(countryValue);
        organizationDTO.setInstituition_name(institutionNameValue);
        organizationDTO.setGroups(groupsValue);

        assertEquals(idValue, organizationDTO.getId());
        assertEquals(nameValue, organizationDTO.getName());
        assertEquals(cepValue, organizationDTO.getCep());
        assertEquals(numberValue, organizationDTO.getNumber());
        assertEquals(streetValue, organizationDTO.getStreet());
        assertEquals(cityValue, organizationDTO.getCity());
        assertEquals(stateValue, organizationDTO.getState());
        assertEquals(countryValue, organizationDTO.getCountry());
        assertEquals(institutionNameValue, organizationDTO.getInstituition_name());
        assertEquals(groupsValue, organizationDTO.getGroups());
    }

    @Test
    void testEqualsAndHashCode() {
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

        assertEquals(organizationDTOFields, organizationDTOFromBuilder);

        OrganizationDTO organizationDTO1 = new OrganizationDTO(
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

        assertEquals(true, organizationDTOFields.equals(organizationDTO1));
        assertEquals(organizationDTOFields.hashCode(), organizationDTO1.hashCode());
    }

    @Test
    void testId() {
        Long idValue = 1L;
        organizationDTO.setId(idValue);
        assertEquals(idValue, organizationDTO.getId());
    }

    @Test
    void testName() {
        String nameValue = "Test Name";
        organizationDTO.setName(nameValue);
        assertEquals(nameValue, organizationDTO.getName());
    }

    @Test
    void testCep() {
        String cepValue = "12345-678";
        organizationDTO.setCep(cepValue);
        assertEquals(cepValue, organizationDTO.getCep());
    }

    @Test
    void testNumber() {
        String numberValue = "123";
        organizationDTO.setNumber(numberValue);
        assertEquals(numberValue, organizationDTO.getNumber());
    }

    @Test
    void testStreet() {
        String streetValue = "Test Street";
        organizationDTO.setStreet(streetValue);
        assertEquals(streetValue, organizationDTO.getStreet());
    }

    @Test
    void testCity() {
        String cityValue = "Test City";
        organizationDTO.setCity(cityValue);
        assertEquals(cityValue, organizationDTO.getCity());
    }

    @Test
    void testState() {
        String stateValue = "Test State";
        organizationDTO.setState(stateValue);
        assertEquals(stateValue, organizationDTO.getState());
    }

    @Test
    void testCountry() {
        String countryValue = "Test Country";
        organizationDTO.setCountry(countryValue);
        assertEquals(countryValue, organizationDTO.getCountry());
    }

    @Test
    void testInstitutionName() {
        String institutionNameValue = "Test Institution";
        organizationDTO.setInstituition_name(institutionNameValue);
        assertEquals(institutionNameValue, organizationDTO.getInstituition_name());
    }

    @Test
    void testGroups() {
        GroupDTO groupDTO = new GroupDTO();
        List<GroupDTO> groupsValue = Arrays.asList(groupDTO);
        organizationDTO.setGroups(groupsValue);
        assertEquals(groupsValue, organizationDTO.getGroups());
    }
}