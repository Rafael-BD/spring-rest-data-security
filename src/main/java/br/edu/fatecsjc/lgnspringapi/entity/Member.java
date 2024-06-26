package br.edu.fatecsjc.lgnspringapi.entity;

import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
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
@Table(name = "members")
public class Member {
    @Id
    @SequenceGenerator(initialValue = 1, allocationSize = 1, name = "membersidgen", sequenceName = "members_seq")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "membersidgen")
    private Long id;
    private String name;
    private Integer age;

    @ToString.Exclude
    @ManyToOne
    @JoinColumn(name="group_id", nullable=false)
    private Group group;

    @ToString.Exclude
    @ManyToMany
    @JoinTable(
        name = "member_marathon", 
        joinColumns = @JoinColumn(name = "member_id"), 
        inverseJoinColumns = @JoinColumn(name = "marathon_id"))
    private List<Marathon> marathons;
}
