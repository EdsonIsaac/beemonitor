package io.github.edsonisaac.beemonitor.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.json.JsonMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import io.github.edsonisaac.beemonitor.entities.Hive;
import io.github.edsonisaac.beemonitor.entities.User;
import io.github.edsonisaac.beemonitor.enums.Department;
import io.github.edsonisaac.beemonitor.repositories.HiveRepository;
import io.github.edsonisaac.beemonitor.services.HiveService;
import io.github.edsonisaac.beemonitor.services.UserService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.HashMap;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ActiveProfiles(profiles = "test")
@AutoConfigureMockMvc
@ExtendWith(SpringExtension.class)
@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class HiveControllerTest {

    private ObjectMapper mapper;
    private String token;

    private final HiveRepository hiveRepository;
    private final HiveService hiveService;
    private final MockMvc mvc;
    private final UserService userService;

    @Autowired
    HiveControllerTest(HiveRepository hiveRepository, HiveService hiveService, MockMvc mvc, UserService userService) {
        this.hiveRepository = hiveRepository;
        this.hiveService = hiveService;
        this.mvc = mvc;
        this.userService = userService;
    }

    @BeforeAll
    public void beforeAll() throws Exception {

        // Init mapper
        this.mapper = JsonMapper.builder()
                .addModule(new JavaTimeModule())
                .build();

        // Create administration user
        var administration = User.builder()
                .name("ADMINISTRATION")
                .username("administration")
                .password("administration")
                .department(Department.ADMINISTRATION)
                .enabled(true)
                .build();

        // Save administration user
        userService.save(administration);

        // Create login body
        var requestBody = new HashMap<String, String>();

        requestBody.put("username", "administration");
        requestBody.put("password", "administration");

        // Call login endpoint to receive authentication information
        var responseBody = this.mvc.perform(
                        post("/login")
                                .content(mapper.writeValueAsString(requestBody)))
                .andReturn();

        // Convert the response body to json object
        var response = mapper.readValue(responseBody.getResponse().getContentAsString(), ObjectNode.class);

        // Set access_token into the token variable
        if (response.has("access_token")) {
            this.token = response.get("access_token").asText();
        }
    }

    @AfterEach
    public void alterEach() {
        this.hiveRepository.deleteAll();
    }

    @Test
    void shouldSaveTheHive() throws Exception {

        var hive = Hive.builder().code("0001").build();

        this.mvc.perform(
                        post("/hives")
                                .header(HttpHeaders.AUTHORIZATION, "Bearer " + token)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(mapper.writeValueAsString(hive)))
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));

    }

    @Test
    void shouldNotSaveTheHiveWhenCodeIsNull() throws Exception {

        var hive = Hive.builder().code(null).build();

        this.mvc.perform(
                        post("/hives")
                                .header(HttpHeaders.AUTHORIZATION, "Bearer " + token)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(mapper.writeValueAsString(hive)))
                .andExpect(status().isBadRequest())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));
    }

    @Test
    void shouldNotSaveTheHiveWhenCodeIsEmpty() throws Exception {

        var hive = Hive.builder().code("").build();

        this.mvc.perform(
                        post("/hives")
                                .header(HttpHeaders.AUTHORIZATION, "Bearer " + token)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(mapper.writeValueAsString(hive)))
                .andExpect(status().isBadRequest())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));
    }

    @Test
    void shouldReturnAllHives() throws Exception {

        this.mvc.perform(
                        get("/hives")
                                .header(HttpHeaders.AUTHORIZATION, "Bearer " + token))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON));
    }

    @Test
    void shouldDeleteTheHive() throws Exception {

        var hive = Hive.builder().code("0001").build();

        hive = hiveService.save(hive);

        this.mvc.perform(
                        delete("/hives/" + hive.getId())
                                .header(HttpHeaders.AUTHORIZATION, "Bearer " + token))
                .andExpect(status().isOk())
                .andReturn();
    }

    @Test
    void shouldNotDeleteWhenIdIsNull() throws Exception {

        this.mvc.perform(
                        delete("/hives/" + null)
                                .header(HttpHeaders.AUTHORIZATION, "Bearer " + token))
                .andExpect(status().isBadRequest());
    }

    @Test
    void shouldNotDeleteWhenIdNotExists() throws Exception {

        this.mvc.perform(
                        delete("/hives/" + "00000000-0000-0000-0000-000000000000")
                                .header(HttpHeaders.AUTHORIZATION, "Bearer " + token))
                .andExpect(status().isNotFound());
    }
}