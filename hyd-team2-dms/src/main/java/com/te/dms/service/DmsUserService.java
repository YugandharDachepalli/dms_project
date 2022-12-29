package com.te.dms.service;

import java.io.IOException;
import java.util.Optional;

import org.springframework.web.multipart.MultipartFile;

import com.te.dms.dto.DeleteDocumentDto;
import com.te.dms.dto.DeleteFileDto;
import com.te.dms.dto.DocumentDataDto;
import com.te.dms.dto.FetchProjectDto;
import com.te.dms.dto.FetchUserDto;
import com.te.dms.dto.LoginDto;
import com.te.dms.dto.NewDocumentsDto;
import com.te.dms.dto.NewProjectDto;
import com.te.dms.dto.ShareFileDto;
import com.te.dms.dto.UserRegisterDto;

public interface DmsUserService {

	Optional<Boolean> register(UserRegisterDto userRegisterDto);

	Boolean validate(LoginDto loginDto);

	Optional<Boolean> createProject(String userId, NewProjectDto newProjectDto);

	Optional<Integer> createNewDocument(String projectName, NewDocumentsDto newDocumentDto, String userId);

	Optional<String> uploadFile(String projectName, Integer documentId, MultipartFile file, String userId)
			throws IOException;

	Optional<DocumentDataDto> downloadFile(String fileName, String userId, String projectName, Integer documentId);

	Optional<FetchProjectDto> fetchData(String projectName, String userId);

	Boolean deleteFile(String userId, DeleteFileDto deleteFileDto);

	Boolean deleteDocument(String userId, DeleteDocumentDto deleteDocumentDto);

	Boolean deleteProject(String projectName, String userId);

	Boolean shareFiles(String userId, ShareFileDto sharefileDto);

	Optional<FetchUserDto> fetchUser(String userId);




}
