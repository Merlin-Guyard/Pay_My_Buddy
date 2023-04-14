package com.paymybuddy.pmbv1.ControllerTest;

import com.paymybuddy.pmbv1.controller.OperationController;
import com.paymybuddy.pmbv1.controller.RegisterController;
import com.paymybuddy.pmbv1.model.Operation;
import com.paymybuddy.pmbv1.model.User;
import com.paymybuddy.pmbv1.repository.OperationRepository;
import com.paymybuddy.pmbv1.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;

@SpringBootTest
@AutoConfigureMockMvc
public class OperationControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private OperationRepository operationRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Test
    @WithMockUser(username = "merlin.guyard6@test.com", password = "mdp123")
    public void testGetTransfer() throws Exception {

        User contact = new User("Tom6", "Guyard6", "tom.guyard6@test.com", passwordEncoder.encode("mdp123"));
        userRepository.save(contact);

        User user = new User("Merlin6", "Guyard6", "merlin.guyard6@test.com", passwordEncoder.encode("mdp123"));
        List<User> contacts = new ArrayList<>();
        contacts.add(contact);
        user.setFriendList(contacts);
        userRepository.save(user);

        Operation operation = new Operation("Merlin6 Guyard6","Tom6 Guyard6","test",10);
        operationRepository.save(operation);

        mockMvc.perform(MockMvcRequestBuilders.get("/operation"))
                .andExpect(MockMvcResultMatchers.model().attributeExists("operations"))
                .andExpect(MockMvcResultMatchers.model().attributeExists("users"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(result -> {
                    List<User> users = (List<User>) result.getModelAndView().getModel().get("users");
                    assertThat(users.get(0).getFirstName()).isEqualTo("Tom6");
                    assertThat(users.get(0).getLastName()).isEqualTo("Guyard6");
                    assertThat(users.get(0).getEmail()).isEqualTo("tom.guyard6@test.com");

                    List<Operation> operations = (List<Operation>) result.getModelAndView().getModel().get("operations");
                    assertThat(operations.get(0).getSender()).isEqualTo("Merlin6 Guyard6");
                    assertThat(operations.get(0).getReceiver()).isEqualTo("Tom6 Guyard6");
                    assertThat(operations.get(0).getDescription()).isEqualTo("test");
                    assertThat(operations.get(0).getAmount()).isEqualTo(10);
                });
    }

    @Test
    @WithMockUser(username = "merlin.guyard7@test.com", password = "mdp123")
    public void testTransferMoney() throws Exception {

        User contact = new User("Tom7", "Guyard7", "tom.guyard7@test.com", passwordEncoder.encode("mdp123"));
        userRepository.save(contact);
        User user = new User("Merlin7", "Guyard7", "merlin.guyard7@test.com", passwordEncoder.encode("mdp123"));
        user.setBalance(1000);
        List<User> contacts = new ArrayList<>();
        contacts.add(contact);
        user.setFriendList(contacts);
        userRepository.save(user);

        mockMvc.perform(MockMvcRequestBuilders.post("/operation/transfer")
                        .with(csrf())
                        .param("email", contact.getEmail())
                        .param("description", "test")
                        .param("amount", String.valueOf(100)))
                .andExpect(MockMvcResultMatchers.flash().attribute("ope_status", "Money sent to your friend successfully."))
                .andExpect(redirectedUrl("/operation"));

        Optional<User> oUser2Check = userRepository.findByEmail(user.getEmail());
        User user2Check = oUser2Check.get();
        assertEquals(user2Check.getBalance(), 899.5);
        assertEquals(user2Check.getFriendList().get(0).getBalance(), 100);
    }
}
