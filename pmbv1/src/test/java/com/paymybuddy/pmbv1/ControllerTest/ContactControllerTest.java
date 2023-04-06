package com.paymybuddy.pmbv1.ControllerTest;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.paymybuddy.pmbv1.model.User;
import com.paymybuddy.pmbv1.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@SpringBootTest
@AutoConfigureMockMvc
public class ContactControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Test
    @WithMockUser(username = "merlin.guyard@test.com", password = "mdp123")
    public void testGetContacts() throws Exception {

        User contact = new User("Tom", "Guyard", "tom.guyard@test.com", passwordEncoder.encode("mdp123"));
        userRepository.save(contact);

        User user = new User("Merlin", "Guyard", "merlin.guyard@test.com", passwordEncoder.encode("mdp123"));
        List<User> contacts = new ArrayList<>();
        contacts.add(contact);
        user.setFriendList(contacts);
        userRepository.save(user);

        mockMvc.perform(MockMvcRequestBuilders.get("/contact")
                        .with(SecurityMockMvcRequestPostProcessors.user("merlin.guyard@test.com").password("test123")))
                .andExpect(MockMvcResultMatchers.model().attributeExists("users"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(result -> {
                    List<User> users = (List<User>) result.getModelAndView().getModel().get("users");
                    assertThat(users.get(0).getFirstName()).isEqualTo("Tom");
                    assertThat(users.get(0).getLastName()).isEqualTo("Guyard");
                    assertThat(users.get(0).getEmail()).isEqualTo("tom.guyard@test.com");
                });
    }

    @Test
    @WithMockUser(username = "merlin.guyard@test.com", password = "mdp123")
    public void testAddContacts() throws Exception {

        User contact = new User("Tom", "Guyard", "tom.guyard@test.com", passwordEncoder.encode("mdp123"));
        userRepository.save(contact);
        User user = new User("Merlin", "Guyard", "merlin.guyard@test.com", passwordEncoder.encode("mdp123"));
        userRepository.save(user);

        User contact2Add = new User();
        contact2Add.setEmail("tom.guyard@test.com");

        mockMvc.perform(MockMvcRequestBuilders.post("/contact/add")
                        .with(SecurityMockMvcRequestPostProcessors.user("merlin.guyard@test.com").password("test123"))
                        .param("user", "tom.guyard@test.com")
                        .flashAttr("user", contact2Add))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.flash().attribute("add_status", "Contact added successfully."));
    }
}
