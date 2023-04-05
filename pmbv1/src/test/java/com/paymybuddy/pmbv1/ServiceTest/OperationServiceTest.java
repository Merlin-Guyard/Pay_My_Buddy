package com.paymybuddy.pmbv1.ServiceTest;

import com.paymybuddy.pmbv1.model.User;
import com.paymybuddy.pmbv1.repository.OperationRepository;
import com.paymybuddy.pmbv1.repository.UserRepository;
import com.paymybuddy.pmbv1.service.ContactService;
import com.paymybuddy.pmbv1.service.MessageService;
import com.paymybuddy.pmbv1.service.OperationService;
import com.paymybuddy.pmbv1.service.SCHService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class OperationServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private OperationRepository operationRepository;

    @Mock
    private MessageService messageService;

    @Mock
    private SCHService schService;

    @InjectMocks
    private OperationService operationService;

    @Test
    public void testSend() {
        User user = new User("Bobby", "Dupont", "Bobby.Dupont@example.com", "password");
        user.setUserId(0);
        User contact = new User("Sarah", "Connor", "Sarah.Connor@example.com", "password");
        contact.setUserId(1);
        List<User> friendList = new ArrayList<>();
        friendList.add(contact);
        user.setFriendList(friendList);
        user.setBalance(1000);
        contact.setBalance(0);


        when(schService.getName()).thenReturn(user.getEmail());
        when(userRepository.findByEmail(user.getEmail())).thenReturn(Optional.of(user));
        when(userRepository.findByEmail(contact.getEmail())).thenReturn(Optional.of(contact));
        when(messageService.returnMessage("stat.transfer")).thenReturn("Transfer successful");

        String result = operationService.send(contact.getEmail(), "Test transfer", 750);

        Assertions.assertEquals("Transfer successful", result);
        Assertions.assertEquals(BigDecimal.valueOf(1000 - 750 - 750 * 0.005).setScale(2, RoundingMode.HALF_DOWN).doubleValue(), user.getBalance());
        Assertions.assertEquals(750, contact.getBalance());
    }

    @Test
    public void testSendUnknownUser() {
        User user = new User("Bobby", "Dupont", "Bobby.Dupont@example.com", "password");
        user.setUserId(0);
        User contact = new User("Sarah", "Connor", "Sarah.Connor@example.com", "password");
        contact.setUserId(1);
        List<User> friendList = new ArrayList<>();
        friendList.add(contact);
        user.setFriendList(friendList);
        user.setBalance(1000);
        contact.setBalance(0);


        when(schService.getName()).thenReturn(user.getEmail());
        when(userRepository.findByEmail(user.getEmail())).thenReturn(Optional.empty());
        String errorMessage = "This user doesn't exist.";
        Mockito.when(messageService.returnMessage("err.unknown_user")).thenReturn(errorMessage);

        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            operationService.send(contact.getEmail(), "Test operation", 750);
        });
        Assertions.assertEquals("This user doesn't exist.", exception.getMessage());
    }

    @Test
    public void testSendUnknownContact() {
        User user = new User("Bobby", "Dupont", "Bobby.Dupont@example.com", "password");
        user.setUserId(0);
        User contact = new User("Sarah", "Connor", "Sarah.Connor@example.com", "password");
        contact.setUserId(1);
        List<User> friendList = new ArrayList<>();
        friendList.add(contact);
        user.setFriendList(friendList);
        user.setBalance(1000);
        contact.setBalance(0);

        when(schService.getName()).thenReturn(user.getEmail());
        when(userRepository.findByEmail(user.getEmail())).thenReturn(Optional.of(user));
        when(userRepository.findByEmail(contact.getEmail())).thenReturn(Optional.empty());
        String errorMessage = "This contact doesn't exist.";
        Mockito.when(messageService.returnMessage("err.unknown_contact")).thenReturn(errorMessage);

        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            operationService.send(contact.getEmail(), "Test operation", 750);
        });
        Assertions.assertEquals("This contact doesn't exist.", exception.getMessage());
    }

    @Test
    public void testSend2Self() {
        User user = new User("Bobby", "Dupont", "Bobby.Dupont@example.com", "password");
        user.setUserId(0);
        User contact = new User("Sarah", "Connor", "Sarah.Connor@example.com", "password");
        contact.setUserId(1);
        List<User> friendList = new ArrayList<>();
        friendList.add(contact);
        user.setFriendList(friendList);
        user.setBalance(1000);
        contact.setBalance(0);


        when(schService.getName()).thenReturn(user.getEmail());
        when(userRepository.findByEmail(user.getEmail())).thenReturn(Optional.of(user));
        when(userRepository.findByEmail(contact.getEmail())).thenReturn(Optional.of(user));
        String errorMessage = "You cannot send money to yourself.";
        Mockito.when(messageService.returnMessage("err.send_to_self")).thenReturn(errorMessage);

        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            operationService.send(contact.getEmail(), "Test operation", 750);
        });
        Assertions.assertEquals("You cannot send money to yourself.", exception.getMessage());
    }

    @Test
    public void testSendNotFriend() {
        User user = new User("Bobby", "Dupont", "Bobby.Dupont@example.com", "password");
        user.setUserId(0);
        User contact = new User("Sarah", "Connor", "Sarah.Connor@example.com", "password");
        contact.setUserId(1);
        List<User> friendList = new ArrayList<>();
        user.setFriendList(friendList);
        user.setBalance(1000);
        contact.setBalance(0);


        when(schService.getName()).thenReturn(user.getEmail());
        when(userRepository.findByEmail(user.getEmail())).thenReturn(Optional.of(user));
        when(userRepository.findByEmail(contact.getEmail())).thenReturn(Optional.of(contact));
        String errorMessage = "This contact isn't in your friend list.";
        Mockito.when(messageService.returnMessage("err.not_friend_contact")).thenReturn(errorMessage);

        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            operationService.send(contact.getEmail(), "Test operation", 750);
        });
        Assertions.assertEquals("This contact isn't in your friend list.", exception.getMessage());
    }

    @Test
    public void testSendBadAmount() {
        User user = new User("Bobby", "Dupont", "Bobby.Dupont@example.com", "password");
        user.setUserId(0);
        User contact = new User("Sarah", "Connor", "Sarah.Connor@example.com", "password");
        contact.setUserId(1);
        List<User> friendList = new ArrayList<>();
        friendList.add(contact);
        user.setFriendList(friendList);
        user.setBalance(1000);
        contact.setBalance(0);


        when(schService.getName()).thenReturn(user.getEmail());
        when(userRepository.findByEmail(user.getEmail())).thenReturn(Optional.of(user));
        when(userRepository.findByEmail(contact.getEmail())).thenReturn(Optional.of(contact));
        String errorMessage = "Amount is not admissible.";
        Mockito.when(messageService.returnMessage("err.operation")).thenReturn(errorMessage);

        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            operationService.send(contact.getEmail(), "Test operation", -5);
        });
        Assertions.assertEquals("Amount is not admissible.", exception.getMessage());
    }

    @Test
    public void testSendNotEnoughMoney() {
        User user = new User("Bobby", "Dupont", "Bobby.Dupont@example.com", "password");
        user.setUserId(0);
        User contact = new User("Sarah", "Connor", "Sarah.Connor@example.com", "password");
        contact.setUserId(1);
        List<User> friendList = new ArrayList<>();
        friendList.add(contact);
        user.setFriendList(friendList);
        user.setBalance(1000);
        contact.setBalance(0);


        when(schService.getName()).thenReturn(user.getEmail());
        when(userRepository.findByEmail(user.getEmail())).thenReturn(Optional.of(user));
        when(userRepository.findByEmail(contact.getEmail())).thenReturn(Optional.of(contact));
        String errorMessage = "You don't have enough money for this transfer.";
        Mockito.when(messageService.returnMessage("err.insufficient_funds")).thenReturn(errorMessage);

        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            operationService.send(contact.getEmail(), "Test operation", 1500);
        });
        Assertions.assertEquals("You don't have enough money for this transfer.", exception.getMessage());
    }

}
