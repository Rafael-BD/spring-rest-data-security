package br.edu.fatecsjc.lgnspringapi.entity;
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
public class Address {
    private String cep;
    private String number;
    private String street;
    private String city;
    private String state;
    private String country;
}