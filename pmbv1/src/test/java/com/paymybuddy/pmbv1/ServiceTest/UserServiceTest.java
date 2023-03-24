package com.paymybuddy.pmbv1.ServiceTest;

import com.paymybuddy.pmbv1.model.User;
import com.paymybuddy.pmbv1.repository.UserRepository;
import com.paymybuddy.pmbv1.service.MessageService;
import com.paymybuddy.pmbv1.service.UserService;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@SpringBootTest
public class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private MessageService messageService;

    @InjectMocks
    private UserService userService;

    @Test
    public void testAddUserDuplicate() throws Throwable {
        // create a new user
        User user = new User();
        user.setEmail("test@test.com");

        // mock an existing user
        List<User> users = new ArrayList<>();
        User existingUser = new User();
        existingUser.setEmail("test@test.com");
        users.add(existingUser);
        Mockito.when(userRepository.findAll()).thenReturn(users);

        // mock the error message
        String errorMessage = "This user already exists.";
        Mockito.when(messageService.returnMessage("err.duplicate_user")).thenReturn(errorMessage);

        // add user expecting to fail and check if message is correct
        try {
            userService.addUser(user);
        } catch (Throwable e) {
            assertEquals(errorMessage, e.getMessage());
        }
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
        // mock the security context to return a test authentication object
        SecurityContext securityContext = Mockito.mock(SecurityContext.class);
        Authentication authentication = Mockito.mock(Authentication.class);
        when(authentication.getName()).thenReturn("test@example.com");
        when(securityContext.getAuthentication()).thenReturn(authentication);
        SecurityContextHolder.setContext(securityContext);

        // mock the repository to return a test user object with the same email
        User testUser = new User("test@example.com", "password", "Test", "User");
        when(userRepository.findByEmail("test@example.com")).thenReturn(Optional.of(testUser));

        // assert that the getUserByEmail method returns the test user object
        User returnedUser = userService.getUserByEmail();
        assertEquals(testUser, returnedUser);
    }
}