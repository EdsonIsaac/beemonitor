package io.github.edsonisaac.beemonitor.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import io.github.edsonisaac.beemonitor.enums.Department;
import io.github.edsonisaac.beemonitor.models.Hive;
import io.github.edsonisaac.beemonitor.models.User;
import io.github.edsonisaac.beemonitor.repositories.HiveRepository;
import io.github.edsonisaac.beemonitor.services.HiveService;
import io.github.edsonisaac.beemonitor.services.UserService;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ActiveProfiles(profiles = "test")
@AutoConfigureMockMvc
@ExtendWith(SpringExtension.class)
@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class HiveControllerTest {

    private Hive hive;
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
        this.mapper = new ObjectMapper();
        this.mapper.registerModules(new JavaTimeModule());

        // Create administration user
        final var administration = User.builder()
                .name("ADMINISTRATION")
                .username("administration")
                .password("administration")
                .department(Department.ADMINISTRATION)
                .enabled(true)
                .build();

        // Save administration user
        userService.save(administration);

        // Create login body
        final var requestBody = new HashMap<String, String>();

        requestBody.put("username", "admin");
        requestBody.put("password", "admin");

        // Call login endpoint to receive authentication information
        final var responseBody = this.mvc.perform(
                        post("/api/auth/token")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(mapper.writeValueAsString(requestBody)))
                .andReturn();

        // Convert the response body to json object
        final var response = mapper.readValue(responseBody.getResponse().getContentAsString(), ObjectNode.class);

        // Set access_token into the token variable
        if (response.has("access_token")) {
            this.token = response.get("access_token").asText();
        }
    }

    @BeforeEach
    public void beforeEach() {

        this.hive = Hive.builder()
                .code("0001")
                .mensurations(Collections.emptySet())
                .build();

        final var hiveDTO = this.hiveService.save(hive);
        BeanUtils.copyProperties(hiveDTO, hiveDTO);
    }

    @AfterEach
    public void alterEach() {
        this.hiveRepository.deleteById(this.hive.getId());
    }

    @Test
    void shouldSaveTheHive() throws Exception {

        this.mvc.perform(
                        post("/api/hives")
                                .header(HttpHeaders.AUTHORIZATION, "Bearer " + token)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(mapper.writeValueAsString(hive)))
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));

    }

    @Test
    void shouldNotSaveTheHiveWhenCodeIsNull() throws Exception {

        hive.setCode(null);

        this.mvc.perform(
                        post("/api/hives")
                                .header(HttpHeaders.AUTHORIZATION, "Bearer " + token)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(mapper.writeValueAsString(hive)))
                .andExpect(status().isBadRequest())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));
    }

    @Test
    void shouldNotSaveTheHiveWhenCodeIsEmpty() throws Exception {

        hive.setCode("");

        this.mvc.perform(
                        post("/api/hives")
                                .header(HttpHeaders.AUTHORIZATION, "Bearer " + token)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(mapper.writeValueAsString(hive)))
                .andExpect(status().isBadRequest())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));
    }

    @Test
    void shouldReturnAllHives() throws Exception {

        final var response = this.mvc.perform(
                        get("/api/hives")
                                .header(HttpHeaders.AUTHORIZATION, "Bearer " + token))
                .andReturn().getResponse();

        final var hives = mapper.readValue(response.getContentAsString(), LinkedHashMap.class);

        assertEquals(HttpStatus.OK.value(), response.getStatus());
        assertEquals(1, Integer.parseInt(hives.get("totalElements").toString()));
    }

    @Test
    void shouldDeleteTheHive() throws Exception {

        this.mvc.perform(
                        delete("/api/hives/" + hive.getId())
                                .header(HttpHeaders.AUTHORIZATION, "Bearer " + token))
                .andExpect(status().isOk());
    }

    @Test
    void shouldNotDeleteWhenIdNotExists() throws Exception {

        this.mvc.perform(
                        delete("/api/hives/" + UUID.randomUUID())
                                .header(HttpHeaders.AUTHORIZATION, "Bearer " + token))
                .andExpect(status().isNotFound());
    }
}