package com.staffinity.recruiting.candidates.infrastructure.web.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.staffinity.recruiting.candidates.application.dto.CandidateRequest;
import com.staffinity.recruiting.candidates.application.dto.CandidateResponse;
import com.staffinity.recruiting.candidates.infrastructure.persistence.JpaCandidateRepository;
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
import org.springframework.test.web.servlet.MvcResult;

import java.math.BigDecimal;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc(addFilters = false)
@ActiveProfiles("test")
class CandidateControllerIntegrationTest {

    private static final String CANDIDATES = "/candidates";

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private JpaCandidateRepository candidateRepository;

    @Autowired
    private JpaVacancyRepository vacancyRepository;

    private CandidateRequest validCandidateRequest;
    private UUID vacancyId;

    @BeforeEach
    void setUp() {
        // Clean up database before each test to ensure isolation
        candidateRepository.deleteAll();
        vacancyRepository.deleteAll();

        // Create a vacancy first
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

        // Assuming we can save directly or use the service, but for simplicity, we'll create the vacancy via API or directly
        // For now, create a vacancy entity directly
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
        vacancyId = vacancyEntity.getId();

        validCandidateRequest = new CandidateRequest();
        validCandidateRequest.vacancyId = vacancyId;
        validCandidateRequest.firstName = "John";
        validCandidateRequest.lastName = "Doe";
        validCandidateRequest.email = "john.doe@example.com";
        validCandidateRequest.phoneNumber = "+1234567890";
        validCandidateRequest.resumeUrl = "http://example.com/resume.pdf";
        validCandidateRequest.linkedinUrl = "http://linkedin.com/in/johndoe";
        validCandidateRequest.portfolioUrl = "http://johndoe.com";
        validCandidateRequest.applicationStatus = "APPLIED";
        validCandidateRequest.source = "LinkedIn";
        validCandidateRequest.rejectionReason = null;
    }

    @Test
    void shouldCreateCandidateSuccessfully() throws Exception {
        // Given
        String requestJson = objectMapper.writeValueAsString(validCandidateRequest);

        // When & Then
        MvcResult result = mockMvc.perform(post(CANDIDATES)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(requestJson))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").exists())
                .andExpect(jsonPath("$.firstName").value("John"))
                .andExpect(jsonPath("$.lastName").value("Doe"))
                .andExpect(jsonPath("$.email").value("john.doe@example.com"))
                .andExpect(jsonPath("$.applicationStatus").value("APPLIED"))
                .andReturn();

        String responseJson = result.getResponse().getContentAsString();
        CandidateResponse response = objectMapper.readValue(responseJson, CandidateResponse.class);

        assertThat(response.id).isNotNull();
        assertThat(response.vacancyId).isEqualTo(vacancyId);
    }

    @Test
    void shouldListAllCandidates() throws Exception {
        // Given - Create a candidate first
        String requestJson = objectMapper.writeValueAsString(validCandidateRequest);
        mockMvc.perform(post(CANDIDATES)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(requestJson))
                .andExpect(status().isCreated());

        // When & Then - List all candidates
        mockMvc.perform(get(CANDIDATES).accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$[0].id").exists())
                .andExpect(jsonPath("$[0].firstName").value("John"))
                .andExpect(jsonPath("$[0].email").value("john.doe@example.com"));
    }

    @Test
    void shouldListEmptyArrayWhenNoCandidatesExist() throws Exception {
        // When & Then
        mockMvc.perform(get(CANDIDATES).accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$").isEmpty());
    }

    @Test
    void shouldUpdateCandidateSuccessfully() throws Exception {
        // Given - Create a candidate first
        String requestJson = objectMapper.writeValueAsString(validCandidateRequest);
        MvcResult createResult = mockMvc.perform(post(CANDIDATES)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(requestJson))
                .andExpect(status().isCreated())
                .andReturn();

        String createResponseJson = createResult.getResponse().getContentAsString();
        CandidateResponse createdCandidate = objectMapper.readValue(createResponseJson, CandidateResponse.class);

        // Update request
        CandidateRequest updateRequest = new CandidateRequest();
        updateRequest.vacancyId = vacancyId;
        updateRequest.firstName = "John Updated";
        updateRequest.lastName = "Doe Updated";
        updateRequest.email = "john.updated@example.com";
        updateRequest.phoneNumber = "+0987654321";
        updateRequest.resumeUrl = "http://example.com/updated-resume.pdf";
        updateRequest.linkedinUrl = "http://linkedin.com/in/johndoeupdated";
        updateRequest.portfolioUrl = "http://johndoeupdated.com";
        updateRequest.applicationStatus = "INTERVIEWING";
        updateRequest.source = "Indeed";
        updateRequest.rejectionReason = null;

        String updateJson = objectMapper.writeValueAsString(updateRequest);

        // When & Then - Update the candidate
        mockMvc.perform(put(CANDIDATES + "/" + createdCandidate.id)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(updateJson))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.firstName").value("John Updated"))
                .andExpect(jsonPath("$.lastName").value("Doe Updated"))
                .andExpect(jsonPath("$.email").value("john.updated@example.com"))
                .andExpect(jsonPath("$.applicationStatus").value("INTERVIEWING"));
    }
}
