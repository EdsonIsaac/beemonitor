package io.github.edsonisaac.beemonitor.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import java.util.HashMap;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ActiveProfiles(profiles = "test")
@AutoConfigureMockMvc
@ExtendWith(SpringExtension.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@SpringBootTest
class AuthenticationControllerTest {

    private final MockMvc mvc;
    private ObjectMapper mapper;

    @Autowired
    AuthenticationControllerTest(MockMvc mvc) {
        this.mvc = mvc;
    }

    @BeforeAll
    public void beforeAll() {
        mapper = new ObjectMapper();
    }

    @Test
    void shouldAuthenticate() throws Exception {

        final var requestBody = new HashMap<String, String>();

        requestBody.put("username", "admin");
        requestBody.put("password", "admin");

        mvc.perform(
                        post("/api/auth/token")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(mapper.writeValueAsString(requestBody)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));
    }

    @Test
    void shouldNotAuthenticate() throws Exception {

        final var requestBody = new HashMap<String, String>();

        requestBody.put("username", "admin");
        requestBody.put("password", "aadmin");

        mvc.perform(
                        post("/api/auth/token")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(mapper.writeValueAsString(requestBody)))
                .andExpect(status().isUnauthorized())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));
    }
}