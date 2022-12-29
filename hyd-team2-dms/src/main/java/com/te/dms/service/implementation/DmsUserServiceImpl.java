package com.te.dms.service.implementation;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.google.common.collect.Lists;
import com.te.dms.dto.DeleteDocumentDto;
import com.te.dms.dto.DeleteFileDto;
import com.te.dms.dto.DocumentDataDto;
import com.te.dms.dto.FetchDocumentFilesDto;
import com.te.dms.dto.FetchDocumentsDto;
import com.te.dms.dto.FetchProjectDto;
import com.te.dms.dto.FetchUserDto;
import com.te.dms.dto.LoginDto;
import com.te.dms.dto.NewDocumentsDto;
import com.te.dms.dto.NewProjectDto;
import com.te.dms.dto.ShareFileDto;
import com.te.dms.dto.UserRegisterDto;
import com.te.dms.entity.AppUser;
import com.te.dms.entity.DmsUser;
import com.te.dms.entity.DocumentData;
import com.te.dms.entity.DocumentFiles;
import com.te.dms.entity.DocumentType;
import com.te.dms.entity.Documents;
import com.te.dms.entity.Projects;
import com.te.dms.entity.Roles;
import com.te.dms.exceptions.UserNameExistsException;
import com.te.dms.repository.AppUserRepository;
import com.te.dms.repository.DmsUserRepository;
import com.te.dms.repository.DocumentDataRepository;
import com.te.dms.repository.DocumentFilesRepository;
import com.te.dms.repository.DocumentRepository;
import com.te.dms.repository.DocumentTypeRepository;
import com.te.dms.repository.ProjectsRepository;
import com.te.dms.repository.RolesRepository;
import com.te.dms.service.DmsUserService;
import com.te.dms.service.EmailSenderService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class DmsUserServiceImpl implements DmsUserService {

	private final DmsUserRepository dmsUserRepository;
	private final RolesRepository rolesRepository;
	private final AppUserRepository appUserRepository;
	private final ProjectsRepository projectsRepository;
	private final DocumentRepository documentRepository;
	private final DocumentTypeRepository documentTypeRepository;
	private final DocumentDataRepository documentDataRepository;
	private final DocumentFilesRepository documentFileRepository;
	private final EmailSenderService emailSenderService;

	@Override
	public Optional<Boolean> register(UserRegisterDto userRegisterDto) {
		Optional<DmsUser> optUser = dmsUserRepository.findById(userRegisterDto.getUserId());
		if (optUser.isEmpty()) {
			DmsUser dmsUser = new DmsUser();
			BeanUtils.copyProperties(userRegisterDto, dmsUser);
			Optional<Roles> roleName = rolesRepository.findByRoleName("ROLE_USER");
			if (roleName.isPresent()) {
				Roles roles = roleName.get();
				AppUser appUser = AppUser.builder().username(userRegisterDto.getUserId())
						.password(userRegisterDto.getPassword()).roles(Lists.newArrayList()).build();
				roles.getAppUsers().add(appUser);
				appUser.getRoles().add(roles);
				appUserRepository.save(appUser);
				dmsUserRepository.save(dmsUser);
				String message = "Thank you for registering Online DMS, We are happy to provide our Services";
				String subject = "Greeting from DMS";
				emailSenderService.sendEmail(userRegisterDto.getEmailId(), subject, message);
				return Optional.ofNullable(true);
			}
			return Optional.ofNullable(false);
		}
		throw new UserNameExistsException("username already exists please try with unique userId");
	}

	@Override
	public Boolean validate(LoginDto loginDto) {
		Optional<AppUser> appUser = appUserRepository.findById(loginDto.getUsername());
		if (appUser.isPresent()) {
			if ((appUser.get().getPassword()).equals(loginDto.getPassword())) {
				return true;
			}
		}
		return false;

	}

	@Override
	public Optional<Boolean> createProject(String userId, NewProjectDto newProjectDto) {
		Optional<Projects> optProjects = projectsRepository.findByProjectId(newProjectDto.getProjectId());
		if (optProjects.isPresent() && (optProjects.get().getProjectName()).equals(newProjectDto.getProjectName())) {
			return Optional.ofNullable(false);
		}
		Projects project = new Projects();
		BeanUtils.copyProperties(newProjectDto, project);
		project.setCreatedAt(LocalDateTime.now());
		project.setUpdatedAt(LocalDateTime.now());
		Optional<DmsUser> user = dmsUserRepository.findById(userId);
		project.setUser(user.get());
		projectsRepository.save(project);
		return Optional.ofNullable(true);
	}

	@Override
	public Optional<Integer> createNewDocument(String projectName, NewDocumentsDto newDocumentDto, String userId) {
		Optional<DocumentType> optDocType = documentTypeRepository
				.findByName(newDocumentDto.getDocumentTypeDto().getName());
		if (optDocType.isEmpty()) {
			Optional<Projects> optProject = projectsRepository.findByProjectandUser(projectName, userId);
			if (optProject.isPresent()) {
				optProject.get().setUpdatedAt(LocalDateTime.now());
				Documents document = new Documents();
				DocumentType documentType = new DocumentType();
				BeanUtils.copyProperties(newDocumentDto.getDocumentTypeDto(), documentType);
				documentType.setCreatedAt(LocalDateTime.now());
				documentType.setUpdatedAt(LocalDateTime.now());
				documentType.getDocument().add(document);
				document.setDocumentType(documentType);
				documentTypeRepository.save(documentType);
				documentType.getDocument().add(document);
				document.setCreatedAt(LocalDateTime.now());
				document.setUpdatedAt(LocalDateTime.now());
				document.setProjectId(optProject.get());
				documentRepository.save(document);
				return Optional.ofNullable(document.getId());
			}
		}

		return Optional.ofNullable(null);
	}

	@Override
	public Optional<String> uploadFile(String projectName, Integer documentId, MultipartFile file, String userId)
			throws IOException {
		String fileName = file.getOriginalFilename();
		DocumentData documentData = new DocumentData();
		documentData.setKey1(userId + "" + projectName + "" + documentId + "" + fileName);
		documentData.setValue(file.getBytes());
		documentData.setCreatedAt(LocalDateTime.now());
		documentData.setUpdatedAt(LocalDateTime.now());
		Optional<Projects> project = projectsRepository.findByProjectandUser(projectName, userId);
		if (project.isPresent()) {
			Optional<Documents> optDocuments = documentRepository.findById(documentId);
			if (optDocuments.isPresent()) {
				optDocuments.get().setUpdatedAt(LocalDateTime.now());
				documentData.setDocuments(optDocuments.get());
				Optional<DocumentFiles> optDocFile = documentFileRepository.findByFileName(fileName);
				if (optDocFile.isEmpty()) {
					DocumentFiles documentFile = new DocumentFiles();
					documentFile.setFileName(fileName);
					documentFile.setMime(file.getContentType());
					documentFile.setDocuments(optDocuments.get());
					documentFile.setCreatedAt(LocalDateTime.now());
					documentFile.setUpdatedAt(LocalDateTime.now());
					String filepath = ServletUriComponentsBuilder.fromCurrentContextPath()
							.path("/dms/users/" + userId + "/downloadfile/" + projectName + "/" + documentId + "/")
							.path(fileName).toUriString();
					documentFile.setFilePath(filepath);
					documentDataRepository.save(documentData);
					project.get().setUpdatedAt(LocalDateTime.now());
					optDocuments.get().setUpdatedAt(LocalDateTime.now());
					documentFileRepository.save(documentFile);
					return Optional.ofNullable(documentFile.getFilePath());
				}
			}
		}
		return Optional.ofNullable(null);
	}

	@Override
	public Optional<DocumentDataDto> downloadFile(String fileName, String userId, String projectName,
			Integer documentId) {
		String key = userId + "" + projectName + "" + documentId + "" + fileName;
		Optional<DocumentData> optDocData = documentDataRepository.findByKey1(key);
		if (optDocData.isPresent()) {
			DocumentDataDto documentDto = new DocumentDataDto();
			BeanUtils.copyProperties(optDocData.get(), documentDto);
			Optional<DocumentFiles> optFile = documentFileRepository.findByFileName(fileName);
			if (optFile.isPresent()) {
				documentDto.setFileType(optFile.get().getMime());
			}
			return Optional.ofNullable(documentDto);
		}
		return Optional.ofNullable(null);
	}

	@Override
	public Boolean deleteFile(String userId, DeleteFileDto deleteFileDto) {
		Optional<Projects> project = projectsRepository.findByProjectandUser(deleteFileDto.getProjectName(), userId);
		if (project.isPresent()) {
			Optional<Documents> document = documentRepository.findById(deleteFileDto.getDocumentId());
			if (document.isPresent()) {
				Optional<DocumentFiles> file = documentFileRepository.findByFilename(deleteFileDto.getFileName());
				if (file.isPresent()) {
					document.get().setUpdatedAt(LocalDateTime.now());
					project.get().setUpdatedAt(LocalDateTime.now());
					file.get().setDeletedAt(LocalDateTime.now());
					documentFileRepository.save(file.get());
					return true;
				}
			}
		}
		return false;
	}

	@Override
	public Boolean deleteDocument(String userId, DeleteDocumentDto deleteDocumentDto) {
		Optional<Projects> project = projectsRepository.findByProjectandUser(deleteDocumentDto.getProjectName(),
				userId);
		if (project.isPresent()) {
			Optional<Documents> document = documentRepository
					.findByDocumentAndProject(deleteDocumentDto.getDocumentId(), project.get().getId());
			if (document.isPresent()) {
				project.get().setUpdatedAt(LocalDateTime.now());
				document.get().setDeletedAt(LocalDateTime.now());
				document.get().getDocumentFiles().stream().forEach(e -> e.setDeletedAt(LocalDateTime.now()));
				documentRepository.save(document.get());
				return true;
			}
		}
		return false;
	}

	@Override
	public Boolean deleteProject(String projectName, String userId) {
		Optional<DmsUser> user = dmsUserRepository.findById(userId);
		if (user.isPresent()) {
			user.get().getProjects().stream().filter(e -> (e.getProjectName()).equals(projectName))
					.forEach(e -> e.setDeletedAt(LocalDateTime.now()));
			dmsUserRepository.save(user.get());
			return true;
		}
		return false;
	}

	@Override
	public Boolean shareFiles(String userId, ShareFileDto sharefileDto) {
		Optional<DmsUser> user = dmsUserRepository.findById(userId);
		if (user.isPresent()) {
			Optional<Documents> docuOptional = documentRepository.findById(sharefileDto.getDocumentId());
			if (docuOptional.isPresent()) {
				Optional<DocumentFiles> file = documentFileRepository.findByFilename(sharefileDto.getFileName());
				emailSenderService.sendEmailWithUrl(sharefileDto.getEmailId(), "Documents", "Attachments",
						file.get().getFilePath());
				return true;
			}

		}
		return false;

	}

	@Override
	public Optional<FetchUserDto> fetchUser(String userId) {
		Optional<DmsUser> optionalUser = dmsUserRepository.findById(userId);
		FetchUserDto fetchUserDto = new FetchUserDto();
		if (optionalUser.isPresent()) {
			BeanUtils.copyProperties(optionalUser.get(), fetchUserDto);
			for (Projects projects : optionalUser.get().getProjects()) {
				FetchProjectDto fetchProjectDto = new FetchProjectDto();
				BeanUtils.copyProperties(projects, fetchProjectDto);

				List<Documents> documents = projects.getDocuments();
				for (Documents documents2 : documents) {
					FetchDocumentsDto documentsDto = new FetchDocumentsDto();
					BeanUtils.copyProperties(documents2, documentsDto);
					List<DocumentFiles> files = documents2.getDocumentFiles();
					for (DocumentFiles file1 : files) {
						FetchDocumentFilesDto fileDto = new FetchDocumentFilesDto();
						BeanUtils.copyProperties(file1, fileDto);
						documentsDto.getDocumentFilesDto().add(fileDto);
					}
					fetchProjectDto.getDocumentsDto().add(documentsDto);

				}
				fetchUserDto.getProjectDto().add(fetchProjectDto);

			}

			return Optional.ofNullable(fetchUserDto);
		}
		return null;
	}

	@Override
	public Optional<FetchProjectDto> fetchData(String projectName, String userId) {
		Optional<Projects> optProject = projectsRepository.findByProjectandUser(projectName, userId);
		if (optProject.isPresent()) {
			FetchProjectDto fetchProjectDto = new FetchProjectDto();
			BeanUtils.copyProperties(optProject.get(), fetchProjectDto);

			Function<Documents, FetchDocumentsDto> function = e -> {
				FetchDocumentsDto fetchDocumentsDto = new FetchDocumentsDto();
				BeanUtils.copyProperties(e, fetchDocumentsDto);
				return fetchDocumentsDto;
			};
			List<FetchDocumentsDto> fetchdocuments = optProject.get().getDocuments().stream().map(function)
					.filter(e -> e.getDeletedAt() == null).collect(Collectors.toList());

			for (FetchDocumentsDto fetchDocumentsDto2 : fetchdocuments) {
				Optional<List<DocumentFiles>> documentFiles = documentFileRepository
						.findAllByDocuments(fetchDocumentsDto2.getId());
				if (documentFiles.isPresent()) {
					Function<DocumentFiles, FetchDocumentFilesDto> function1 = e -> {
						FetchDocumentFilesDto fetchDocumentFilesDto = new FetchDocumentFilesDto();
						BeanUtils.copyProperties(e, fetchDocumentFilesDto);
						return fetchDocumentFilesDto;
					};
					List<FetchDocumentFilesDto> fetchDocumentFiles = documentFiles.get().stream().map(function1)
							.collect(Collectors.toList());
					fetchDocumentsDto2.setDocumentFilesDto(fetchDocumentFiles);
				}
			}
			fetchProjectDto.setDocumentsDto(fetchdocuments);
			return Optional.ofNullable(fetchProjectDto);
		}
		return Optional.ofNullable(null);
	}
}
