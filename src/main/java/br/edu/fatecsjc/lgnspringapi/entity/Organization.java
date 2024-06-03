package br.edu.fatecsjc.lgnspringapi.entity;
import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "organizations")
public class Organization {
    @Id
    @SequenceGenerator(initialValue = 1, allocationSize = 1, name = "organizationsidgen", sequenceName = "organizations_seq")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "organizationsidgen")
    private Long id;

    private String name;
    
    private String cep;
    private String number;
    private String street;
    private String city;
    private String state;
    private String country;

    private String instituition_name;

    @OneToMany(mappedBy="organization", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @Builder.Default
    private List<Group> groups = new ArrayList<>();
}






