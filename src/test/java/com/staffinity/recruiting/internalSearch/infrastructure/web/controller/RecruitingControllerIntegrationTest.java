package com.staffinity.recruiting.internalSearch.infrastructure.web.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.staffinity.recruiting.vacancies.application.dto.CreateVacancyRequest;
import com.staffinity.recruiting.vacancies.infrastructure.persistence.JpaVacancyRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.util.UUID;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc(addFilters = false)
@ActiveProfiles("test")
class RecruitingControllerIntegrationTest {

    private static final String INTERNAL_SEARCH = "/recruiting/internal-search";

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private JpaVacancyRepository vacancyRepository;

    @BeforeEach
    void setUp() {
        // Clean up database before each test to ensure isolation
        vacancyRepository.deleteAll();

        // Create a vacancy
        CreateVacancyRequest vacancyRequest = new CreateVacancyRequest();
        vacancyRequest.hiringManagerId = UUID.randomUUID();
        vacancyRequest.recruiterId = UUID.randomUUID();
        vacancyRequest.title = "Senior Java Developer";
        vacancyRequest.description = "We are looking for an experienced Java developer";
        vacancyRequest.requirements = "5+ years of Java experience";
        vacancyRequest.location = "Remote";
        vacancyRequest.remoteAllowed = true;
        vacancyRequest.seniority = "Senior";
        vacancyRequest.status = "OPEN";
        vacancyRequest.salaryMin = new BigDecimal("80000.00");
        vacancyRequest.salaryMax = new BigDecimal("120000.00");
        vacancyRequest.currency = "USD";

        var vacancyEntity = new com.staffinity.recruiting.vacancies.infrastructure.persistence.VacancyEntity();
        vacancyEntity.setId(UUID.randomUUID());
        vacancyEntity.setHiringManagerId(vacancyRequest.hiringManagerId);
        vacancyEntity.setRecruiterId(vacancyRequest.recruiterId);
        vacancyEntity.setTitle(vacancyRequest.title);
        vacancyEntity.setDescription(vacancyRequest.description);
        vacancyEntity.setRequirements(vacancyRequest.requirements);
        vacancyEntity.setLocation(vacancyRequest.location);
        vacancyEntity.setRemoteAllowed(vacancyRequest.remoteAllowed);
        vacancyEntity.setSeniority(vacancyRequest.seniority);
        vacancyEntity.setStatus(vacancyRequest.status);
        vacancyEntity.setSalaryMin(vacancyRequest.salaryMin);
        vacancyEntity.setSalaryMax(vacancyRequest.salaryMax);
        vacancyEntity.setCurrency(vacancyRequest.currency);
        vacancyRepository.save(vacancyEntity);
    }

    @Test
    void shouldReturnInternalSearchResults() throws Exception {
        // When & Then
        mockMvc.perform(get(INTERNAL_SEARCH).accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$").isArray())
                // Since mock employees are returned, there should be results
                .andExpect(jsonPath("$[0].vacancy").exists())
                .andExpect(jsonPath("$[0].employee").exists())
                .andExpect(jsonPath("$[0].matchScore").exists())
                .andExpect(jsonPath("$[0].matchScore").isNumber());
    }

    @Test
    void shouldReturnEmptyArrayWhenNoVacancies() throws Exception {
        // Given - Delete all vacancies
        vacancyRepository.deleteAll();

        // When & Then
        mockMvc.perform(get(INTERNAL_SEARCH).accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$").isEmpty());
    }
}
