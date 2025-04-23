package com.example.expensetracker.controller;

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

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class UserControllerTest {

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

        User user = new User();
        user.setEmail("test@example.com");
        user.setPassword("123456");
        user.setName("Alex");
        user.setSurname("Alexewich");

        mockMvc.perform(post("/api/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(user)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.email").value("test@example.com"))
                .andExpect(jsonPath("$.id").isNumber());

        User saved = userRepository.findByEmail("test@example.com");
        assertThat(saved).isNotNull();
        assertThat(saved.getName()).isEqualTo("Alex");
    }

    @Test
    void shouldReturnBadRequestWhenUserDataIsInvalid() throws Exception {

        User user = new User();
        user.setEmail("invalid");
        user.setPassword("1");
        user.setName("");
        user.setSurname("");

        mockMvc.perform(post("/api/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(user)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.errors").isArray())
                .andExpect(jsonPath("$.errors.length()").value(4));

    }

    @Test
    void shouldReturnErrorWhenEmailIsDuplicated() throws Exception {

        User userOne = new User();
        userOne.setEmail("dublicateTest@example.com");
        userOne.setPassword("123456");
        userOne.setName("first");
        userOne.setSurname("first");

        userRepository.save(userOne);

        User userTwo = new User();
        userTwo.setEmail("dublicateTest@example.com");
        userTwo.setPassword("123456");
        userTwo.setName("second");
        userTwo.setSurname("second");

        mockMvc.perform(post("/api/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(userTwo)))
                .andExpect(status().isConflict())
                .andExpect(jsonPath("$.errors").isArray())
                .andExpect(jsonPath("$.errors[0]").value("User with this email already exists"));

    }

}
