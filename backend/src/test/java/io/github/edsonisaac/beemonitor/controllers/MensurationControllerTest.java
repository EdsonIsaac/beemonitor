package io.github.edsonisaac.beemonitor.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import io.github.edsonisaac.beemonitor.models.Hive;
import io.github.edsonisaac.beemonitor.models.Mensuration;
import io.github.edsonisaac.beemonitor.repositories.HiveRepository;
import io.github.edsonisaac.beemonitor.repositories.MensurationRepository;
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
class MensurationControllerTest {

    private final HiveRepository hiveRepository;
    private final MensurationRepository mensurationRepository;
    private final MockMvc mvc;

    private String token;
    private Hive hive;
    private Mensuration mensuration;
    private ObjectMapper mapper;

    @Autowired
    MensurationControllerTest(MockMvc mvc, MensurationRepository mensurationRepository, HiveRepository hiveRepository) {
        this.mvc = mvc;
        this.mensurationRepository = mensurationRepository;
        this.hiveRepository = hiveRepository;
    }

    @BeforeAll
    public void beforeAll() throws Exception {

        mapper = new ObjectMapper();
        mapper.registerModules(new JavaTimeModule());

        hive = Hive.builder()
                .code("0001")
                .build();

        hive = hiveRepository.save(hive);

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

        mensuration = Mensuration.builder()
                .hive(hive)
                .temperature(25.0)
                .humidity(70.0)
                .weight(50.0)
                .build();
    }

    @AfterEach
    public void afterEach() {
        mensurationRepository.delete(mensuration);
    }
    
    @Test
    public void shouldDeleteTheMensuration() throws Exception {
        
        mensuration = mensurationRepository.save(mensuration);

        mvc.perform(
                        delete("/api/mensurations/" + mensuration.getId())
                                .header(HttpHeaders.AUTHORIZATION, "Bearer " + token))
                .andExpect(status().isOk());
    }

    @Test
    public void shouldNotDeleteTheMensurationWhenIdNotFound() throws Exception {

        mvc.perform(
                        delete("/api/mensurations/" + UUID.randomUUID())
                                .header(HttpHeaders.AUTHORIZATION, "Bearer " + token))
                .andExpect(status().isNotFound())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));
    }
}