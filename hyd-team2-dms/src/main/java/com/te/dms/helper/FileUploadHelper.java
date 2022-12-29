package com.te.dms.helper;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

@Component
public class FileUploadHelper {

	public final String UPLOAD_DIR = "C:\\Users\\rajes\\OneDrive\\Desktop\\uplift-hyd-2022\\project-2\\document-management-system\\src\\main\\resources\\static\\documents";

	public Boolean uploadFile(MultipartFile file) {
		Boolean f = false;

		try { 
			Files.copy(file.getInputStream(),Paths.get(UPLOAD_DIR+File.separator+file.getOriginalFilename()), StandardCopyOption.REPLACE_EXISTING);
			f=true;
			return f;

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return f;
		}
		

	}

}
