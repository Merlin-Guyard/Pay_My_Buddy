package com.paymybuddy.pmbv1.ServiceTest;

import com.paymybuddy.pmbv1.model.User;
import com.paymybuddy.pmbv1.repository.UserRepository;
import com.paymybuddy.pmbv1.service.MessageService;
import com.paymybuddy.pmbv1.service.UserService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.platform.commons.util.RuntimeUtils;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private MessageService messageService;

    @InjectMocks
    private UserService userService;

    @Test
    public void testAddUserDuplicate() throws RuntimeException {
        // create a new user
        User user = new User();
        user.setEmail("test@example.com");

        // mock an existing user
        List<User> users = new ArrayList<>();
        User existingUser = new User();
        existingUser.setEmail("test@example.com");
        users.add(existingUser);
        Mockito.when(userRepository.findAll()).thenReturn(users);

        // mock the error message
        String errorMessage = "This user already exists.";
        Mockito.when(messageService.returnMessage("err.duplicate_user")).thenReturn(errorMessage);

        // add user expecting to fail and check if message is correct

         RuntimeException result = assertThrows(RuntimeException.class, () -> userService.addUser(user));
         assertEquals(errorMessage, result.getMessage());
    }

    @Test
    public void testAddUser() throws Throwable {
        // create a new user
        User user = new User();
        user.setEmail("test@example.com");

        // mock an empty database
        when(userRepository.findAll()).thenReturn(new ArrayList<>());

        // mock the status message
        String statusMessage = "Registration was successful.";
        Mockito.when(messageService.returnMessage("stat.register")).thenReturn(statusMessage);

        // add user and get back status
        String result = userService.addUser(user);

        // check if message is correct
        assertEquals("Registration was successful.", result);
    }

    @Test
    void getUserByEmailTest() {
        // mock the security context holder
        SecurityContext securityContext = Mockito.mock(SecurityContext.class);
        Authentication authentication = Mockito.mock(Authentication.class);
        when(authentication.getName()).thenReturn("test@example.com");
        when(securityContext.getAuthentication()).thenReturn(authentication);
        SecurityContextHolder.setContext(securityContext);

        // mock an existing user
        User testUser = new User("test@example.com", "password", "test@example.com", "User");
        when(userRepository.findByEmail("test@example.com")).thenReturn(Optional.of(testUser));

        // check if users are matching
        User returnedUser = userService.getUserByEmail();
        assertEquals(testUser, returnedUser);
    }
}