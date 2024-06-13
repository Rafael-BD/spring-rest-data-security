package br.edu.fatecsjc.lgnspringapi.service;

import java.security.Principal;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import static org.mockito.ArgumentMatchers.any;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;

import br.edu.fatecsjc.lgnspringapi.dto.ChangePasswordRequestDTO;
import br.edu.fatecsjc.lgnspringapi.entity.User;
import br.edu.fatecsjc.lgnspringapi.repository.UserRepository;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

    @InjectMocks
    private UserService userService;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private UserRepository userRepository;

    @Test
    public void testChangePassword() {
        ChangePasswordRequestDTO request = new ChangePasswordRequestDTO("oldPassword", "newPassword", "newPassword");
        User user = new User();
        user.setPassword("oldPasswordEncoded");
        Principal principal = new UsernamePasswordAuthenticationToken(user, null);
        when(passwordEncoder.matches(any(String.class), any(String.class))).thenReturn(true);
        when(passwordEncoder.encode(any(String.class))).thenReturn("newPasswordEncoded");
        userService.changePassword(request, principal);
        verify(userRepository).save(user);
    }

    @Test
    public void testChangePasswordWithWrongCurrentPassword() {
        ChangePasswordRequestDTO request = new ChangePasswordRequestDTO("wrongOldPassword", "newPassword", "newPassword");
        User user = new User();
        user.setPassword("oldPasswordEncoded");
        Principal principal = new UsernamePasswordAuthenticationToken(user, null);
        when(passwordEncoder.matches(any(String.class), any(String.class))).thenReturn(false);

        Exception exception = assertThrows(IllegalStateException.class, () -> userService.changePassword(request, principal));
        assertEquals("Wrong password", exception.getMessage());
    }

    @Test
    public void testChangePasswordWithNonMatchingNewPasswords() {
        ChangePasswordRequestDTO request = new ChangePasswordRequestDTO("oldPassword", "newPassword", "differentNewPassword");
        User user = new User();
        user.setPassword("oldPasswordEncoded");
        Principal principal = new UsernamePasswordAuthenticationToken(user, null);
        when(passwordEncoder.matches(any(String.class), any(String.class))).thenReturn(true);

        Exception exception = assertThrows(IllegalStateException.class, () -> userService.changePassword(request, principal));
        assertEquals("Password are not the same", exception.getMessage());
    }
}