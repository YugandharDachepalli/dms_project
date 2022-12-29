package com.te.dms.controller;

import static org.junit.jupiter.api.Assertions.*;

import java.io.UnsupportedEncodingException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.assertj.core.util.Lists;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.te.dms.entity.DmsUser;
import com.te.dms.entity.Projects;
import com.te.dms.response.GeneralResponse;
import com.te.dms.service.DmsUserService;

@SpringBootTest
@AutoConfigureMockMvc
class GeneralControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private DmsUserService dmsUserService;

	@InjectMocks
	private GeneralController generalController;

	@Autowired
	private ObjectMapper objectMapper;

	// test case for Registering User Successfully
	@Test
	public void testUserRegister() throws JsonProcessingException, UnsupportedEncodingException, Exception {
		List<Projects> projects = Lists.newArrayList();
		Projects projects2 = new Projects();
		projects2.setContactName("Rakesh");
		projects2.setCreatedAt(LocalDateTime.now());
		projects2.setProjectName("Document-Management-System");
		projects2.setUpdatedAt(LocalDateTime.now());
		projects.add(projects2);
		DmsUser dmsUser = DmsUser.builder().firstName("Srikar").lastName("Koti").emailId("s@s.com").phoneNo(8341460425L)
				.projects(projects).userId("TE001").build();

		Mockito.when(dmsUserService.register(Mockito.any())).thenReturn(Optional.ofNullable(true));

		String contentAsString = mockMvc
				.perform(MockMvcRequestBuilders.post("/dms/register").accept(MediaType.APPLICATION_JSON_VALUE)
						.content(objectMapper.writeValueAsString(dmsUser))
						.contentType(MediaType.APPLICATION_JSON_VALUE))
				.andExpect(MockMvcResultMatchers.status().isOk()).andReturn().getResponse().getContentAsString();
		GeneralResponse<Boolean> readValue = objectMapper.readValue(contentAsString, GeneralResponse.class);
		assertEquals("Registration Successfull", readValue.getMessage());
	}

	// test case for register user which returns bad Http response as bad request
	@Test
	public void testRegisterUser_Returns400() throws JsonProcessingException, UnsupportedEncodingException, Exception {

		List<Projects> projects = Lists.newArrayList();
		Projects projects2 = new Projects();
		projects2.setContactName("Rakesh");
		projects2.setCreatedAt(LocalDateTime.now());
		projects2.setProjectName("Document-Management-System");
		projects2.setUpdatedAt(LocalDateTime.now());
		projects.add(projects2);
		DmsUser dmsUser = DmsUser.builder().emailId("s@s.com").firstName("Srikar").lastName("Koti").phoneNo(123456789L)
				.projects(projects).userId("TE001").build();

		Mockito.when(dmsUserService.register(Mockito.any())).thenReturn(Optional.ofNullable(false));

		mockMvc.perform(MockMvcRequestBuilders.post("/dms/register").accept(MediaType.APPLICATION_JSON_VALUE)
				.contentType(MediaType.APPLICATION_JSON_VALUE).content(objectMapper.writeValueAsString(dmsUser)))
				.andExpect(MockMvcResultMatchers.status().isBadRequest());
	}
}
