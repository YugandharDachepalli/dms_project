package com.te.dms.controller;

import java.io.IOException;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;

import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.te.dms.dto.DeleteDocumentDto;
import com.te.dms.dto.DeleteFileDto;
import com.te.dms.dto.DocumentDataDto;
import com.te.dms.dto.FetchProjectDto;
import com.te.dms.dto.FetchUserDto;
import com.te.dms.dto.NewDocumentsDto;
import com.te.dms.dto.NewProjectDto;
import com.te.dms.dto.ShareFileDto;
import com.te.dms.exceptions.CannotBeProcessedException;
import com.te.dms.exceptions.DocumentUploadFailedException;
import com.te.dms.exceptions.ErrorCreatingDocumentException;
import com.te.dms.exceptions.FailedToShareException;
import com.te.dms.exceptions.FileNotFoundInTheStorageException;
import com.te.dms.exceptions.ProjectCannotbeCreatedException;
import com.te.dms.response.GeneralResponse;
import com.te.dms.service.DmsUserService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
@RequestMapping(path = "dms/users/{userId}")
public class UserController {

	private final DmsUserService dmsUserService;

	@PostMapping(path = "/createproject")
	public GeneralResponse<String> createProject(@PathVariable("userId") String userId,
			@RequestBody NewProjectDto newProjectDto) {
		Optional<Boolean> isCreated = dmsUserService.createProject(userId, newProjectDto);
		if (isCreated.get()) {
			return new GeneralResponse<String>("A project has been created successfully", null, null);
		}
		throw new ProjectCannotbeCreatedException("a new project cannot be created");
	}
  
	@PostMapping(path = "/{projectname}/createdocument")
	public GeneralResponse<Integer> createDocument(@PathVariable("projectname") String projectName,
			@RequestBody NewDocumentsDto newDocumentDto, @PathVariable("userId") String userId) {
		Optional<Integer> docId = dmsUserService.createNewDocument(projectName, newDocumentDto, userId);
		if (docId.isPresent()) {
			return new GeneralResponse<Integer>("Document has been created", docId.get(), null);
		}
		throw new ErrorCreatingDocumentException("unable to create document");
	}

	@PostMapping(path = "{projectname}/{documentId}/addfile")
	public GeneralResponse<String> uploadFile(@PathVariable("projectname") String projectName,
			@PathVariable("userId") String userId, @PathVariable("documentId") Integer documentId,
			@RequestParam("file") MultipartFile file) throws IOException {
		Optional<String> string = dmsUserService.uploadFile(projectName, documentId, file, userId);
		if (string.isPresent()) {
			return new GeneralResponse<String>("document has been uploaded", string.get(), null);
		}
		throw new DocumentUploadFailedException("failed to upload file");
	}

	@GetMapping(path = "/downloadfile/{projectname}/{documentId}/{filename}")
	public ResponseEntity<Resource> downloadfile(@PathVariable("filename") String fileName,
			@PathVariable("projectname") String projectName, @PathVariable("documentId") Integer documentId,
			@PathVariable("userId") String userId, HttpServletRequest request) {
		Optional<DocumentDataDto> document = dmsUserService.downloadFile(fileName, userId, projectName, documentId);
		if (document.isPresent()) {
			return ResponseEntity.ok().contentType(MediaType.parseMediaType(document.get().getFileType()))
					.header(HttpHeaders.CONTENT_DISPOSITION, "attachment;fileName=\"" + document.get().getKey1() + "\"")
					.body(new ByteArrayResource(document.get().getValue()));
		}
		throw new FileNotFoundInTheStorageException("file not found");
	}

	
	@PutMapping(path = "/deletefiles")
	public GeneralResponse<String> deleteFile(@PathVariable("userId") String userId,
			@RequestBody DeleteFileDto deleteFileDto) {
		Boolean isDeleted = dmsUserService.deleteFile(userId, deleteFileDto);
		if (isDeleted) {
			return new GeneralResponse<String>("file has been deleted successfully", null, userId);
		}
		return new GeneralResponse<String>("unable to delete the file ", null, userId);
	}

	@PutMapping(path = "/deleteDocument")
	public GeneralResponse<String> deleteDocument(@PathVariable("userId") String userId,
			@RequestBody DeleteDocumentDto deleteDocumentDto) {
		Boolean isDeleted = dmsUserService.deleteDocument(userId, deleteDocumentDto);
		if (isDeleted) {
			return new GeneralResponse<String>("document has been deleted successfully", null, userId);
		}
		return new GeneralResponse<String>("unable to delete the document", null, userId);
	}

	@PutMapping(path = "deleteproject/{projectName}")
	public GeneralResponse<String> deleteProject(@PathVariable("userId") String userId,
			@PathVariable("projectName") String projectName) {
		Boolean isDeleted = dmsUserService.deleteProject(projectName, userId);
		if (isDeleted) {
			return new GeneralResponse<String>("project has been deleted successfully", null, projectName);
		}
		throw new CannotBeProcessedException("unable to delete project");
	}

	@PostMapping(path = "/sharefile")
	public GeneralResponse<String> shareFiles(@PathVariable("userId") String userId,
			@RequestBody ShareFileDto sharefileDto) {
		Boolean isShared = dmsUserService.shareFiles(userId, sharefileDto);
		if (isShared) {
			return new GeneralResponse<String>("email has been shared", null, userId);
		}
		throw new FailedToShareException("unable to share the file via email");
	}
	
	@GetMapping(path = "/fetchUser")
	public GeneralResponse<FetchUserDto>fetchUser(@PathVariable (name="userId") String userId){
		Optional<FetchUserDto> fetchUserDto = dmsUserService.fetchUser(userId);
		if(fetchUserDto.isPresent()) {
			return new GeneralResponse<FetchUserDto>("user is Found for the User id  "+userId,fetchUserDto.get(),null);
		}
		return new GeneralResponse<FetchUserDto>(" User is Not Found ",null,null);
	}
	
	@GetMapping(path = "/{projectname}/getData")
	public GeneralResponse<Object> fetchData(@PathVariable("userId") String userId,
			@PathVariable("projectname") String projectName) {
		Optional<FetchProjectDto> optProject = dmsUserService.fetchData(projectName, userId);
		if (optProject.isPresent()) {
			return new GeneralResponse<Object>(projectName, optProject.get(), null);
		}
		return new GeneralResponse<Object>("no projects found", null, projectName);
	}


}
