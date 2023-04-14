package com.paymybuddy.pmbv1.TU;

import com.paymybuddy.pmbv1.model.User;
import com.paymybuddy.pmbv1.repository.UserRepository;
import com.paymybuddy.pmbv1.service.MessageService;
import com.paymybuddy.pmbv1.service.SCHService;
import com.paymybuddy.pmbv1.service.UserService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

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

    @Mock
    private SCHService schService;

    @InjectMocks
    private UserService userService;

    @Test
    public void testAddUserDuplicate() throws RuntimeException {
        //Arrange
        User user = new User();
        user.setEmail("test@example.com");

        List<User> users = new ArrayList<>();
        User existingUser = new User();
        existingUser.setEmail("test@example.com");
        users.add(existingUser);
        Mockito.when(userRepository.findAll()).thenReturn(users);

        String errorMessage = "This user already exists.";
        Mockito.when(messageService.returnMessage("err.duplicate_user")).thenReturn(errorMessage);

        //Act
        RuntimeException result = assertThrows(RuntimeException.class, () -> userService.addUser(user));
        //Assert
        assertEquals(errorMessage, result.getMessage());
    }

    @Test
    public void testAddUser() throws RuntimeException {
        //Arrange
        User user = new User();
        user.setEmail("test@example.com");

        when(userRepository.findAll()).thenReturn(new ArrayList<>());

        String statusMessage = "Registration was successful.";
        Mockito.when(messageService.returnMessage("stat.register")).thenReturn(statusMessage);

        //Act
        String result = userService.addUser(user);

        //Assert
        assertEquals("Registration was successful.", result);
    }

    @Test
    void getUserByEmailTest() {
        //Arrange
        when(schService.getName()).thenReturn("test@example.com");

        User testUser = new User("test@example.com", "password", "test@example.com", "User");
        when(userRepository.findByEmail("test@example.com")).thenReturn(Optional.of(testUser));

        //Act
        User returnedUser = userService.getUserByEmail();

        //Assert
        assertEquals(testUser, returnedUser);
    }
}