package ru.maruchekas.micromessage.controller;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import ru.maruchekas.micromessage.AbstractTest;
import ru.maruchekas.micromessage.api.request.AuthRequest;
import ru.maruchekas.micromessage.repository.UserRepository;
import ru.maruchekas.micromessage.service.MessageService;

@SpringBootTest
@AutoConfigureMockMvc
@TestPropertySource(value = {"classpath:application-test.properties"})
public class UserControllerTest extends AbstractTest {

    @Autowired
    MessageService messageService;

    @Autowired
    UserRepository userRepository;

    @Autowired
    private MockMvc mockMvc;

    private String userEmail;
    private String adminEmail;
    private String userPass;
    private String adminPass;

    @BeforeEach
    public void setup() {
        super.setup();
        userEmail = "user@mail.ru";
        adminEmail = "admin@mail.ru";
        userPass = "user";
        adminPass = "admin";
    }

    @AfterEach
    public void cleanup() {
    }

    @Test
    void loginUserTest() throws Exception {

        AuthRequest authRequest = new AuthRequest();
        authRequest.setEmail(userEmail);
        authRequest.setPassword(userPass);

        mockMvc.perform(MockMvcRequestBuilders
                        .post("/api/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(authRequest)))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.userId")
                        .value(userRepository.findByEmail(userEmail).get().getId()));
    }

    @Test
    void loginAdminTest() throws Exception {

        AuthRequest authRequest = new AuthRequest();
        authRequest.setEmail(adminEmail);
        authRequest.setPassword(adminPass);

        mockMvc.perform(MockMvcRequestBuilders
                        .post("/api/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(authRequest)))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.userId")
                        .value(userRepository.findByEmail(adminEmail).get().getId()));
    }

    @Test
    void badLoginTest() throws Exception {

        AuthRequest authRequest = new AuthRequest();
        authRequest.setEmail(adminEmail);
        authRequest.setPassword(userPass);

        mockMvc.perform(MockMvcRequestBuilders
                        .post("/api/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(authRequest)))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isForbidden())
                .andExpect(MockMvcResultMatchers.jsonPath("$.title")
                        .value("permission"));
    }

    @Test
    @WithMockUser(username = "admin@mail.ru", authorities = "user:write")
    void getListOfUsersTest() throws Exception {

        mockMvc.perform(MockMvcRequestBuilders
                        .get("/api/auth/users")
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    @WithMockUser(username = "user@mail.ru", authorities = "user:read")
    void getListOfUsersBadTest() throws Exception {

        mockMvc.perform(MockMvcRequestBuilders
                        .get("/api/auth/users")
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isForbidden());
    }

}
