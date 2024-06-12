package br.edu.fatecsjc.lgnspringapi.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

import java.time.Instant;

import lombok.EqualsAndHashCode;
import lombok.Setter;
import lombok.Getter;

@Getter
@Setter
@EqualsAndHashCode
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ApiErrorDTO {
    private String message;
    private Instant timestamp;
}
