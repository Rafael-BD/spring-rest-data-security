package br.edu.fatecsjc.lgnspringapi.dto;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class OrganizationDTO {
    @Schema(hidden = true)
    private Long id;
    
    private String name;

    private String cep;
    private String number;
    private String street;
    private String city;
    private String state;
    private String country;

    private String instituition_name;

    private List<GroupDTO> groups;
}
