package com.paymybuddy.pmbv1.service;

import com.paymybuddy.pmbv1.model.Operation;
import com.paymybuddy.pmbv1.model.User;
import com.paymybuddy.pmbv1.repository.OperationRepository;
import com.paymybuddy.pmbv1.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class OperationService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private OperationRepository operationRepository;

    @Autowired
    private MessageService messageService;

    public String send(String email, String description, int amount) {

        Optional<User> oUser = userRepository.findByEmail(SecurityContextHolder.getContext().getAuthentication().getName());
        if (oUser.isEmpty()) {
            return messageService.returnMessage("err.unknown_user");
        }
        User user = oUser.get();

        Optional<User> oContact = userRepository.findByEmail(email);
        if (oContact.isEmpty()) {
            return messageService.returnMessage("err.unknown_contact");
        }
        User contact = oContact.get();

        if (user.getUserId() == contact.getUserId()) {
            return messageService.returnMessage("err.send_to_self");
        }

        boolean isAlreadyFriend = user.getFriendList().stream().anyMatch(friend -> friend.getEmail().equals(contact.getEmail()));
        if (!isAlreadyFriend) {
            return messageService.returnMessage("err.not_friend_contact");
        }

        if (amount < 0.00) {
            return messageService.returnMessage("err.operation");
        }

        BigDecimal bd = new BigDecimal(amount * 0.005).setScale(2, RoundingMode.HALF_DOWN);
        double commission = bd.doubleValue();

        if (user.getBalance() - amount - commission < 0.00) {
            return messageService.returnMessage("err.insufficient_funds");
        }

        if (user.getBalance() - amount < 1.00) {
            return messageService.returnMessage("err.minimum");
        }

        Operation operation = new Operation(user.getFirstName() + ' ' + user.getLastName(),contact.getFirstName() + ' ' + contact.getLastName(), description, amount);

        user.setBalance(user.getBalance() - amount - commission);
        contact.setBalance(contact.getBalance() + amount);
        System.out.println("The commission for this transaction was :" + commission);
        operationRepository.save(operation);
        userRepository.save(user);
        userRepository.save(contact);

        return messageService.returnMessage("stat.transfer");
    }

    public String manage(String type, int amount) throws Throwable {

        Optional<User> oUser = userRepository.findByEmail(SecurityContextHolder.getContext().getAuthentication().getName());
        if (oUser.isEmpty()) {
            throw new Throwable(messageService.returnMessage("err.unknown_user"));
        }
        User user = oUser.get();

        if (amount > user.getBalance()) {
            throw new Throwable(messageService.returnMessage("err.insufficient_funds"));
        }

        if (amount < 0.00) {
            throw new Throwable(messageService.returnMessage("err.operation"));
        }

        Operation operation = new Operation(user.getFirstName() + ' ' + user.getLastName(),user.getFirstName() + ' ' + user.getLastName(), type, amount);

        if (type.equals("depot")) {
            user.setBalance(user.getBalance() + amount);
        }
        else {
            user.setBalance(user.getBalance() - amount);
        }

        operationRepository.save(operation);
        userRepository.save(user);
        return messageService.returnMessage("stat.transfer");
    }

    public List<Operation> getOperations() throws Throwable {
        Optional<User> oUser = userRepository.findByEmail(SecurityContextHolder.getContext().getAuthentication().getName());
        if (oUser.isEmpty()) {
            throw new Throwable(messageService.returnMessage("err.unknown_user"));
        }
        User user = oUser.get();
        
        Iterable<Operation> iOperations = operationRepository.findAll();
//        if (oOperations.()) {
//            throw new Throwable(messageService.returnMessage("err.unknown_user"));
//        }

        List<Operation> operations = new ArrayList<>();
        iOperations.forEach(operations::add);

        List<Operation> operations2Send = new ArrayList<>();

        for (Operation operation: operations) {
            if(operation.getSender().equals(user.getFirstName() + ' ' + user.getLastName())){
                operations2Send.add(operation);
            }
        }

        return operations2Send;
    }
}
