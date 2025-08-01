package com.desouza.app.controllers;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.transaction.annotation.Transactional;

import com.desouza.app.dto.CityDTO;
import com.desouza.app.tests.TokenUtil;
import com.fasterxml.jackson.databind.ObjectMapper;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
public class CityControllerIT {

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
		invalidToken = adminToken + "xpto"; // Simulates a wrong token
	}

	@Test
	public void insertShouldReturn401WhenIvalidToken() throws Exception {

		CityDTO dto = new CityDTO(null, "Kingston");
		String jsonBody = objectMapper.writeValueAsString(dto);
		
		ResultActions result =
				mockMvc.perform(post("/cities")
					.header("Authorization", "Bearer " + invalidToken)
					.content(jsonBody)
					.contentType(MediaType.APPLICATION_JSON)
					.accept(MediaType.APPLICATION_JSON));
		
		result.andExpect(status().isUnauthorized());
	}
	
	@Test
	public void insertShouldReturn403WhenClientLogged() throws Exception {

		CityDTO dto = new CityDTO(null, "Kingston");
		String jsonBody = objectMapper.writeValueAsString(dto);
		
		ResultActions result =
				mockMvc.perform(post("/cities")
					.header("Authorization", "Bearer " + clientToken)
					.content(jsonBody)
					.contentType(MediaType.APPLICATION_JSON)
					.accept(MediaType.APPLICATION_JSON));
		
		result.andExpect(status().isForbidden());
	}
	
	@Test
	public void insertShouldInsertResourceWhenAdminLoggedAndCorrectData() throws Exception {

		CityDTO dto = new CityDTO(null, "Kingston");
		String jsonBody = objectMapper.writeValueAsString(dto);
		
		ResultActions result =
				mockMvc.perform(post("/cities")
					.header("Authorization", "Bearer " + adminToken)
					.content(jsonBody)
					.contentType(MediaType.APPLICATION_JSON)
					.accept(MediaType.APPLICATION_JSON));
		
		result.andExpect(status().isCreated());
		result.andExpect(jsonPath("$.id").exists());
		result.andExpect(jsonPath("$.name").value("Kingston"));
	}

	@Test
	public void insertShouldReturn422WhenAdminLoggedAndBlankName() throws Exception {

		CityDTO dto = new CityDTO(null, "    ");
		String jsonBody = objectMapper.writeValueAsString(dto);
		
		ResultActions result =
				mockMvc.perform(post("/cities")
					.header("Authorization", "Bearer " + adminToken)
					.content(jsonBody)
					.contentType(MediaType.APPLICATION_JSON)
					.accept(MediaType.APPLICATION_JSON));
		
		result.andExpect(status().isUnprocessableEntity());
		result.andExpect(jsonPath("$.errors[0].fieldName").value("name"));
		result.andExpect(jsonPath("$.errors[0].message").value("Field is required"));
	}

	@Test
	public void findAllShouldReturnAllResourcesSortedByName() throws Exception {
		
		ResultActions result =
				mockMvc.perform(get("/cities")
					.contentType(MediaType.APPLICATION_JSON));

		result.andExpect(status().isOk());
		result.andExpect(jsonPath("$[0].name").value("Calgary"));
		result.andExpect(jsonPath("$[1].name").value("Edmonton"));
		result.andExpect(jsonPath("$[2].name").value("Halifax"));
	}
}
