package com.example.expensetracker.dto;

import com.example.expensetracker.model.User;
import com.example.expensetracker.repository.UserRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class RegisterRequestTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ObjectMapper objectMapper;

    @BeforeEach
    void cleanUp() {
        userRepository.deleteAll();
    }

    @Test
    void shouldCreateUserSuccessfully() throws Exception {

        RegisterRequest registerRequest = new RegisterRequest();
        registerRequest.setEmail("test@example.com");
        registerRequest.setPassword("123456");
        registerRequest.setName("Alex");
        registerRequest.setSurname("Alexewich");

        mockMvc.perform(post("/auth/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(registerRequest)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.token").exists());

        User saved = userRepository.findByEmail("test@example.com");
        assertThat(saved).isNotNull();
        assertThat(saved.getName()).isEqualTo("Alex");
    }

    @Test
    void shouldReturnBadRequestWhenUserDataIsInvalid() throws Exception {

        RegisterRequest registerRequest = new RegisterRequest();
        registerRequest.setEmail("invalid");
        registerRequest.setPassword("1");
        registerRequest.setName("");
        registerRequest.setSurname("");

        mockMvc.perform(post("/auth/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(registerRequest)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.errors").isArray())
                .andExpect(jsonPath("$.errors.length()").value(4));

    }

    @Test
    void shouldReturnErrorWhenEmailIsDuplicated() throws Exception {

        RegisterRequest registerRequestFirst = new RegisterRequest();
        registerRequestFirst.setEmail("dublicateTest@example.com");
        registerRequestFirst.setPassword("123456");
        registerRequestFirst.setName("first");
        registerRequestFirst.setSurname("first");

        mockMvc.perform(post("/auth/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(registerRequestFirst)))
                .andExpect(status().isOk());

        RegisterRequest registerRequestSecond = new RegisterRequest();
        registerRequestSecond.setEmail("dublicateTest@example.com");
        registerRequestSecond.setPassword("123456");
        registerRequestSecond.setName("first");
        registerRequestSecond.setSurname("first");


        mockMvc.perform(post("/auth/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(registerRequestSecond)))
                .andExpect(status().isInternalServerError())
                .andExpect(jsonPath("$.errors").isArray())
                .andExpect(jsonPath("$.errors[0]").value("Unexpected error: Email already in use"));

        List<User> users = userRepository.findAll();
        assertThat(users).hasSize(1);
        assertThat(users.get(0).getEmail()).isEqualTo("dublicateTest@example.com");

    }

    @Test
    void shouldReturnCurrentUserWithValidToken() throws Exception {
        RegisterRequest registerRequest = new RegisterRequest();
        registerRequest.setEmail("testuser@example.com");
        registerRequest.setPassword("password123");
        registerRequest.setName("Test");
        registerRequest.setSurname("User");

        String registerJson = objectMapper.writeValueAsString(registerRequest);

        String token = mockMvc.perform(post("/auth/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(registerJson))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();

        String actualToken = objectMapper.readTree(token).get("token").asText();

        mockMvc.perform(get("/api/users/me")
                        .header("Authorization", "Bearer " + actualToken))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.email").value("testuser@example.com"));
    }

}