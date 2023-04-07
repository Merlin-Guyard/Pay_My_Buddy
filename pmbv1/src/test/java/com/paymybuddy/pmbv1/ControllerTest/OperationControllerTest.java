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

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

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
    @WithMockUser(username = "merlin.guyard@test.com", password = "mdp123")
    public void testGetOperations() throws Exception {

        User user = new User("Merlin", "Guyard", "merlin.guyard@test.com", passwordEncoder.encode("mdp123"));
        userRepository.save(user);

        Operation operation = new Operation("Merlin Guyard", "Tom Guyard", "test operation",10);
        operationRepository.save(operation);

        mockMvc.perform(MockMvcRequestBuilders.get("/operation"))
                .andExpect(MockMvcResultMatchers.model().attributeExists("operations"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(result -> {
                    List<Operation> operations = (List<Operation>) result.getModelAndView().getModel().get("operations");
                    assertThat(operations.get(0).getSender()).isEqualTo("Tom Guyard");
                    assertThat(operations.get(0).getReceiver()).isEqualTo("Merlin Guyard");
                    assertThat(operations.get(0).getDescription()).isEqualTo("test operation");
                    assertThat(operations.get(0).getAmount()).isEqualTo(10);
                });
    }
}
