package br.edu.fatecsjc.lgnspringapi.dto;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class ChangePasswordRequestDTOTest {

    private ChangePasswordRequestDTO changePasswordRequestDTO;

    @BeforeEach
    public void setUp() {
        changePasswordRequestDTO = new ChangePasswordRequestDTO();
    }

    @Test
    public void testCurrentPassword() {
        String currentPasswordValue = "current_password";
        changePasswordRequestDTO.setCurrentPassword(currentPasswordValue);
        assertEquals(currentPasswordValue, changePasswordRequestDTO.getCurrentPassword());
    }

    @Test
    public void testNewPassword() {
        String newPasswordValue = "new_password";
        changePasswordRequestDTO.setNewPassword(newPasswordValue);
        assertEquals(newPasswordValue, changePasswordRequestDTO.getNewPassword());
    }

    @Test
    public void testConfirmationPassword() {
        String confirmationPasswordValue = "confirmation_password";
        changePasswordRequestDTO.setConfirmationPassword(confirmationPasswordValue);
        assertEquals(confirmationPasswordValue, changePasswordRequestDTO.getConfirmationPassword());
    }
}