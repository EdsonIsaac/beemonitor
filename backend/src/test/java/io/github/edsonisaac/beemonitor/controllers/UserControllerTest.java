package io.github.edsonisaac.beemonitor.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import io.github.edsonisaac.beemonitor.enums.Department;
import io.github.edsonisaac.beemonitor.models.User;
import io.github.edsonisaac.beemonitor.repositories.UserRepository;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import java.util.HashMap;
import java.util.UUID;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ActiveProfiles(profiles = "test")
@AutoConfigureMockMvc
@ExtendWith(SpringExtension.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@SpringBootTest
class UserControllerTest {

    private final MockMvc mvc;
    private final UserRepository userRepository;

    private ObjectMapper mapper;
    private String token;
    private User user;

    @Autowired
    UserControllerTest(MockMvc mvc, UserRepository userRepository) {
        this.mvc = mvc;
        this.userRepository = userRepository;
    }

    @BeforeAll
    public void beforeAll() throws Exception {
        
        mapper = new ObjectMapper();
        mapper.registerModules(new JavaTimeModule());

        final var requestBody = new HashMap<String, String>();

        requestBody.put("username", "admin");
        requestBody.put("password", "admin");

        final var responseBody = mvc.perform(
                        post("/api/auth/token")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(mapper.writeValueAsString(requestBody)))
                .andReturn();

        final var response = mapper.readValue(responseBody.getResponse().getContentAsString(), ObjectNode.class);

        if (response.has("access_token")) {
            token = response.get("access_token").asText();
        }
    }

    @BeforeEach
    public void beforeEach() {

        user = User.builder()
                .name("teste")
                .username("teste")
                .password("teste")
                .department(Department.ADMINISTRATION)
                .enabled(true)
                .build();
    }

    @AfterEach
    public void afterEach() {
        userRepository.delete(user);
    }

    @Test
    public void shouldDeleteTheUser() throws Exception {
        
        user = userRepository.save(user);

        mvc.perform(
                        delete("/api/users/" + user.getId())
                                .header(HttpHeaders.AUTHORIZATION, "Bearer " + token))
                .andExpect(status().isOk());
    }

    @Test
    public void shouldNotDeleteTheUserWhenIdNotFound() throws Exception {

        mvc.perform(
                        delete("/api/users/" + UUID.randomUUID())
                                .header(HttpHeaders.AUTHORIZATION, "Bearer " + token))
                .andExpect(status().isNotFound())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));
    }
}