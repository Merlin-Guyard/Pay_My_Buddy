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
import java.util.List;
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

        when(schService.getName()).thenReturn(currentUser.getEmail());
        when(userRepository.findByEmail(currentUser.getEmail())).thenReturn(Optional.empty());
        when(messageService.returnMessage("err.unknown_user")).thenReturn("This user doesn't exist.");

        String errorMessage = "This user doesn't exist.";
        Mockito.when(messageService.returnMessage("err.unknown_user")).thenReturn(errorMessage);


        try {
            //Act
            contactService.addContact(contactUser.getEmail());
        } catch (RuntimeException e) {
            //Assert
            assertEquals("This user doesn't exist.", e.getMessage());
        }
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

        when(schService.getName()).thenReturn(currentUser.getEmail());
        when(userRepository.findByEmail(currentUser.getEmail())).thenReturn(Optional.of(currentUser));
        when(userRepository.findByEmail(contactUser.getEmail())).thenReturn(Optional.empty());
        when(messageService.returnMessage("err.unknown_contact")).thenReturn("This contact doesn't exist.");

        String errorMessage = "This contact doesn't exist.";
        Mockito.when(messageService.returnMessage("err.unknown_contact")).thenReturn(errorMessage);

        try {
            //Act
            contactService.addContact(contactUser.getEmail());
        } catch (RuntimeException e) {
            //Assert
            assertEquals("This contact doesn't exist.", e.getMessage());
        }
    }

    @Test
    public void testContactIsUser() {
        // Arrange
        User contactUser = new User();
        contactUser.setEmail("contact@example.com");
        contactUser.setUserId(0);
        contactUser.setFriendList(new ArrayList<User>());

        User currentUser = new User();
        currentUser.setEmail("user@example.com");
        currentUser.setUserId(0);
        List<User> friendlist = new ArrayList<>();
        friendlist.add(contactUser);
        currentUser.setFriendList(friendlist);

        when(schService.getName()).thenReturn(currentUser.getEmail());
        when(userRepository.findByEmail(currentUser.getEmail())).thenReturn(Optional.of(currentUser));
        when(userRepository.findByEmail(contactUser.getEmail())).thenReturn(Optional.of(contactUser));
        when(messageService.returnMessage("err.contact_is_user")).thenReturn("You cannot add yourself.");

        String errorMessage = "You cannot add yourself.";
        Mockito.when(messageService.returnMessage("err.contact_is_user")).thenReturn(errorMessage);

        try {
            //Act
            contactService.addContact(contactUser.getEmail());
        } catch (RuntimeException e) {
            //Assert
            assertEquals("You cannot add yourself.", e.getMessage());
        }
    }
    @Test
    public void testAddContactAlreadyFriend() {
        // Arrange
        User contactUser = new User();
        contactUser.setEmail("contact@example.com");
        contactUser.setUserId(1);
        contactUser.setFriendList(new ArrayList<User>());

        User currentUser = new User();
        currentUser.setEmail("user@example.com");
        currentUser.setUserId(0);
        List<User> friendlist = new ArrayList<>();
        friendlist.add(contactUser);
        currentUser.setFriendList(friendlist);

        when(schService.getName()).thenReturn(currentUser.getEmail());
        when(userRepository.findByEmail(currentUser.getEmail())).thenReturn(Optional.of(currentUser));
        when(userRepository.findByEmail(contactUser.getEmail())).thenReturn(Optional.of(contactUser));
        when(messageService.returnMessage("err.duplicate_contact")).thenReturn("This contact is already added.");

        String errorMessage = "This contact is already added.";
        Mockito.when(messageService.returnMessage("err.duplicate_contact")).thenReturn(errorMessage);

        try {
            //Act
            contactService.addContact(contactUser.getEmail());
        } catch (RuntimeException e) {
            //Assert
            assertEquals("This contact is already added.", e.getMessage());
        }
    }

    @Test
    public void testRemoveContact() {
        // Arrange
        User contactUser = new User();
        contactUser.setEmail("contact@example.com");
        contactUser.setUserId(1);
        contactUser.setFriendList(new ArrayList<User>());

        User currentUser = new User();
        currentUser.setEmail("user@example.com");
        currentUser.setUserId(0);
        List<User> friendlist = new ArrayList<>();
        friendlist.add(contactUser);
        currentUser.setFriendList(friendlist);

        String errorMessage = "Contact removed successfully.";

        when(schService.getName()).thenReturn(currentUser.getEmail());
        when(userRepository.findByEmail(currentUser.getEmail())).thenReturn(Optional.of(currentUser));
        when(userRepository.findByEmail(contactUser.getEmail())).thenReturn(Optional.of(contactUser));
        when(messageService.returnMessage("stat.del_contact")).thenReturn(errorMessage);


        Mockito.when(messageService.returnMessage("stat.del_contact")).thenReturn(errorMessage);

        try {
            //Act
            contactService.removeContact(contactUser.getEmail());
        } catch (RuntimeException e) {
            //Assert
            assertEquals(errorMessage, e.getMessage());
        }
    }

    @Test
    public void testRemoveContactNotFriend() {
        // Arrange
        User contactUser = new User();
        contactUser.setEmail("contact@example.com");
        contactUser.setUserId(1);
        contactUser.setFriendList(new ArrayList<User>());

        User currentUser = new User();
        currentUser.setEmail("user@example.com");
        currentUser.setUserId(0);

        String errorMessage = "This contact isn't in your friend list.";

        when(schService.getName()).thenReturn(currentUser.getEmail());
        when(userRepository.findByEmail(currentUser.getEmail())).thenReturn(Optional.of(currentUser));
        when(userRepository.findByEmail(contactUser.getEmail())).thenReturn(Optional.of(contactUser));
        when(messageService.returnMessage("err.not_friend_contact")).thenReturn(errorMessage);


        Mockito.when(messageService.returnMessage("err.not_friend_contact")).thenReturn(errorMessage);

        try {
            //Act
            contactService.removeContact(contactUser.getEmail());
        } catch (RuntimeException e) {
            //Assert
            assertEquals(errorMessage, e.getMessage());
        }
    }

    @Test
    public void testRemoveContactUnknownUser() {
        // Arrange
        User contactUser = new User();
        contactUser.setEmail("contact@example.com");
        contactUser.setUserId(1);
        contactUser.setFriendList(new ArrayList<User>());

        User currentUser = new User();
        currentUser.setEmail("user@example.com");
        currentUser.setUserId(0);

        String errorMessage = "This user doesn't exist.";

        when(schService.getName()).thenReturn(currentUser.getEmail());
        when(userRepository.findByEmail(currentUser.getEmail())).thenReturn(Optional.empty());


        Mockito.when(messageService.returnMessage("err.unknown_user")).thenReturn(errorMessage);

        try {
            //Act
            contactService.removeContact(contactUser.getEmail());
        } catch (RuntimeException e) {
            //Assert
            assertEquals(errorMessage, e.getMessage());
        }
    }

    @Test
    public void testRemoveContactUnknownContact() {
        // Arrange
        User contactUser = new User();
        contactUser.setEmail("contact@example.com");
        contactUser.setUserId(1);
        contactUser.setFriendList(new ArrayList<User>());

        User currentUser = new User();
        currentUser.setEmail("user@example.com");
        currentUser.setUserId(0);

        String errorMessage = "This contact doesn't exist.";

        when(schService.getName()).thenReturn(currentUser.getEmail());
        when(userRepository.findByEmail(currentUser.getEmail())).thenReturn(Optional.of(currentUser));
        when(userRepository.findByEmail(contactUser.getEmail())).thenReturn(Optional.empty());


        Mockito.when(messageService.returnMessage("err.unknown_contact")).thenReturn(errorMessage);

        try {
            //Act
            contactService.removeContact(contactUser.getEmail());
        } catch (RuntimeException e) {
            //Assert
            assertEquals(errorMessage, e.getMessage());
        }
    }
}
