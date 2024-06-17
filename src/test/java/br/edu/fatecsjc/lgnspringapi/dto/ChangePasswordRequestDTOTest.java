package br.edu.fatecsjc.lgnspringapi.dto;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ChangePasswordRequestDTOTest {

    private ChangePasswordRequestDTO changePasswordRequestDTO;

    @BeforeEach
    public void setUp() {
        changePasswordRequestDTO = new ChangePasswordRequestDTO();
    }

    @Test
    void testAllArgsConstructorNoArgsConstructorAndBuilder() {
        String currentPasswordValue = "CurrentPassword";
        String newPasswordValue = "NewPassword";
        String confirmationPasswordValue = "ConfirmationPassword";

        ChangePasswordRequestDTO changePasswordRequestDTOFields = new ChangePasswordRequestDTO(
                currentPasswordValue,
                newPasswordValue,
                confirmationPasswordValue
        );
        ChangePasswordRequestDTO changePasswordRequestDTOFromBuilder = ChangePasswordRequestDTO.builder()
                .currentPassword(currentPasswordValue)
                .newPassword(newPasswordValue)
                .confirmationPassword(confirmationPasswordValue)
                .build();

        assertEquals(changePasswordRequestDTOFields, changePasswordRequestDTOFromBuilder);

        ChangePasswordRequestDTO changePasswordRequestDTONoArgs = new ChangePasswordRequestDTO();
        assertNotNull(changePasswordRequestDTONoArgs);

        assertEquals(changePasswordRequestDTOFields.equals(changePasswordRequestDTOFromBuilder), true);
        assertEquals(changePasswordRequestDTOFields.hashCode(), changePasswordRequestDTOFromBuilder.hashCode());
        assertNotNull(changePasswordRequestDTOFields.toString());
    }

    @Test
    void testGettersAndSetters() {
        String currentPasswordValue = "CurrentPasswordGetterSetter";
        String newPasswordValue = "NewPasswordGetterSetter";
        String confirmationPasswordValue = "ConfirmationPasswordGetterSetter";

        changePasswordRequestDTO.setCurrentPassword(currentPasswordValue);
        changePasswordRequestDTO.setNewPassword(newPasswordValue);
        changePasswordRequestDTO.setConfirmationPassword(confirmationPasswordValue);

        assertEquals(currentPasswordValue, changePasswordRequestDTO.getCurrentPassword());
        assertEquals(newPasswordValue, changePasswordRequestDTO.getNewPassword());
        assertEquals(confirmationPasswordValue, changePasswordRequestDTO.getConfirmationPassword());
    }

    @Test
    void testEqualsAndHashCode() {
        String currentPasswordValue = "CurrentPassword";
        String newPasswordValue = "NewPassword";
        String confirmationPasswordValue = "ConfirmationPassword";

        ChangePasswordRequestDTO changePasswordRequestDTOFields = new ChangePasswordRequestDTO(
                currentPasswordValue,
                newPasswordValue,
                confirmationPasswordValue
        );
        ChangePasswordRequestDTO changePasswordRequestDTOFromBuilder = ChangePasswordRequestDTO.builder()
                .currentPassword(currentPasswordValue)
                .newPassword(newPasswordValue)
                .confirmationPassword(confirmationPasswordValue)
                .build();

        assertEquals(changePasswordRequestDTOFields, changePasswordRequestDTOFromBuilder);
        assertEquals(changePasswordRequestDTOFields.hashCode(), changePasswordRequestDTOFromBuilder.hashCode());
    }

    @Test
    void testCurrentPassword() {
        String currentPasswordValue = "current_password";
        changePasswordRequestDTO.setCurrentPassword(currentPasswordValue);
        assertEquals(currentPasswordValue, changePasswordRequestDTO.getCurrentPassword());
    }

    @Test
    void testNewPassword() {
        String newPasswordValue = "new_password";
        changePasswordRequestDTO.setNewPassword(newPasswordValue);
        assertEquals(newPasswordValue, changePasswordRequestDTO.getNewPassword());
    }

    @Test
    void testConfirmationPassword() {
        String confirmationPasswordValue = "confirmation_password";
        changePasswordRequestDTO.setConfirmationPassword(confirmationPasswordValue);
        assertEquals(confirmationPasswordValue, changePasswordRequestDTO.getConfirmationPassword());
    }
}