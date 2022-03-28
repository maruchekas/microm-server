package ru.maruchekas.micromessage.controller;

import org.hamcrest.Matchers;
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
import ru.maruchekas.micromessage.api.request.CreateMessageRequest;
import ru.maruchekas.micromessage.repository.UserRepository;
import ru.maruchekas.micromessage.service.MessageService;

import java.time.LocalDateTime;

import static ru.maruchekas.micromessage.config.Constants.INCORRECT_PERIOD;
import static ru.maruchekas.micromessage.config.Constants.INVALID_DATEFORMAT;


@SpringBootTest
@AutoConfigureMockMvc
@TestPropertySource(value = {"classpath:application-test.properties"})
public class MessageControllerTest extends AbstractTest {

    @Autowired
    MessageService messageService;

    @Autowired
    UserRepository userRepository;

    @Autowired
    private MockMvc mockMvc;

    @BeforeEach
    public void setup() {
        super.setup();
    }

    @AfterEach
    public void cleanup() {
    }


    @Test
    @WithMockUser(username = "admin@mail.ru", authorities = "user:write")
    void sendMessageTest() throws Exception {
        CreateMessageRequest createMessageRequest = new CreateMessageRequest()
                .setText("test text");

        String ctime = LocalDateTime.now().toString().substring(0, 17);

        mockMvc.perform(MockMvcRequestBuilders.post("/api/message")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(createMessageRequest)))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.jsonPath("$.text").value(createMessageRequest.getText()))
                .andExpect(MockMvcResultMatchers.content().string(Matchers.containsString(ctime)))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    @WithMockUser(username = "user@mail.ru", authorities = "user:read")
    void sendMessageBadTest() throws Exception {
        CreateMessageRequest createMessageRequest = new CreateMessageRequest()
                .setText("test text");

        String ctime = LocalDateTime.now().toString().substring(0, 17);

        mockMvc.perform(MockMvcRequestBuilders.post("/api/message")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(createMessageRequest)))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isForbidden());
    }

    @Test
    @WithMockUser(username = "user@mail.ru", authorities = "user:read")
    void getMessageListTest() throws Exception {

        String fromTime = LocalDateTime.now().minusDays(1L).toString();
        String toTime = LocalDateTime.now().toString();

        mockMvc.perform(MockMvcRequestBuilders
                        .get("/api/message/from/{from}/to/{to}", fromTime, toTime)
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.jsonPath("$.total").exists())
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    @WithMockUser(username = "user@mail.ru", authorities = "user:read")
    void getMessageListBadTest() throws Exception {

        String fromTime = LocalDateTime.now().minusDays(1L).toString();
        String toTime = LocalDateTime.now().toString();

        mockMvc.perform(MockMvcRequestBuilders
                        .get("/api/message/from/{from}/to/{to}",
                                fromTime.replace('T', ' '), toTime)
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.jsonPath("$.total").doesNotExist())
                .andExpect(MockMvcResultMatchers.jsonPath("$.message").value(INVALID_DATEFORMAT))
                .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }

    @Test
    @WithMockUser(username = "user@mail.ru", authorities = "user:read")
    void getMessageListBadRangeTest() throws Exception {

        String fromTime = LocalDateTime.now().toString();
        String toTime = LocalDateTime.now().minusDays(1L).toString();

        mockMvc.perform(MockMvcRequestBuilders
                        .get("/api/message/from/{from}/to/{to}", fromTime, toTime)
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.jsonPath("$.total").doesNotExist())
                .andExpect(MockMvcResultMatchers.jsonPath("$.message").value(INCORRECT_PERIOD))
                .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }
}