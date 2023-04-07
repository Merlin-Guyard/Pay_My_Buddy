//package com.paymybuddy.pmbv1.ControllerTest;
//
//import com.fasterxml.jackson.databind.ObjectMapper;
//import com.paymybuddy.pmbv1.model.User;
//import com.paymybuddy.pmbv1.repository.UserRepository;
//import net.minidev.json.JSONObject;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.http.MediaType;
//import org.springframework.security.crypto.password.PasswordEncoder;
//import org.springframework.security.test.context.support.WithMockUser;
//import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors;
//import org.springframework.test.web.servlet.MockMvc;
//import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
//import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
//import org.springframework.web.servlet.mvc.support.RedirectAttributes;
//
//import java.util.ArrayList;
//import java.util.List;
//
//import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
//import static org.junit.jupiter.api.Assertions.assertEquals;
//
//@SpringBootTest
//@AutoConfigureMockMvc
//public class ContactControllerTest {
//    @Autowired
//    private MockMvc mockMvc;
//
//    @Autowired
//    private UserRepository userRepository;
//
//    @Autowired
//    private PasswordEncoder passwordEncoder;
//
//    @Test
//    @WithMockUser(username = "merlin.guyard@test.com", password = "mdp123")
//    public void testGetContacts() throws Exception {
//
//        User contact = new User("Tom", "Guyard", "tom.guyard@test.com", passwordEncoder.encode("mdp123"));
//        userRepository.save(contact);
//
//        User user = new User("Merlin", "Guyard", "merlin.guyard@test.com", passwordEncoder.encode("mdp123"));
//        List<User> contacts = new ArrayList<>();
//        contacts.add(contact);
//        user.setFriendList(contacts);
//        userRepository.save(user);
//
//        mockMvc.perform(MockMvcRequestBuilders.get("/contact"))
//                .andExpect(MockMvcResultMatchers.model().attributeExists("users"))
//                .andExpect(MockMvcResultMatchers.status().isOk())
//                .andExpect(result -> {
//                    List<User> users = (List<User>) result.getModelAndView().getModel().get("users");
//                    assertThat(users.get(0).getFirstName()).isEqualTo("Tom");
//                    assertThat(users.get(0).getLastName()).isEqualTo("Guyard");
//                    assertThat(users.get(0).getEmail()).isEqualTo("tom.guyard@test.com");
//                });
//    }
//
//    @Test
//    @WithMockUser(username = "merlin.guyard@test.com", password = "mdp123")
//    public void testAddContacts() throws Exception {
//
//        User contact = new User("Tom", "Guyard", "tom.guyard@test.com", passwordEncoder.encode("mdp123"));
//        userRepository.save(contact);
//        User user = new User("Merlin", "Guyard", "merlin.guyard@test.com", passwordEncoder.encode("mdp123"));
//        userRepository.save(user);
//
//        User contact2Add = new User();
//        contact2Add.setEmail("tom.guyard@test.com");
//
//        JSONObject jsonEmail = new JSONObject();
//        jsonEmail.put("email", "tom.guyard@test.com");
//
//        JSONObject jsonUser = new JSONObject();
//        jsonUser.put("user", jsonEmail);
//
//        final ObjectMapper mapper = new ObjectMapper();
//        final String jsonContent = mapper.writeValueAsString(contact2Add);
//
//        mockMvc.perform(MockMvcRequestBuilders.post("/contact/add")
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content(String.valueOf(jsonEmail))
//                        .accept(MediaType.APPLICATION_JSON))
//                .andExpect(MockMvcResultMatchers.status().isOk())
//                .andExpect(MockMvcResultMatchers.flash().attribute("add_status", "Contact added successfully."));
//
//        assertEquals(user.getFriendList().get(0).getEmail(), "tom.guyard@test.com");
//    }
//
//    @Test
//    @WithMockUser(username = "merlin.guyard@test.com", password = "mdp123")
//    public void testDelContacts() throws Exception {
//
//        User contact = new User("Tom", "Guyard", "tom.guyard@test.com", passwordEncoder.encode("mdp123"));
//        userRepository.save(contact);
//
//        User user = new User("Merlin", "Guyard", "merlin.guyard@test.com", passwordEncoder.encode("mdp123"));
//        List<User> contacts = new ArrayList<>();
//        contacts.add(contact);
//        user.setFriendList(contacts);
//        userRepository.save(user);
//
//        User contact2Add = new User();
//        contact2Add.setEmail("tom.guyard@test.com");
//
//        JSONObject jsonEmail = new JSONObject();
//        jsonEmail.put("email", "tom.guyard@test.com");
//
//        JSONObject jsonUser = new JSONObject();
//        jsonUser.put("user", jsonEmail);
//
//        final ObjectMapper mapper = new ObjectMapper();
//        final String jsonContent = mapper.writeValueAsString(contact2Add);
//
//        mockMvc.perform(MockMvcRequestBuilders.post("/contact/del")
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content(String.valueOf(jsonEmail))
//                        .accept(MediaType.APPLICATION_JSON))
//                .andExpect(MockMvcResultMatchers.status().isOk())
//                .andExpect(MockMvcResultMatchers.flash().attribute("del_status", "Contact removed successfully."));
//
//        assertThat(user.getFriendList().isEmpty());
//    }
//}
