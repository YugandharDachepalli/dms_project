package com.te.dms.exceptions.handler;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.te.dms.exceptions.CannotBeProcessedException;
import com.te.dms.exceptions.CannotRegisterUserException;
import com.te.dms.exceptions.DocumentUploadFailedException;
import com.te.dms.exceptions.ErrorCreatingDocumentException;
import com.te.dms.exceptions.FailedToShareException;
import com.te.dms.exceptions.FileNotFoundInTheStorageException;
import com.te.dms.exceptions.PasswordMisMatchException;
import com.te.dms.exceptions.ProjectCannotbeCreatedException;
import com.te.dms.exceptions.UserNameExistsException;

@RestControllerAdvice
public class DmsExceptionHandler {

	@ResponseStatus(value = HttpStatus.BAD_REQUEST)
	@ExceptionHandler(UserNameExistsException.class)
	public Map<String, String> handler(UserNameExistsException ex) {
		Map<String, String> map = new HashMap<String, String>();
		map.put("message", ex.getMessage());
		return map;
	}

	@ResponseStatus(value = HttpStatus.BAD_REQUEST)
	@ExceptionHandler(CannotRegisterUserException.class)
	public Map<String, String> handler(CannotRegisterUserException ex) {
		Map<String, String> map = new HashMap<String, String>();
		map.put("message", ex.getMessage());
		return map;
	}

	@ResponseStatus(value = HttpStatus.BAD_REQUEST)
	@ExceptionHandler(PasswordMisMatchException.class)
	public Map<String, String> handler(PasswordMisMatchException ex) {
		Map<String, String> map = new HashMap<String, String>();
		map.put("message", ex.getMessage());
		return map;
	}

	@ResponseStatus(value = HttpStatus.BAD_REQUEST)
	@ExceptionHandler(ProjectCannotbeCreatedException.class)
	public Map<String, String> handler(ProjectCannotbeCreatedException ex) {
		Map<String, String> map = new HashMap<String, String>();
		map.put("message", ex.getMessage());
		return map;
	}

	@ResponseStatus(value = HttpStatus.BAD_REQUEST)
	@ExceptionHandler(ErrorCreatingDocumentException.class)
	public Map<String, String> handler(ErrorCreatingDocumentException ex) {
		Map<String, String> map = new HashMap<String, String>();
		map.put("message", ex.getMessage());
		return map;
	}

	@ResponseStatus(value = HttpStatus.BAD_REQUEST)
	@ExceptionHandler(DocumentUploadFailedException.class)
	public Map<String, String> handler(DocumentUploadFailedException ex) {
		Map<String, String> map = new HashMap<String, String>();
		map.put("message", ex.getMessage());
		return map;
	}

	@ResponseStatus(value = HttpStatus.BAD_REQUEST)
	@ExceptionHandler(FileNotFoundInTheStorageException.class)
	public Map<String, String> handler(FileNotFoundInTheStorageException ex) {
		Map<String, String> map = new HashMap<String, String>();
		map.put("message", ex.getMessage());
		return map;
	}

	@ResponseStatus(value = HttpStatus.BAD_REQUEST)
	@ExceptionHandler(CannotBeProcessedException.class)
	public Map<String, String> handler(CannotBeProcessedException ex) {
		Map<String, String> map = new HashMap<String, String>();
		map.put("message", ex.getMessage());
		return map;
	}

	@ResponseStatus(value = HttpStatus.BAD_REQUEST)
	@ExceptionHandler(FailedToShareException.class)
	public Map<String, String> handler(FailedToShareException ex) {
		Map<String, String> map = new HashMap<String, String>();
		map.put("message", ex.getMessage());
		return map;
	}
}
