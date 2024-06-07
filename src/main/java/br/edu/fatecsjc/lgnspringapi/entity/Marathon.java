package br.edu.fatecsjc.lgnspringapi.entity;

import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
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
@Table(name = "marathons")
public class Marathon {
    @Id
    @SequenceGenerator(initialValue = 1, allocationSize = 1, name = "marathonsidgen", sequenceName = "marathons_seq")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "marathonsidgen")
    private Long id;

    private int weight;
    private int score;

    @ManyToMany(mappedBy = "marathons")
    private List<Member> members;
}