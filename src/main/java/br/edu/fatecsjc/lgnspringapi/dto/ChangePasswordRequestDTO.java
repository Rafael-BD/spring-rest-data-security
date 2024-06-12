package br.edu.fatecsjc.lgnspringapi.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@EqualsAndHashCode
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ChangePasswordRequestDTO {

    private String currentPassword;
    private String newPassword;
    private String confirmationPassword;

}
