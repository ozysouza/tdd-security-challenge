package com.desouza.app.controllers;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.time.LocalDate;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.transaction.annotation.Transactional;

import com.desouza.app.dto.EventDTO;
import com.desouza.app.tests.TokenUtil;
import com.fasterxml.jackson.databind.ObjectMapper;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
public class EventControllerIT {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private TokenUtil tokenUtil;

    private String clientUsername;
    private String clientPassword;
    private String adminUsername;
    private String adminPassword;
    private String clientToken;
    private String adminToken;
    private String invalidToken;

    @BeforeEach
    void setUp() throws Exception {
        clientUsername = "ana@gmail.com";
        clientPassword = "123456";
        adminUsername = "bob@gmail.com";
        adminPassword = "123456";

        clientToken = tokenUtil.obtainAccessToken(mockMvc, clientUsername, clientPassword);
        adminToken = tokenUtil.obtainAccessToken(mockMvc, adminUsername, adminPassword);
        invalidToken = adminToken + "xpto";
    }

    @Test
    public void insertShouldReturn401WhenInvalidToken() throws Exception {
        EventDTO dto = new EventDTO(null, "Software Fair", LocalDate.of(2025, 5, 18), "https://softwarefair.ca", 1L);
        String jsonBody = objectMapper.writeValueAsString(dto);

        ResultActions result = mockMvc.perform(post("/events")
                .header("Authorization", "Bearer " + invalidToken)
                .content(jsonBody)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON));

        result.andExpect(status().isUnauthorized());
    }

    @Test
    public void insertShouldInsertResourceWhenClientLoggedAndCorrectData() throws Exception {
        LocalDate nextMonth = LocalDate.now().plusMonths(1L);

        EventDTO dto = new EventDTO(null, "Software Fair", nextMonth, "https://softwarefair.ca", 1L);
        String jsonBody = objectMapper.writeValueAsString(dto);

        ResultActions result = mockMvc.perform(post("/events")
                .header("Authorization", "Bearer " + clientToken)
                .content(jsonBody)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON));

        result.andExpect(status().isCreated());
        result.andExpect(jsonPath("$.id").exists());
        result.andExpect(jsonPath("$.name").value("Software Fair"));
        result.andExpect(jsonPath("$.date").value(nextMonth.toString()));
        result.andExpect(jsonPath("$.url").value("https://softwarefair.ca"));
        result.andExpect(jsonPath("$.cityId").value(1L));
    }

    @Test
    public void insertShouldInsertResourceWhenAdminLoggedAndCorrectData() throws Exception {
        LocalDate nextMonth = LocalDate.now().plusMonths(1L);

        EventDTO dto = new EventDTO(null, "Software Fair", nextMonth, "https://softwarefair.ca", 1L);
        String jsonBody = objectMapper.writeValueAsString(dto);

        ResultActions result = mockMvc.perform(post("/events")
                .header("Authorization", "Bearer " + adminToken)
                .content(jsonBody)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON));

        result.andExpect(status().isCreated());
        result.andExpect(jsonPath("$.id").exists());
        result.andExpect(jsonPath("$.name").value("Software Fair"));
        result.andExpect(jsonPath("$.date").value(nextMonth.toString()));
        result.andExpect(jsonPath("$.url").value("https://softwarefair.ca"));
        result.andExpect(jsonPath("$.cityId").value(1L));
    }

    @Test
    public void insertShouldReturn422WhenAdminLoggedAndBlankName() throws Exception {
        LocalDate nextMonth = LocalDate.now().plusMonths(1L);

        EventDTO dto = new EventDTO(null, "      ", nextMonth, "https://softwarefair.ca", 1L);
        String jsonBody = objectMapper.writeValueAsString(dto);

        ResultActions result = mockMvc.perform(post("/events")
                .header("Authorization", "Bearer " + adminToken)
                .content(jsonBody)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON));

        result.andExpect(status().isUnprocessableEntity());
        result.andExpect(jsonPath("$.errors[0].fieldName").value("name"));
        result.andExpect(jsonPath("$.errors[0].message").value("Field is required"));
    }

    @Test
    public void insertShouldReturn422WhenAdminLoggedAndPastDate() throws Exception {
        LocalDate pastMonth = LocalDate.now().minusMonths(1L);

        EventDTO dto = new EventDTO(null, "Software Fair", pastMonth, "https://softwarefair.ca", 1L);
        String jsonBody = objectMapper.writeValueAsString(dto);

        ResultActions result = mockMvc.perform(post("/events")
                .header("Authorization", "Bearer " + adminToken)
                .content(jsonBody)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON));

        result.andExpect(status().isUnprocessableEntity());
        result.andExpect(jsonPath("$.errors[0].fieldName").value("date"));
        result.andExpect(jsonPath("$.errors[0].message").value("Date must be in the present or in the future"));
    }

    @Test
    public void insertShouldReturn422WhenAdminLoggedAndNullCity() throws Exception {
        LocalDate nextMonth = LocalDate.now().plusMonths(1L);

        EventDTO dto = new EventDTO(null, "Software Fair", nextMonth, "https://softwarefair.ca", null);
        String jsonBody = objectMapper.writeValueAsString(dto);

        ResultActions result = mockMvc.perform(post("/events")
                .header("Authorization", "Bearer " + adminToken)
                .content(jsonBody)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON));

        result.andExpect(status().isUnprocessableEntity());
        result.andExpect(jsonPath("$.errors[0].fieldName").value("cityId"));
        result.andExpect(jsonPath("$.errors[0].message").value("Field is required"));
    }

    @Test
    public void findAllShouldReturnPagedResources() throws Exception {
        ResultActions result = mockMvc.perform(get("/events")
                .contentType(MediaType.APPLICATION_JSON));

        result.andExpect(status().isOk());
        result.andExpect(jsonPath("$.content").exists());
    }
}
