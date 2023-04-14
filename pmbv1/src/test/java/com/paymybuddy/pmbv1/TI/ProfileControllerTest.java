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
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;

@SpringBootTest
@AutoConfigureMockMvc
public class ProfileControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Test
    @WithMockUser(username = "merlin.guyard4@test.com", password = "mdp123")
    public void testGetProfile() throws Exception {

        User user = new User("Merlin4", "Guyard4", "merlin.guyard4@test.com", passwordEncoder.encode("mdp123"));
        user.setBalance(10);
        userRepository.save(user);

        mockMvc.perform(MockMvcRequestBuilders.get("/profile"))
                .andExpect(MockMvcResultMatchers.model().attributeExists("user"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(result -> {
                    User resultUser = (User) result.getModelAndView().getModel().get("user");
                    assertThat(resultUser.getFirstName()).isEqualTo("Merlin4");
                    assertThat(resultUser.getLastName()).isEqualTo("Guyard4");
                    assertThat(resultUser.getEmail()).isEqualTo("merlin.guyard4@test.com");
                    assertThat(resultUser.getBalance()).isEqualTo(10);
                });
    }

    @Test
    @WithMockUser(username = "merlin.guyard5@test.com", password = "mdp123")
    public void testManageMoney() throws Exception {

        User user = new User("Merlin5", "Guyard5", "merlin.guyard5@test.com", passwordEncoder.encode("mdp123"));
        user.setBalance(0);
        userRepository.save(user);

        mockMvc.perform(MockMvcRequestBuilders.get("/profile/bank")
                .with(csrf())
                .param("operation", "depot")
                .param("amount", "10"))
                .andExpect(MockMvcResultMatchers.flash().attribute("man_status", "Money moved successfully"))
                .andExpect(redirectedUrl("/profile"));

        Optional<User> user2Check = userRepository.findByEmail("merlin.guyard5@test.com");
        assertThat(user2Check).isPresent();
        assertThat(user2Check.get().getBalance()).isEqualTo(10);
    }
}
