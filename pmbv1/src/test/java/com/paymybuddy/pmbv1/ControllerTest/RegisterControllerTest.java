package com.paymybuddy.pmbv1.ControllerTest;

import com.paymybuddy.pmbv1.controller.RegisterController;
import com.paymybuddy.pmbv1.model.User;
import com.paymybuddy.pmbv1.repository.UserRepository;
import com.paymybuddy.pmbv1.service.CustomUserDetailService;
import com.paymybuddy.pmbv1.service.UserService;
import org.apache.catalina.security.SecurityConfig;
import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.http.RequestEntity.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(RegisterController.class)
@AutoConfigureMockMvc
public class RegisterControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService userService;

    @MockBean
    private WebApplicationContext webApplicationContext;

    @MockBean
    private UserRepository userRepository;

    @MockBean
    private CustomUserDetailService customUserDetailService;

    @Test
    public void testRegisterUser() throws Exception {

        mockMvc.perform(MockMvcRequestBuilders.post("/register")
                        .param("user", "Bobby", "Dupont", "email@test.com", "pazzword"))
                .andExpect(status().isOk());

        // Verify that the user was created
        User expectedUser = new User("Bobby", "Dupont", "email@test.com", "pazzword");
        verify(userRepository).save(expectedUser);
    }

}
