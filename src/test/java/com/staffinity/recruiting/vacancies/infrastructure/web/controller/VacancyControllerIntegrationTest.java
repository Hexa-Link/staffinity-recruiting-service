package com.staffinity.recruiting.vacancies.infrastructure.web.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.staffinity.recruiting.vacancies.application.dto.CreateVacancyRequest;
import com.staffinity.recruiting.vacancies.application.dto.VacancyResponse;
import com.staffinity.recruiting.vacancies.infrastructure.persistence.JpaVacancyRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.math.BigDecimal;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc(addFilters = false)
@ActiveProfiles("test")
class VacancyControllerIntegrationTest {

    private static final String VACANCIES = "/vacancies";

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private JpaVacancyRepository vacancyRepository;

    private CreateVacancyRequest validVacancyRequest;

    @BeforeEach
    void setUp() {
        // Clean up database before each test to ensure isolation
        vacancyRepository.deleteAll();

        validVacancyRequest = new CreateVacancyRequest();
        validVacancyRequest.hiringManagerId = UUID.randomUUID();
        validVacancyRequest.recruiterId = UUID.randomUUID();
        validVacancyRequest.title = "Senior Java Developer";
        validVacancyRequest.description = "We are looking for an experienced Java developer to join our team.";
        validVacancyRequest.requirements = "5+ years of Java experience, Spring Boot, Microservices";
        validVacancyRequest.location = "Remote";
        validVacancyRequest.remoteAllowed = true;
        validVacancyRequest.seniority = "Senior";
        validVacancyRequest.status = "OPEN";
        validVacancyRequest.salaryMin = new BigDecimal("80000.00");
        validVacancyRequest.salaryMax = new BigDecimal("120000.00");
        validVacancyRequest.currency = "USD";
        validVacancyRequest.closedAt = null;
    }

    @Test
    void shouldCreateVacancySuccessfully() throws Exception {
        // Given
        String requestJson = objectMapper.writeValueAsString(validVacancyRequest);

        // When & Then
        MvcResult result = mockMvc.perform(post(VACANCIES)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(requestJson))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").exists())
                .andExpect(jsonPath("$.title").value("Senior Java Developer"))
                .andExpect(jsonPath("$.description").value(validVacancyRequest.description))
                .andExpect(jsonPath("$.status").value("OPEN"))
                .andExpect(jsonPath("$.location").value("Remote"))
                .andExpect(jsonPath("$.remoteAllowed").value(true))
                .andExpect(jsonPath("$.seniority").value("Senior"))
                .andExpect(jsonPath("$.salaryMin").value(80000.00))
                .andExpect(jsonPath("$.salaryMax").value(120000.00))
                .andExpect(jsonPath("$.currency").value("USD"))
                .andReturn();

        String responseJson = result.getResponse().getContentAsString();
        VacancyResponse response = objectMapper.readValue(responseJson, VacancyResponse.class);

        assertThat(response.id).isNotNull();
        assertThat(response.hiringManagerId).isEqualTo(validVacancyRequest.hiringManagerId);
        assertThat(response.recruiterId).isEqualTo(validVacancyRequest.recruiterId);
    }

    @Test
    void shouldListAllVacancies() throws Exception {
        // Given - Create a vacancy first
        String requestJson = objectMapper.writeValueAsString(validVacancyRequest);
        mockMvc.perform(post(VACANCIES)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(requestJson))
                .andExpect(status().isCreated());

        // When & Then - List all vacancies
        mockMvc.perform(get(VACANCIES).accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$[0].id").exists())
                .andExpect(jsonPath("$[0].title").value("Senior Java Developer"))
                .andExpect(jsonPath("$[0].status").value("OPEN"));
    }

    @Test
    void shouldListEmptyArrayWhenNoVacanciesExist() throws Exception {
        // When & Then
        mockMvc.perform(get(VACANCIES).accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$").isEmpty());
    }

    @Test
    void shouldCreateMultipleVacanciesAndListThem() throws Exception {
        // Given - Create first vacancy
        String request1Json = objectMapper.writeValueAsString(validVacancyRequest);
        mockMvc.perform(post(VACANCIES)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(request1Json))
                .andExpect(status().isCreated());

        // Create second vacancy
        CreateVacancyRequest request2 = new CreateVacancyRequest();
        request2.hiringManagerId = UUID.randomUUID();
        request2.recruiterId = UUID.randomUUID();
        request2.title = "Frontend Developer";
        request2.description = "React developer needed";
        request2.requirements = "3+ years React experience";
        request2.location = "New York";
        request2.remoteAllowed = false;
        request2.seniority = "Mid";
        request2.status = "OPEN";
        request2.salaryMin = new BigDecimal("60000.00");
        request2.salaryMax = new BigDecimal("90000.00");
        request2.currency = "USD";

        String request2Json = objectMapper.writeValueAsString(request2);
        mockMvc.perform(post(VACANCIES)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(request2Json))
                .andExpect(status().isCreated());

        // When & Then - List should have 2 vacancies
        mockMvc.perform(get(VACANCIES).accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$[0].title").value("Senior Java Developer"))
                .andExpect(jsonPath("$[1].title").value("Frontend Developer"));
    }
}
