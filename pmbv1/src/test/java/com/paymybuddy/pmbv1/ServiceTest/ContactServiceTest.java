package com.paymybuddy.pmbv1.ServiceTest;

import com.paymybuddy.pmbv1.model.User;
import com.paymybuddy.pmbv1.repository.UserRepository;
import com.paymybuddy.pmbv1.service.ContactService;
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
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ContactServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private MessageService messageService;

    @Mock
    private SCHService schService;

    @InjectMocks
    private ContactService contactService;

    @Test
    public void testAddContact() throws RuntimeException {
        User currentUser = new User();
        currentUser.setEmail("user@example.com");
        currentUser.setUserId(0);
        currentUser.setFriendList(new ArrayList<User>());

        User contactUser = new User();
        contactUser.setEmail("contact@example.com");
        contactUser.setUserId(1);
        contactUser.setFriendList(new ArrayList<User>());

        when(schService.getName()).thenReturn(currentUser.getEmail());
        when(userRepository.findByEmail(currentUser.getEmail())).thenReturn(Optional.of(currentUser));
        when(userRepository.findByEmail(contactUser.getEmail())).thenReturn(Optional.of(contactUser));
        when(messageService.returnMessage("stat.add_contact")).thenReturn("Contact added successfully");

        // Act
        String result = contactService.addContact(contactUser.getEmail());

        // Assert
        assertEquals("Contact added successfully", result);
        assertEquals(1, currentUser.getFriendList().size());
        assertEquals(contactUser.getEmail(), currentUser.getFriendList().get(0).getEmail());
    }

    @Test
    public void testAddContactUnknownUser() {
        // Arrange
        User currentUser = new User();
        currentUser.setEmail("user@example.com");
        currentUser.setUserId(0);
        currentUser.setFriendList(new ArrayList<User>());

        User contactUser = new User();
        contactUser.setEmail("contact@example.com");
        contactUser.setUserId(1);
        contactUser.setFriendList(new ArrayList<User>());

        User unknownUser = new User();
        contactUser.setEmail("unknown@example.com");
        contactUser.setUserId(1);
        contactUser.setFriendList(new ArrayList<User>());

        when(schService.getName()).thenReturn(unknownUser.getEmail());
        when(userRepository.findByEmail(currentUser.getEmail())).thenReturn(Optional.of(unknownUser));
        when(userRepository.findByEmail(contactUser.getEmail())).thenReturn(Optional.of(contactUser));
        when(messageService.returnMessage("err.unknown_user")).thenReturn("Unknown user");

        String errorMessage = "Unknown user";
        Mockito.when(messageService.returnMessage("err.unknown_user")).thenReturn(errorMessage);

        assertThrows(RuntimeException.class, () -> {
            contactService.addContact(contactUser.getEmail());
        }, "Unknown user");
    }

    @Test
    public void testAddContactUnknownContact() {
        // Arrange
        User currentUser = new User();
        currentUser.setEmail("user@example.com");
        currentUser.setUserId(0);
        currentUser.setFriendList(new ArrayList<User>());

        User contactUser = new User();
        contactUser.setEmail("contact@example.com");
        contactUser.setUserId(1);
        contactUser.setFriendList(new ArrayList<User>());

        User unknownUser = new User();
        contactUser.setEmail("unknown@example.com");
        contactUser.setUserId(1);
        contactUser.setFriendList(new ArrayList<User>());

        when(schService.getName()).thenReturn(currentUser.getEmail());
        when(userRepository.findByEmail(currentUser.getEmail())).thenReturn(Optional.of(currentUser));
        when(userRepository.findByEmail(contactUser.getEmail())).thenReturn(Optional.of(unknownUser));
        when(messageService.returnMessage("err.unknown_user")).thenReturn("Unknown user");

        String errorMessage = "Unknown user";
        Mockito.when(messageService.returnMessage("err.unknown_user")).thenReturn(errorMessage);

        assertThrows(RuntimeException.class, () -> {
            contactService.addContact(contactUser.getEmail());
        }, "Unknown user");
    }

    @Test
    public void testAddContactDuplicateContact() {
        // Arrange
        User currentUser = new User();
        currentUser.setEmail("user@example.com");
        currentUser.setUserId(0);
        currentUser.setFriendList(new ArrayList<User>());

        User contactUser = new User();
        contactUser.setEmail("contact@example.com");
        contactUser.setUserId(1);
        contactUser.setFriendList(new ArrayList<User>());

        when(schService.getName()).thenReturn(currentUser.getEmail());
        when(userRepository.findByEmail(currentUser.getEmail())).thenReturn(Optional.of(currentUser));
        when(userRepository.findByEmail(contactUser.getEmail())).thenReturn(Optional.of(contactUser));
        when(messageService.returnMessage("err.unknown_user")).thenReturn("Unknown user");

        when(schService.getName()).thenReturn(currentUser.getEmail());
        when(userRepository.findByEmail(currentUser.getEmail())).thenReturn(Optional.of(currentUser));
        when(userRepository.findByEmail(contactUser.getEmail())).thenReturn(Optional.of(contactUser));
        when(messageService.returnMessage("err.duplicate_contact")).thenReturn("Duplicate contact");

        String errorMessage = "Duplicate contact";
        Mockito.when(messageService.returnMessage("err.duplicate_contact")).thenReturn(errorMessage);

        assertThrows(RuntimeException.class, () -> {
            contactService.addContact(contactUser.getEmail());
        }, "Duplicate contact");
    }
}
