package com.paymybuddy.pmbv1.service;

import com.paymybuddy.pmbv1.model.User;
import com.paymybuddy.pmbv1.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Optional;

@Service
public class OperationService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private MessageService messageService;

    public String send(String email, int amount) {

        Optional<User> oUser = userRepository.findByEmail(SecurityContextHolder.getContext().getAuthentication().getName());
        if(oUser.isEmpty()) {
            return messageService.returnMessage("err.unknown_user");
        }
        User user = oUser.get();

        Optional<User> oContact = userRepository.findByEmail(email);
        if(oContact.isEmpty()) {
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

        if (amount<0.00) {
            return messageService.returnMessage("err.operation");
        }

        BigDecimal bd = new BigDecimal(amount*0.005).setScale(2, RoundingMode.HALF_DOWN);
        double commission = bd.doubleValue();

        if (user.getBalance()-amount-commission<0.00) {
            return messageService.returnMessage("err.insufficient_funds");
        }

        if (user.getBalance()-amount<1.00) {
            return messageService.returnMessage("err.minimum");
        }

        user.setBalance(user.getBalance()-amount-commission);
        contact.setBalance(contact.getBalance()+amount);
        System.out.println("The commission for this transaction was :"+commission);
        userRepository.save(user);
        userRepository.save(contact);

        return messageService.returnMessage("stat.transfer");
    }

    public void manage(String operation, int amount) {
    }
}
