package com.paymybuddy.pmbv1.TI;

import com.paymybuddy.pmbv1.model.User;
import com.paymybuddy.pmbv1.repository.UserRepository;
import com.paymybuddy.pmbv1.service.CustomUserDetailService;
import com.paymybuddy.pmbv1.service.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.Mockito.verify;
import static org.springframework.http.RequestEntity.post;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class RegisterControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserService userService;

    @Autowired
    private WebApplicationContext webApplicationContext;

    @Autowired
    private CustomUserDetailService customUserDetailService;

    @Test
    public void testRegisterUser() throws Exception {

        mockMvc.perform(MockMvcRequestBuilders.post("/register")
                        .with(csrf())
                        .param("firstName", "Bobby")
                        .param("lastName", "Dupont")
                        .param("email", "email@test.com")
                        .param("password", "pazzword"))
                .andExpect(status().isOk());

        Optional<User> user = userRepository.findByEmail("email@test.com");
        assertThat(user).isPresent();
        assertThat(user.get().getFirstName()).isEqualTo("Bobby");
    }

}
