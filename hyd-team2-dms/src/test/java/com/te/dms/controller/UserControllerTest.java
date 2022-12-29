package com.te.dms.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.UnsupportedEncodingException;
import java.util.Optional;

import org.junit.Test;
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
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.te.dms.dto.DeleteDocumentDto;
import com.te.dms.dto.DeleteFileDto;
import com.te.dms.dto.NewDocumentsDto;
import com.te.dms.dto.NewProjectDto;
import com.te.dms.dto.ShareFileDto;
import com.te.dms.response.GeneralResponse;
import com.te.dms.service.DmsUserService;

@SpringBootTest
@AutoConfigureMockMvc
class UserControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private DmsUserService dmsUserService;

	@InjectMocks
	private UserController userController;

	ObjectMapper objectMapper = new ObjectMapper();

	
	
	@Test
	public void testCreateProject() throws JsonProcessingException, Exception {
		objectMapper.registerModule(new JavaTimeModule());
		NewProjectDto newProjectDto = NewProjectDto.builder().projectId("Ty001").projectName("DMS").contactName("Leo")
				.build();
		Mockito.when(dmsUserService.createProject(Mockito.any(), Mockito.any())).thenReturn(Optional.ofNullable(true));
		String contentAsString = mockMvc
				.perform(MockMvcRequestBuilders.post("/dms/users/Ty001/createproject")
						.contentType(MediaType.APPLICATION_JSON_VALUE)
						.content(objectMapper.writeValueAsString(newProjectDto)))
				.andExpect(MockMvcResultMatchers.status().isOk()).andReturn().getResponse().getContentAsString();

		GeneralResponse<String> generalResponse = objectMapper.readValue(contentAsString, GeneralResponse.class);
		assertEquals(generalResponse.getMessage(), "A project has been created successfully");

	}
	
	@Test
	public void testCreateProject_returns400() throws JsonProcessingException, Exception {
		objectMapper.registerModule(new JavaTimeModule());
		NewProjectDto newProjectDto = NewProjectDto.builder().projectId("Ty001").projectName("DMS").contactName("Leo")
				.build();
		Mockito.when(dmsUserService.createProject(Mockito.any(), Mockito.any())).thenReturn(Optional.ofNullable(false));
		String contentAsString = mockMvc
				.perform(MockMvcRequestBuilders.post("/dms/users/Ty001/createproject")
						.contentType(MediaType.APPLICATION_JSON_VALUE)
						.content(objectMapper.writeValueAsString(newProjectDto)))
				.andExpect(MockMvcResultMatchers.status().isBadRequest()).andReturn().getResponse().getContentAsString();

	}
 
	
	@Test
	public void testCreateDocument() throws Exception {
		objectMapper.registerModule(new JavaTimeModule());
		NewDocumentsDto documentsDto = NewDocumentsDto.builder().documentTypeDto(null).build();

		Mockito.when(dmsUserService.createNewDocument(Mockito.any(), Mockito.any(), Mockito.any()))
				.thenReturn(Optional.ofNullable(1));
		String contentAsString = mockMvc
				.perform(MockMvcRequestBuilders.post("/dms/users/Ty001/DMS/createdocument")
						.accept(MediaType.APPLICATION_JSON).contentType(MediaType.APPLICATION_JSON)
						.content(objectMapper.writeValueAsString(documentsDto)))
				.andExpect(MockMvcResultMatchers.status().isOk()).andReturn().getResponse().getContentAsString();
		GeneralResponse generalResponse = objectMapper.readValue(contentAsString, GeneralResponse.class);
		assertEquals("Document has been created", generalResponse.getMessage());
	}

	
	
	@Test
	public void testDeleteDocument() throws Exception {
		objectMapper.registerModule(new JavaTimeModule());
		DeleteDocumentDto deleteDocumentDto = DeleteDocumentDto.builder().documentId(1).build();
		Mockito.when(dmsUserService.deleteDocument(Mockito.any(), Mockito.any())).thenReturn(true);

		String contentAsString = mockMvc
				.perform(MockMvcRequestBuilders.put("/dms/users/1/deleteDocument").accept(MediaType.APPLICATION_JSON)
						.contentType(MediaType.APPLICATION_JSON)
						.content(objectMapper.writeValueAsString(deleteDocumentDto)))
				.andExpect(MockMvcResultMatchers.status().isOk()).andReturn().getResponse().getContentAsString();

		GeneralResponse<String> generalResponse = objectMapper.readValue(contentAsString, GeneralResponse.class);
		assertEquals(generalResponse.getMessage(), "document has been deleted successfully");

	}
	
	@Test
	public void testDeleteDocument_returns400() throws Exception {
		objectMapper.registerModule(new JavaTimeModule());
		DeleteDocumentDto deleteDocumentDto = DeleteDocumentDto.builder().documentId(1).build();
		Mockito.when(dmsUserService.deleteDocument(Mockito.any(), Mockito.any())).thenReturn(false);

		String contentAsString = mockMvc
				.perform(MockMvcRequestBuilders.put("/dms/users/1/deleteDocument").accept(MediaType.APPLICATION_JSON)
						.contentType(MediaType.APPLICATION_JSON)
						.content(objectMapper.writeValueAsString(deleteDocumentDto)))
				.andExpect(MockMvcResultMatchers.status().isBadRequest()).andReturn().getResponse().getContentAsString();

	}

	
	
	@Test
	public void testDeleteFile() throws Exception {
		objectMapper.registerModule(new JavaTimeModule());
		DeleteFileDto deleteFileDto = DeleteFileDto.builder().documentId(1).projectName("DMS").build();
		Mockito.when(dmsUserService.deleteFile(Mockito.any(), Mockito.any())).thenReturn(true);

		String contentAsString = mockMvc
				.perform(MockMvcRequestBuilders.put("/dms/users/1/deletefiles").accept(MediaType.APPLICATION_JSON)
						.contentType(MediaType.APPLICATION_JSON)
						.content(objectMapper.writeValueAsString(deleteFileDto)))
				.andExpect(MockMvcResultMatchers.status().isOk()).andReturn().getResponse().getContentAsString();

		GeneralResponse<String> generalResponse = objectMapper.readValue(contentAsString, GeneralResponse.class);
		assertEquals(generalResponse.getMessage(), "file has been deleted successfully");

	}
	
	@Test
	public void testDeleteFile_returns400() throws Exception {
		objectMapper.registerModule(new JavaTimeModule());
		DeleteFileDto deleteFileDto = DeleteFileDto.builder().documentId(1).projectName("DMS").build();
		Mockito.when(dmsUserService.deleteFile(Mockito.any(), Mockito.any())).thenReturn(false);

		String contentAsString = mockMvc
				.perform(MockMvcRequestBuilders.put("/dms/users/1/deletefiles").accept(MediaType.APPLICATION_JSON)
						.contentType(MediaType.APPLICATION_JSON)
						.content(objectMapper.writeValueAsString(deleteFileDto)))
				.andExpect(MockMvcResultMatchers.status().isBadRequest()).andReturn().getResponse().getContentAsString();

	}
	
	
	@Test
	public void testDeleteProject() throws Exception{
		objectMapper.registerModule(new JavaTimeModule());
		Mockito.when(dmsUserService.deleteProject(Mockito.any(), Mockito.any())).thenReturn(true);
		String contentAsString = mockMvc
				.perform(MockMvcRequestBuilders.put("/dms/users/1/deleteproject/DMS").contentType(MediaType.APPLICATION_JSON_VALUE))
				.andExpect(MockMvcResultMatchers.status().isOk()).andReturn().getResponse().getContentAsString();
		GeneralResponse<String> generalResponse = objectMapper.readValue(contentAsString, GeneralResponse.class);
		assertEquals(generalResponse.getMessage(), "project has been deleted successfully");
		
	}
	
	@Test
	public void testDeleteProject_returns400() throws Exception{
		objectMapper.registerModule(new JavaTimeModule());
		Mockito.when(dmsUserService.deleteProject(Mockito.any(), Mockito.any())).thenReturn(false);
		String contentAsString = mockMvc
				.perform(MockMvcRequestBuilders.put("/dms/users/1/deleteproject/DMS").contentType(MediaType.APPLICATION_JSON_VALUE))
				.andExpect(MockMvcResultMatchers.status().isBadRequest()).andReturn().getResponse().getContentAsString();
		
	}
	
	
	
	
	
	@Test
	public void testUploadFile() throws Exception {
		objectMapper.registerModule(new JavaTimeModule());
		Mockito.when(dmsUserService.uploadFile(Mockito.any(), Mockito.any(), Mockito.any(), Mockito.any()))
		                                 .thenReturn(Optional.ofNullable("Ty001"));
		String contentAsString = mockMvc
				.perform(MockMvcRequestBuilders.post("/dms/users/1/DMS/1/addfile").contentType(MediaType.APPLICATION_JSON_VALUE))
				.andExpect(MockMvcResultMatchers.status().isOk()).andReturn().getResponse().getContentAsString();
		GeneralResponse<String> generalResponse = objectMapper.readValue(contentAsString, GeneralResponse.class);
		assertEquals(generalResponse.getMessage(),"document has been uploaded");
	}

	
	@Test
	public void testShareFiles() throws JsonProcessingException, UnsupportedEncodingException, Exception {
	objectMapper.registerModule(new JavaTimeModule());	
	ShareFileDto shareFileDto=ShareFileDto.builder().fileName("leo").documentId(12).projectName("DMS").build();
	Mockito.when(dmsUserService.createProject(Mockito.any(), Mockito.any())).thenReturn(Optional.ofNullable(true));
	String contentAsString = mockMvc
			.perform(MockMvcRequestBuilders.post("/dms/users/Tyoo1/sharefile")
					.contentType(MediaType.APPLICATION_JSON_VALUE)
					.content(objectMapper.writeValueAsString(shareFileDto)))
			.andExpect(MockMvcResultMatchers.status().isBadRequest()).andReturn().getResponse().getContentAsString();

	GeneralResponse<String> generalResponse = objectMapper.readValue(contentAsString, GeneralResponse.class);
	assertEquals(generalResponse.getMessage(),"unable to share the file via email");

	}
	
	
	}

