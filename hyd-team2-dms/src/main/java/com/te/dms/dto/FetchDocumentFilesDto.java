package com.te.dms.dto;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class FetchDocumentFilesDto {
	
	private Integer id;

	private String fileName;

	private String mime;

	private String filePath;

	private LocalDateTime deletedAt;

	private LocalDateTime createdAt;

	private LocalDateTime updatedAt;
}
