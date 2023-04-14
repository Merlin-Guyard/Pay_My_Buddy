package com.paymybuddy.pmbv1.TI;

import com.paymybuddy.pmbv1.model.User;
import com.paymybuddy.pmbv1.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
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
import static org.mockito.Mockito.verify;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;

@SpringBootTest
@AutoConfigureMockMvc
public class ContactControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Test
    @WithMockUser(username = "merlin.guyard1@test.com", password = "mdp123")
    public void testGetContacts() throws Exception {

        User contact = new User("Tom1", "Guyard1", "tom.guyard1@test.com", passwordEncoder.encode("mdp123"));
        userRepository.save(contact);

        User user = new User("Merlin1", "Guyard1", "merlin.guyard1@test.com", passwordEncoder.encode("mdp123"));
        List<User> contacts = new ArrayList<>();
        contacts.add(contact);
        user.setFriendList(contacts);
        userRepository.save(user);

        mockMvc.perform(MockMvcRequestBuilders.get("/contact"))
                .andExpect(MockMvcResultMatchers.model().attributeExists("users"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(result -> {
                    List<User> users = (List<User>) result.getModelAndView().getModel().get("users");
                    assertThat(users.get(0).getFirstName()).isEqualTo("Tom1");
                    assertThat(users.get(0).getLastName()).isEqualTo("Guyard1");
                    assertThat(users.get(0).getEmail()).isEqualTo("tom.guyard1@test.com");
                });
    }

    @Test
    @WithMockUser(username = "merlin.guyard2@test.com", password = "mdp123")
    public void testAddContacts() throws Exception {

        User contact = new User("Tom2", "Guyard2", "tom.guyard2@test.com", passwordEncoder.encode("mdp123"));
        userRepository.save(contact);
        User user = new User("Merlin2", "Guyard2", "merlin.guyard2@test.com", passwordEncoder.encode("mdp123"));
        userRepository.save(user);

        mockMvc.perform(MockMvcRequestBuilders.post("/contact/add")
                        .with(csrf())
                        .param("email", contact.getEmail()))
                .andExpect(MockMvcResultMatchers.flash().attribute("add_status", "Contact added successfully."))
                .andExpect(redirectedUrl("/contact"));

        Optional<User> oUser2Check = userRepository.findByEmail(user.getEmail());
        User user2Check = oUser2Check.get();
        assertEquals(user2Check.getFriendList().get(0).getEmail(), contact.getEmail());
    }

    @Test
    @WithMockUser(username = "merlin.guyard3@test.com", password = "mdp123")
    public void testDelContacts() throws Exception {

        User contact = new User("Tom3", "Guyard3", "tom.guyard3@test.com", passwordEncoder.encode("mdp123"));
        userRepository.save(contact);

        User user = new User("Merlin3", "Guyard3", "merlin.guyard3@test.com", passwordEncoder.encode("mdp123"));
        List<User> contacts = new ArrayList<>();
        contacts.add(contact);
        user.setFriendList(contacts);
        userRepository.save(user);

        mockMvc.perform(MockMvcRequestBuilders.get("/contact/del")
                        .with(csrf())
                        .param("userEmail", contact.getEmail()))
                .andExpect(MockMvcResultMatchers.flash().attribute("del_status", "Contact removed successfully."))
                .andExpect(redirectedUrl("/contact"));;

        assertThat(user.getFriendList().isEmpty());
    }

}
